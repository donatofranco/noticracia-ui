package ui;

import instancia.ClarinInformationSource;
import noticracia.core.Noticracia;

import javax.swing.*;

public class Main {

    private final Noticracia noticracia;

    public Main() {
        this.noticracia = new Noticracia(new ClarinInformationSource());
        this.noticracia.setPolitician("Candidato A");
    }

    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
        SwingUtilities.invokeLater(() -> {
            NoticraciaView noticraciaView = new NoticraciaView(noticracia);
            noticraciaView.setVisible(true);
        });
    }

}
