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

    public void search(String candidateName) {
        if (candidateName != null) {
            noticracia.search(candidateName);
            noticraciaView.setProcessing(true);
        }
    }

    public void stopProcess() {
        noticraciaView.setProcessing(false);
    }
}
