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
        controlador = null;
        modelo = new Modelo(this);
        vista = new Vista("Tiempos", this);
        vista.mostrar();
    }

    public static void main(String[] args) {
        //Mesurament24.mesura();
        new Main();
        leerXML();
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