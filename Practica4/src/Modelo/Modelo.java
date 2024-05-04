package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Modelo implements Notificacion {
    private final Main prog;
    private Grafo grafo;
    private Algoritmo algoritmo;
    private ArrayList<Carretera> solucionPrim;

    private static double minLat;
    private static double maxLat;
    private static double rangoLat;
    private static double minLon;
    private static double maxLon;
    private static double rangoLon;

    public Modelo (Main p, Grafo grafo) {
        this.prog = p;
        this.grafo = grafo;
        minLat = 100;
        maxLat = -100;
        minLon = 100;
        maxLon = -100;
        obtenerMinyMax(grafo.getPoblaciones());
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public ArrayList<String> getPoblaciones() {
        return new ArrayList<>(grafo.getPoblaciones().keySet());
    }

    private void obtenerMinyMax(HashMap<String, Poblacion> poblaciones) {
        for (Poblacion poblacion : poblaciones.values()) {
            if (poblacion.getLat() > maxLat) {
                maxLat = poblacion.getLat();
            }
            if (poblacion.getLat() < minLat) {
                minLat = poblacion.getLat();
            }
            if (poblacion.getLon() > maxLon) {
                maxLon = poblacion.getLon();
            }
            if (poblacion.getLon() < minLon) {
                minLon = poblacion.getLon();
            }
        }

        rangoLat = maxLat - minLat;
        rangoLon = maxLon - minLon;
    }

    public static double getMinLat() {
        return minLat;
    }

    public static double getMaxLat() {
        return maxLat;
    }

    public static double getMinLon() {
        return minLon;
    }
    public static double getMaxLon() { return maxLon; }
    public static double getRangoLat() {
        return rangoLat;
    }

    public static double getRangoLon() { return rangoLon; }

    public Algoritmo getAlgoritmo() { return algoritmo; }

    public ArrayList<Carretera> getSolucionPrim() {
        return solucionPrim;
    }

    public void setSolucionPrim(ArrayList<Carretera> sol) {
        this.solucionPrim = sol;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETALGORITMO -> {
                algoritmo = (Algoritmo) message;
            }
            case SETNBUCKET -> {
            }
        }
    }
}
