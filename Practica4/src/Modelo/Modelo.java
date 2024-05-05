package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;
import Main.ParserSAX;

import java.lang.reflect.Array;
import java.util.*;

public class Modelo implements Notificacion {
    private final Main prog;
    private Grafo grafo;
    private Algoritmo algoritmo;
    private ArrayList<Carretera> solucionPrim;
    private Poblacion origen;
    private Poblacion destino;
    private String xml;

    private static double minLat;
    private static double maxLat;
    private static double rangoLat;
    private static double minLon;
    private static double maxLon;
    private static double rangoLon;

    public Modelo (Main p, Grafo grafo) {
        this.prog = p;
        this.grafo = grafo;
        //origen = null;
        //destino = null;
        //randomOrigenYDestino();
        minLat = 100;
        maxLat = -100;
        minLon = 100;
        maxLon = -100;
        obtenerMinyMax(grafo.getPoblaciones());
    }

    // Método que permite reinicializar el modelo con los datos de un nuevo xml
    public void reset() {
        ParserSAX parserSAX = new ParserSAX();
        if (!xml.equals("poblaciones.xml")) {
            xml = "poblaciones2";
            HashMap<String, Poblacion> poblaciones = parserSAX.parse("src/" + xml);
            this.grafo = new Grafo(poblaciones);
            //randomOrigenYDestino();
            minLat = 100;
            maxLat = -100;
            minLon = 100;
            maxLon = -100;
        } else {
            int a = 0;
        }
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

    public void setSolucion(ArrayList<Carretera> sol) {
        this.solucionPrim = sol;
    }

    public void randomOrigenYDestino() {
        ArrayList<String> poblaciones = getPoblaciones();
        Random random = new Random();
        int origenIndex = random.nextInt(poblaciones.size());
        int destinoIndex = random.nextInt(poblaciones.size());
        while (origenIndex == destinoIndex) {
            destinoIndex = random.nextInt(poblaciones.size());
        }
        System.out.println("Indice origen:" + (origenIndex + 1));
        System.out.println("Indice destino:" + (destinoIndex + 1));
        origen = grafo.getPoblacion(poblaciones.get(origenIndex));
        destino = grafo.getPoblacion(poblaciones.get(destinoIndex));
    }

    public Poblacion getOrigen() {
        return origen;
    }

    public void setOrigen(Poblacion origen) {
        this.origen = origen;
    }

    public Poblacion getDestino() {
        return destino;
    }

    public void setDestino(Poblacion destino) {
        this.destino = destino;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETALGORITMO -> {
                algoritmo = (Algoritmo) message;
            }
        }
    }
}
