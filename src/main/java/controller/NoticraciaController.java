package controller;

import noticracia.core.Noticracia;
import ui.NoticraciaView;

public class NoticraciaController {

    private final Noticracia noticracia;
    private final NoticraciaView noticraciaView;

    public NoticraciaController(NoticraciaView noticraciaView, Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaView = noticraciaView;
    }

    public void search(String informationSourceSelected, String candidateName) {
        if (candidateName != null) {
            noticraciaView.setProcessing(true);
            noticracia.startSearch(informationSourceSelected, candidateName);
            noticraciaView.setProcessing(false);
        }
    }
}
