package ui;

import noticracia.core.Noticracia;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Noticracia noticracia = new Noticracia("src/main/java/resources/extensions");

        SwingUtilities.invokeLater(() -> {
            NoticraciaView noticraciaView = new NoticraciaView(noticracia);
            noticraciaView.setVisible(true);
        });
    }

}
