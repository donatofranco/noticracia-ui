package instancia;

import noticracia.entities.InformationSource;

import java.util.Map;


//SABEMOS QUE ESTO NO VA ACA, VA EN UN PROYECTO APARTE
public class ClarinInformationSource extends InformationSource {
    @Override
    public void startInformationCollection(String politicianName) {
        /*
            COSAS COSAS COSAS
         */
        notifyObservers(Map.of("link", "textito"));
    }
}
