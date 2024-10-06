package ui;

import noticracia.core.Noticracia;
import validator.ArgsValidator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        new ArgsValidator().validate(args);
        Noticracia noticracia = new Noticracia(args[0]);

        SwingUtilities.invokeLater(() -> {
            NoticraciaView noticraciaView = new NoticraciaView(noticracia);
            noticraciaView.setVisible(true);
        });
    }

}
