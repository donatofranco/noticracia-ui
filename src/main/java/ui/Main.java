package ui;

import instancia.ClarinInformationSource;
import noticracia.core.Noticracia;

import javax.swing.*;

public class Main {

    public static void main(String[] args) { //TODO: Que mandamos acá?
        Noticracia noticracia = new Noticracia(new ClarinInformationSource());

        SwingUtilities.invokeLater(() -> {
            NoticraciaView noticraciaView = new NoticraciaView(noticracia);
            noticraciaView.setVisible(true);
        });
    }

}
