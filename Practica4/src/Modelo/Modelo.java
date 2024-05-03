package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private Grafo grafo;
    public Modelo (Main p, Grafo grafo) {
        this.prog = p;
        this.grafo = grafo;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public ArrayList<String> getPoblaciones() {
        return new ArrayList<>(grafo.getPoblaciones().keySet());
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETDISTRIBUCION -> {
            }
            case SETNBUCKET -> {
            }
            case SETN -> {
            }
        }
    }
}
