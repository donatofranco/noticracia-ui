package ui;

import noticracia.core.Noticracia;
import noticracia.services.factories.NoticraciaFactory;
import validator.ArgsValidator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        new ArgsValidator().validate(args);
        Noticracia noticracia = new NoticraciaFactory().createNoticracia(args[0]);

        SwingUtilities.invokeLater(() -> {
            NoticraciaView noticraciaView = new NoticraciaView(noticracia);
            noticraciaView.setVisible(true);
        });
    }

}
