package ui;

import configuration.ConfigLoader;
import controller.NoticraciaController;
import noticracia.core.Noticracia;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

@SuppressWarnings("deprecation")
public class NoticraciaView extends JFrame implements Observer {
    private final Noticracia noticracia;
    private final NoticraciaController noticraciaController;
    private JComboBox<String> candidateComboBox;
    private JButton startButton;
    private JButton cancelButton;
    private JPanel wordCloudPanel;

    public NoticraciaView(Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaController = new NoticraciaController(this, noticracia);
        ConfigLoader configLoader = ConfigLoader.getInstance();
        initializeUI(configLoader.getCriteria());
        noticracia.addObserver(this);
    }

    private void initializeUI(String[] candidatos) {
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

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionPanel.add(new JLabel("Fuente:"), gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;
        selectionPanel.add(new JLabel(this.noticracia.getInformationSourceName()), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        startButton = new JButton("Generar Nube de Palabras");
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            cancelButton.setVisible(true);
            noticraciaController.search((String) candidateComboBox.getSelectedItem());
        });
        selectionPanel.add(startButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        cancelButton = new JButton("Cancelar");
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> {
            startButton.setEnabled(true);
            cancelButton.setVisible(false);
            noticraciaController.stopProcess();
        });
        selectionPanel.add(cancelButton, gbc);

        add(selectionPanel, BorderLayout.NORTH);
    }

    private void setupWordCloudPanel() {
        wordCloudPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        wordCloudPanel.setPreferredSize(new Dimension(500, 400));
        add(new JScrollPane(wordCloudPanel), BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Noticracia && arg instanceof Map) {
            drawWordCloud(wordCloudPanel.getGraphics(), (Map<String, Integer>) arg);
        }
    }

    private void drawWordCloud(Graphics g, Map<String, Integer> wordCloud) {
        Graphics2D g2d = (Graphics2D) g;
        Random random = new Random();
        Dimension panelSize = wordCloudPanel.getSize();

        for (Map.Entry<String, Integer> entry : wordCloud.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();

            int fontSize = Math.max(10, (int) (count * 2));
            Font font = new Font("Arial", Font.BOLD, fontSize);
            g2d.setFont(font);

            FontMetrics metrics = g2d.getFontMetrics(font);
            int stringWidth = metrics.stringWidth(word);
            int stringHeight = metrics.getHeight();

            int x = random.nextInt(panelSize.width - stringWidth) + stringWidth / 2;
            int y = random.nextInt(panelSize.height - stringHeight) + stringHeight;

            float angle = random.nextFloat() * 360;
            AffineTransform originalTransform = g2d.getTransform();
            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(angle));
            g2d.drawString(word, -stringWidth / 2, stringHeight / 2);
            g2d.setTransform(originalTransform);
        }
    }

    public void setProcessing(boolean isProcessing) {
        startButton.setEnabled(!isProcessing);
    }
}
