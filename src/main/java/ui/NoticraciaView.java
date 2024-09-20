package ui;

import controller.NoticraciaController;
import noticracia.core.Noticracia;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class NoticraciaView extends JFrame implements Observer {

    private final Noticracia noticracia;
    private final NoticraciaController noticraciaController;

    public NoticraciaView(Noticracia noticracia) {
        this.noticracia = noticracia;
        initialize();

        noticraciaController = new NoticraciaController(this, this.noticracia);
        noticracia.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Noticracia){
            this.generateWordCloud((Map<String, Integer>) arg);
        }
    }

    private JComboBox<String> candidateComboBox;
    private JButton startButton;
    private JButton cancelButton;
    private JPanel wordCloudPanel;
    private JComboBox<String> sourceComboBox;


    private final String[] candidates = {"Javier Milei", "Cristina Kirchner"};



    private void initialize() {
        setTitle("Noticracia");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Generar Nube de Palabras"));

        candidateComboBox = new JComboBox<>(candidates);
        sourceComboBox = new JComboBox<>();



        startButton = new JButton("Generar Nube de Palabras");
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            cancelButton.setVisible(true);
            noticraciaController.startProcess((String) candidateComboBox.getSelectedItem(), (String) sourceComboBox.getSelectedItem());
        });

        cancelButton = new JButton("Cancelar");
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> {
            startButton.setEnabled(true);
            cancelButton.setVisible(false);
            noticraciaController.stopProcess();
        });

        selectionPanel.add(new JLabel("Seleccionar Candidato:"));
        selectionPanel.add(candidateComboBox);
        selectionPanel.add(new JLabel("Seleccionar Fuente:"));
        selectionPanel.add(sourceComboBox);

        selectionPanel.add(startButton);
        selectionPanel.add(cancelButton);

        JButton addSourceButton = new JButton("Añadir Fuente");

        wordCloudPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLACK);
                g.drawString("Aquí se mostrará la nube de palabras", 10, 20);
            }
        };
        wordCloudPanel.setPreferredSize(new Dimension(500, 300));

        mainPanel.add(selectionPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(mainPanel, BorderLayout.NORTH);
        add(new JScrollPane(wordCloudPanel), BorderLayout.CENTER);

    }

    private void generateWordCloud(Map<String, Integer> wordCloud) {
        String selectedCandidate = (String) candidateComboBox.getSelectedItem();
        String selectedSource = (String) sourceComboBox.getSelectedItem();

        if (selectedCandidate != null && selectedSource != null) {
            Graphics g = wordCloudPanel.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, wordCloudPanel.getWidth(), wordCloudPanel.getHeight());

            g.setColor(Color.BLACK);

            var rand = new Random();

            Map<String, Integer> topTenWords = wordCloud.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            for (Map.Entry<String, Integer> word : topTenWords.entrySet()) {
                g.setFont(new Font("Arial", Font.BOLD, word.getValue() * 4));
                g.drawString(word.getKey(), rand.nextInt(wordCloudPanel.getWidth() - 100), rand.nextInt(wordCloudPanel.getHeight() - 20) + 20);
            }

            g.setFont(new Font("Arial", Font.PLAIN, 20));
        }
    }


    public void updateInformationSources(Set<String> sources) {
        SwingUtilities.invokeLater(() -> {
            sourceComboBox.setModel(new DefaultComboBoxModel<>(sources.toArray(new String[0])));
            sourceComboBox.revalidate();
            sourceComboBox.repaint();
        });
    }

    public void setProcessing(boolean isProcessing) {
        startButton.setEnabled(!isProcessing);
    }

}