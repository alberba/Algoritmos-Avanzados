package Main;

import Controlador.Controlador;
import Modelo.Modelo;
import Modelo.Poblacion;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;
import Modelo.Grafo;

import java.util.HashMap;


public class Main implements Notificacion {
    private final Vista vista;
    private final Modelo modelo;
    private Controlador controlador;

    public Main() {
        // Lectura del XML
        ParserSAX parserSAX = new ParserSAX();
        HashMap<String, Poblacion> poblaciones = parserSAX.parse("src/poblaciones.xml");
        //imprimirMayoryMenorLatyLon(poblaciones);
        modelo = new Modelo(this, new Grafo(generarPoblacionesGrafo(poblaciones)));
        controlador = new Controlador(this);
        vista = new Vista("Grafo", this);
        vista.mostrar();
    }

    private void imprimirMayoryMenorLatyLon(HashMap<String, Poblacion> poblaciones) {
        Poblacion poblacionMayorLat = null;
        Poblacion poblacionMenorLat = null;
        Poblacion poblacionMayorLon = null;
        Poblacion poblacionMenorLon = null;
        for (Poblacion poblacion : poblaciones.values()) {
            if (poblacionMayorLat == null || poblacion.getLat() > poblacionMayorLat.getLat()) {
                poblacionMayorLat = poblacion;
            }
            if (poblacionMenorLat == null || poblacion.getLat() < poblacionMenorLat.getLat()) {
                poblacionMenorLat = poblacion;
            }
            if (poblacionMayorLon == null || poblacion.getLon() > poblacionMayorLon.getLon()) {
                poblacionMayorLon = poblacion;
            }
            if (poblacionMenorLon == null || poblacion.getLon() < poblacionMenorLon.getLon()) {
                poblacionMenorLon = poblacion;
            }
        }
        System.out.println("Población con mayor latitud: " + poblacionMayorLat.getPoblacion() + ", latitud: " + poblacionMayorLat.getLat());
        System.out.println("Población con menor latitud: " + poblacionMenorLat.getPoblacion() + ", latitud: " + poblacionMenorLat.getLat());
        System.out.println("Población con mayor longitud: " + poblacionMayorLon.getPoblacion() + ", longitud: " + poblacionMayorLon.getLon());
        System.out.println("Población con menor longitud: " + poblacionMenorLon.getPoblacion() + ", longitud: " + poblacionMenorLon.getLon());
    }

    public static void main(String[] args) {
        //Mesurament24.mesura();
        new Main();
    }

    // Método que recopila 20 poblaciones del hashmap de poblaciones
    public HashMap<String, Poblacion> generarPoblacionesGrafo(HashMap<String, Poblacion> poblaciones) {
        HashMap<String, Poblacion> poblacionesRecopiladas = new HashMap<>();
        int i = 0;
        for (String key : poblaciones.keySet()) {
            if (i == 20) {
                break;
            }
            poblacionesRecopiladas.put(key, poblaciones.get(key));
            i++;
        }
        return poblacionesRecopiladas;
    }

    private static void leerXML() {
        ParserSAX parserSAX = new ParserSAX();
        HashMap<String, Poblacion> poblaciones = parserSAX.parse("src/poblaciones.xml");
        Grafo grafo = new Grafo(poblaciones);
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case INICIAR:
                break;
            case PARAR:
                // Se detiene el controlador
                break;
            default:
                break;
        }
    }

    public Vista getVista() {
        return vista;
    }

    public Modelo getModelo() {
        return modelo;
    }

}