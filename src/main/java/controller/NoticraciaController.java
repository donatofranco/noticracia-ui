package controller;

import noticracia.core.Noticracia;
import noticracia.entities.WordCloud;
import ui.NoticraciaView;

public class NoticraciaController {

    private final Noticracia noticracia;
    private final NoticraciaView noticraciaView;

    public NoticraciaController(NoticraciaView noticraciaView, Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaView = noticraciaView;
    }

    public void getWordCloud(String candidateName) {
        WordCloud wordCloud = noticracia.generateWordCloud(candidateName);
        noticraciaView.setWordCloud(wordCloud);
    }

    public void stopProcess() {
        noticraciaView.setProcessing(false);
    }
}
