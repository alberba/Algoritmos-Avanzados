package Main;

import Controlador.Controlador;
import Modelo.Modelo;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;
import mesurament.Mesurament24;

import java.util.*;


public class Main implements Notificacion {
    private final Vista vista;
    private Modelo modelo;
    private Controlador controlador;
    private ArrayList<String> diccionario;

    public Main() {
        this.modelo = new Modelo(this);
        vista = new Vista("Corrector ortográfico", this);
        vista.mostrar();
        controlador = new Controlador(this);
        controlador.run();
    }

    public static void main(String[] args) {
        //Mesurament24.mesura();
        new Main();
    }




    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case DETIDIOMA:
                controlador = new Controlador(this);
                this.modelo = new Modelo(this, (String) o);
                controlador.detectarIdioma(modelo.getTexto().getTexto());
                break;
            default:
                break;
        }
    }

    public Vista getVista() { return vista; }

    public Modelo getModelo() {
        return modelo;
    }


}