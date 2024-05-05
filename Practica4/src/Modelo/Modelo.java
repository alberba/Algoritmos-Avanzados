package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;
import Main.ParserSAX;

import java.util.*;

public class Modelo implements Notificacion {
    private final Main prog;
    private Grafo grafo;
    private Algoritmo algoritmo = Algoritmo.PRIM;
    private ArrayList<Carretera> solucionPrim;
    private Poblacion origen = null;
    private Poblacion destino = null;
    private String xml = "src/poblaciones.xml";

    private static double minLat;
    private static double maxLat;
    private static double rangoLat;
    private static double minLon;
    private static double maxLon;
    private static double rangoLon;

    public Modelo (Main p, Grafo grafo) {
        this.prog = p;
        this.grafo = grafo;
        resetMinYMax();
        obtenerMinyMax(grafo.getPoblaciones());
    }

    /**
     * Obtiene el valor mínimo y máximo de latitud y longitud de las poblaciones de la muestra. Servirá para luego
     * poder escalar el mapa
     * @param poblaciones HashMap con las poblaciones
     */
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

    private void resetMinYMax() {
        minLat = 100;
        maxLat = -100;
        minLon = 100;
        maxLon = -100;
    }

    /**
     * Método que crea un nuevo grafo a partir de los parámetros pasados por el usuario
     * @param nPoblaciones Número de poblaciones
     * @param nMinCarreteras Número mínimo de carreteras
     */
    private void nuevoGrafo(int nPoblaciones, int nMinCarreteras) {
        if (xml.equals("src/poblaciones.xml")) {
            grafo = new Grafo(prog.generarPoblacionesGrafo(prog.getTotalPoblaciones(), nPoblaciones), nMinCarreteras);
        } else {
            HashMap<String, Poblacion> poblaciones = new ParserSAX().parse(xml);
            grafo = new Grafo(prog.generarPoblacionesGrafo(poblaciones, nPoblaciones), nMinCarreteras);
        }
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETALGORITMO -> algoritmo = (Algoritmo) message;
            case PARAMPUEBLO -> {
                // Los pueblos iran con el formato "1. Pueblo", hay que formatearlo
                String[] pueblos = (String[]) message;
                pueblos[0] = pueblos[0].substring(pueblos[0].indexOf(". ") + 2);
                pueblos[1] = pueblos[1].substring(pueblos[1].indexOf(". ") + 2);

                origen = grafo.getPoblacion(pueblos[0]);
                destino = grafo.getPoblacion(pueblos[1]);
            }
            case SETDATOSGRAFO -> {
                // Hay que generar un nuevo grafo con los datos pasados
                nuevoGrafo(((int[]) message)[0], ((int[]) message)[1]);
                resetMinYMax();
                obtenerMinyMax(grafo.getPoblaciones());
                // Actualizar vista
                prog.getVista().repaint();
            }
            case RESETGRAFO -> {
                // Se resetea el grafo
                nuevoGrafo(grafo.getPoblaciones().size(), grafo.getNumMinCarreteras());
                resetMinYMax();
                obtenerMinyMax(grafo.getPoblaciones());
                prog.getVista().repaint();
            }
            case SETXML -> {
                // Se parsea el nuevo xml
                xml = (String) message;
                HashMap<String, Poblacion> poblaciones = new ParserSAX().parse(xml);

                // Se crea un nuevo grafo con las poblaciones del xml
                int nPoblaciones = grafo.getPoblaciones().size();
                int nMinCarreteras = grafo.getNumMinCarreteras();
                grafo = new Grafo(prog.generarPoblacionesGrafo(poblaciones, nPoblaciones), nMinCarreteras);

                resetMinYMax();
                obtenerMinyMax(grafo.getPoblaciones());
                prog.getVista().repaint();
            }
        }
    }

    public static double getMinLat() { return minLat; }

    public static double getMinLon() { return minLon; }

    public static double getRangoLat() { return rangoLat; }

    public static double getRangoLon() { return rangoLon; }

    public Algoritmo getAlgoritmo() { return algoritmo; }

    public ArrayList<Carretera> getSolucionPrim() { return solucionPrim; }

    public void setSolucion(ArrayList<Carretera> sol) { this.solucionPrim = sol; }

    public Poblacion getOrigen() { return origen; }

    public Poblacion getDestino() { return destino; }

    public String getXml() { return xml; }

    public Grafo getGrafo() { return grafo; }

    public ArrayList<String> getPoblaciones() {
        return new ArrayList<>(grafo.getPoblaciones().keySet());
    }
}
