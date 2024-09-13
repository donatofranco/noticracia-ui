package instancia;

import noticracia.entities.InformationSource;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


//SABEMOS QUE ESTO NO VA ACA, VA EN UN PROYECTO APARTE
public class ClarinInformationSource extends InformationSource {
    @Override
    public void startInformationCollection(String politicianName) {
        /*
            COSAS COSAS COSAS
         */
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000); // Pausa por 5 segundos
                setChanged();
                notifyObservers(Map.of("link", "textito"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("El hilo fue interrumpido");
            }
        });
    }
}
