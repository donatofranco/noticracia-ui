package controller;

import noticracia.core.Noticracia;
import ui.NoticraciaView;

public class NoticraciaController {

    private Noticracia noticracia;
    private NoticraciaView noticraciaView;

    public NoticraciaController(NoticraciaView noticraciaView, Noticracia noticracia) {
        this.noticracia = noticracia;
        this.noticraciaView = noticraciaView;
        initializeInformationSources();
    }

    private void initializeInformationSources() {
        noticraciaView.updateInformationSources(noticracia.getInformationSourcesNames());
    }

    public void startProcess(String candidateName, String sourceName) {
        if (candidateName != null && sourceName != null) {
            boolean success = noticracia.selectSearchCriteria(sourceName, candidateName);
            noticraciaView.setProcessing(true);
            if (!success) {
                System.err.println("Error al iniciar la recolección de información.");
                noticraciaView.setProcessing(false);
            }
        }
    }

    public void stopProcess() {
        noticraciaView.setProcessing(false);
    }
}
