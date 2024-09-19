package controller;

import noticracia.core.Noticracia;
import ui.NoticraciaView;

public class NoticraciaController {

    private Noticracia noticracia;
    private NoticraciaView noticraciaView;

    //TODO: Recibir el mock de candidatos de Noticracia?
    public NoticraciaController(NoticraciaView noticraciaView, Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaView = noticraciaView;
    }
}
