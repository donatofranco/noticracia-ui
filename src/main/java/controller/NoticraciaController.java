package controller;

import noticracia.core.Noticracia;
import noticracia.entities.InformationSource;
import noticracia.entities.WordCloud;
import ui.NoticraciaView;

import java.util.Map;

public class NoticraciaController {

    private final Noticracia noticracia;
    private final NoticraciaView noticraciaView;

    public NoticraciaController(NoticraciaView noticraciaView, Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaView = noticraciaView;
        this.noticraciaView.setController(this);
    }

    public void getWordCloud(String candidateName) {
        WordCloud wordCloud = noticracia.generateWordCloud(candidateName);
        noticraciaView.setWordCloud(wordCloud);
    }

    public void stopProcess() {
        noticraciaView.setProcessing(false);
    }

    public String[] getPoliticalCandidates() {
        return noticracia.getPoliticalCandidatesNames();
    }

    public Map<String, InformationSource> getInformationSources() {
        return noticracia.getInformationSources();
    }
}
