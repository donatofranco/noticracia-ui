package ui;

import controller.NoticraciaController;
import noticracia.core.Noticracia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class NoticraciaView extends JFrame implements Observer {

    private Noticracia noticracia;
    private NoticraciaController noticraciaController;

    public NoticraciaView(Noticracia noticracia) {
        this.noticracia = noticracia;
        initialize();

        noticraciaController = new NoticraciaController(this, noticracia);
        noticracia.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        // TODO: Implementar, tiene que "rehacer" la nube de palabras
    }


    private JComboBox<String> candidateComboBox;
    private JComboBox<String> sourceComboBox;
    private JButton generateButton;
    private JTextField newSourceField;
    private JButton addSourceButton;
    private JPanel wordCloudPanel;

    private String[] candidates = {"Candidato A", "Candidato B", "Candidato C"};
    private Vector<String> sources = new Vector<>(Arrays.asList("Fuente 1", "Fuente 2", "Fuente 3"));



    private void initialize() {
        setTitle("Noticracia");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Generar Nube de Palabras"));

        candidateComboBox = new JComboBox<>(candidates);
        sourceComboBox = new JComboBox<>(sources);
        generateButton = new JButton("Generar Nube de Palabras");

        selectionPanel.add(new JLabel("Seleccionar Candidato:"));
        selectionPanel.add(candidateComboBox);
        //selectionPanel.add(new JLabel("Seleccionar Fuente:"));
        //selectionPanel.add(sourceComboBox);
        selectionPanel.add(new JLabel());
        selectionPanel.add(generateButton);

        //JPanel addSourcePanel = new JPanel(new FlowLayout());
        //addSourcePanel.setBorder(BorderFactory.createTitledBorder("Añadir Nueva Fuente"));

        newSourceField = new JTextField(20);
        addSourceButton = new JButton("Añadir Fuente");

        //addSourcePanel.add(newSourceField);
        //addSourcePanel.add(addSourceButton);

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
        //mainPanel.add(addSourcePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(mainPanel, BorderLayout.NORTH);
        add(new JScrollPane(wordCloudPanel), BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateWordCloud();
            }
        });

        addSourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewSource();
            }
        });
    }

    private void generateWordCloud() {
        String selectedCandidate = (String) candidateComboBox.getSelectedItem();
        String selectedSource = (String) sourceComboBox.getSelectedItem();

        if (selectedCandidate != null && selectedSource != null) {
            Graphics g = wordCloudPanel.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, wordCloudPanel.getWidth(), wordCloudPanel.getHeight());

            g.setColor(Color.BLACK);
            String[] words = {"Economía", "Educación", "Salud", "Seguridad", "Empleo", "Infraestructura", "Corrupción", "Medio Ambiente"};
            Random rand = new Random();

            for (String word : words) {
                int fontSize = rand.nextInt(20) + 12;  // Tamaño de fuente entre 12 y 32
                g.setFont(new Font("Arial", Font.BOLD, fontSize));
                g.drawString(word, rand.nextInt(wordCloudPanel.getWidth() - 100), rand.nextInt(wordCloudPanel.getHeight() - 20) + 20);
            }

            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString("Nube de palabras para " + selectedCandidate + " usando " + selectedSource, 10, wordCloudPanel.getHeight() - 10);
        }
    }

    private void addNewSource() {
        String newSource = newSourceField.getText().trim();
        if (!newSource.isEmpty()) {
            sources.add(newSource);
            sourceComboBox.addItem(newSource);
            newSourceField.setText("");
            JOptionPane.showMessageDialog(this, "Nueva fuente añadida: " + newSource);
        }
    }


}