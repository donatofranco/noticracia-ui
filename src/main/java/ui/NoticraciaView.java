package ui;

import configuration.ConfigLoader;
import controller.NoticraciaController;
import noticracia.core.Noticracia;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class NoticraciaView extends JFrame implements Observer {
    private final Noticracia noticracia;
    private final NoticraciaController noticraciaController;
    private JComboBox<String> candidateComboBox;
    private JComboBox<String> informationSourceBox = new JComboBox<>();
    private JButton startButton;
    private JPanel wordCloudPanel;
    private Map<String, Integer> currentWordCloud = new HashMap<>();
    private BufferedImage wordCloudImage;

    private static class WordRectangle {
        Rectangle bounds;
        String word;

        WordRectangle(Rectangle bounds, String word) {
            this.bounds = bounds;
            this.word = word;
        }
    }

    private static final int CLOUD_PADDING = 20; // Adjust this value as needed

    public NoticraciaView(Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaController = new NoticraciaController(this, noticracia);
        ConfigLoader configLoader = ConfigLoader.getInstance();
        initializeUI(configLoader.getCriteria());
        noticracia.addObserver(this);
    }

    private void initializeUI(String[] candidatos) {
        URL iconURL = getClass().getResource("/img/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);

        setIconImage(icon.getImage());
        setTitle("Noticracia - Nube de Palabras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setupSelectionPanel(candidatos);
        setupWordCloudPanel();
        pack();
        setLocationRelativeTo(null);
    }

    private void setupSelectionPanel(String[] candidatos) {
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Configuración"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionPanel.add(new JLabel("Seleccionar Candidato:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        candidateComboBox = new JComboBox<>(candidatos);
        selectionPanel.add(candidateComboBox, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;

        loadInformationSourceNames();


        selectionPanel.add(informationSourceBox, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionPanel.add(new JLabel("Fuente:"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        startButton = new JButton("Generar Nube de Palabras");
        startButton.addActionListener(e -> {
             startButton.setEnabled(false);
             clearWordCloud(); 
            noticraciaController.search((String) informationSourceBox.getSelectedItem() ,(String) candidateComboBox.getSelectedItem());
        });
        selectionPanel.add(startButton, gbc);

        add(selectionPanel, BorderLayout.NORTH);
    }

    private void loadInformationSourceNames() {
        informationSourceBox.removeAllItems();
        for (String informationSourcesName : noticracia.getInformationSourcesNames()) {
            informationSourceBox.addItem(informationSourcesName);
        }
        informationSourceBox.revalidate();
        informationSourceBox.repaint();
    }

    private void setupWordCloudPanel() {
        wordCloudPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (wordCloudImage != null) {
                    g.drawImage(wordCloudImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        wordCloudPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 150)); // Adjust 150 as needed
        add(wordCloudPanel, BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Noticracia && arg instanceof Map) {
            currentWordCloud = (Map<String, Integer>) arg;
            wordCloudImage = new BufferedImage(wordCloudPanel.getWidth(), wordCloudPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = wordCloudImage.createGraphics();
            drawWordCloud(g2d, currentWordCloud);
            g2d.dispose();
            wordCloudPanel.repaint();
        }
        if(o instanceof Noticracia && "Attemting to load new information sources...".equals(arg)) {
            loadInformationSourceNames();
            System.out.println(noticracia.getInformationSourcesNames());
        }
    }

    private void drawWordCloud(Graphics g, Map<String, Integer> wordCloud) {
        if (wordCloud == null || wordCloud.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        Dimension panelSize = wordCloudPanel.getSize();

        drawCloudShape(g2d, panelSize.width, panelSize.height);

        g2d.setColor(Color.BLACK);
        Random random = new Random();
        

        List<Map.Entry<String, Integer>> topWords = wordCloud.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(8)
                .collect(Collectors.toList());

        int centerX = panelSize.width / 2;
        int centerY = panelSize.height / 2;
        int radius = (Math.min(panelSize.width, panelSize.height) / 3) - CLOUD_PADDING;

        int baseFontSize = Math.max(10, Math.min(panelSize.width, panelSize.height) / 20);

        List<WordRectangle> placedWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : topWords) {
            String word = entry.getKey();
            int count = entry.getValue();

            // Scale font size based on word frequency and panel size
            int fontSize = Math.max(baseFontSize, (int)(baseFontSize * (1 + count / 15.0)));
            Font font = new Font("Arial", Font.BOLD, fontSize);
            g2d.setFont(font);

            FontMetrics metrics = g2d.getFontMetrics(font);
            int stringWidth = metrics.stringWidth(word);
            int stringHeight = metrics.getHeight();

            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < 100) {
                int x = random.nextInt(panelSize.width - stringWidth);
                int y = random.nextInt(panelSize.height - stringHeight) + stringHeight;

                if (isInsideCloudShape(x, y, panelSize.width, panelSize.height)) {
                    Rectangle newWordRect = new Rectangle(x, y, stringWidth, stringHeight);
                    if (isValidPlacement(newWordRect, placedWords)) {
                        float rotationAngle = (float) (random.nextFloat() * Math.PI / 4 - Math.PI / 8);
                        AffineTransform originalTransform = g2d.getTransform();
                        g2d.translate(x, y);
                        g2d.rotate(rotationAngle);
                        g2d.drawString(word, 0, 0);
                        g2d.setTransform(originalTransform);

                        placedWords.add(new WordRectangle(newWordRect, word));
                        placed = true;
                    }
                }
                attempts++;
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String byText = "by: " + informationSourceBox.getSelectedItem();
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(byText);
        int textHeight = fm.getHeight();
        int x = panelSize.width - textWidth - 10;
        int y = panelSize.height - textHeight - 10;
        g2d.drawString(byText, x, y);
    }

    private void drawCloudShape(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(171, 218, 204));
        g2d.setStroke(new BasicStroke(2));
        
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = (int)(Math.min(width, height) / 3 * 1.15);

        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2d.fillOval(centerX - radius / 2, centerY - radius * 3 / 4, radius, radius);
        g2d.fillOval(centerX + radius / 2, centerY - radius * 3 / 4, radius, radius);
        g2d.fillOval(centerX - radius * 3 / 4, centerY, radius, radius);
        g2d.fillOval(centerX + radius * 3 / 4, centerY, radius, radius);

        // New circle to the left, 10% smaller
        int smallerRadius = (int)(radius * 0.9 * 1.15);
        g2d.fillOval(centerX - radius * 3 / 2, centerY - radius / 2, smallerRadius, smallerRadius);
    }

    private boolean isValidPlacement(Rectangle newRect, List<WordRectangle> placedWords) {
        for (WordRectangle placedWord : placedWords) {
            if (newRect.intersects(placedWord.bounds)) {
                return false;
            }
        }
        return true;
    }

    private void clearWordCloud() {
        wordCloudImage = null;
        wordCloudPanel.repaint();
    }

    public void setProcessing(boolean isProcessing) {
        startButton.setEnabled(!isProcessing);
    }

    private boolean isInsideCloudShape(int x, int y, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = (int)(Math.min(width, height) / 3 * 1);

        // Check if the point is inside any of the ovals
        if (isInsideOval(x, y, centerX, centerY, radius, radius)) return true;
        if (isInsideOval(x, y, centerX - radius / 2, centerY - radius * 3 / 4, radius, radius)) return true;
        if (isInsideOval(x, y, centerX + radius / 2, centerY - radius * 3 / 4, radius, radius)) return true;
        if (isInsideOval(x, y, centerX - radius * 3 / 4, centerY, radius, radius)) return true;
        if (isInsideOval(x, y, centerX + radius * 3 / 4, centerY, radius, radius)) return true;
        if (isInsideOval(x, y, centerX - radius * 3 / 2, centerY - radius / 2, (int)(radius * 0.9 * 1.15), (int)(radius * 0.9 * 1.15))) return true;

        return false;
    }

    private boolean isInsideOval(int x, int y, int centerX, int centerY, int width, int height) {
        double normalizedX = Math.pow(x - centerX, 2) / Math.pow(width / 2.0, 2);
        double normalizedY = Math.pow(y - centerY, 2) / Math.pow(height / 2.0, 2);
        return normalizedX + normalizedY <= 1;
    }
}