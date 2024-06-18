package Main;

import Controlador.Controlador;
import Controlador.Factorizador;
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

    public Main() {
        this.modelo = new Modelo(this);
        vista = new Vista("Algoritmos probabilistas", this);
        vista.mostrar();
        controlador = new Controlador(this);
    }

    public static void main(String[] args) {
        //Mesurament24.mesura();
        new Main();
    }




    @Override
    public void notificar(NotiEnum s, Object o) {
        if(NotiEnum.FACTORIZAR == s){
            modelo.notificar(NotiEnum.FACTORIZAR, o);
            controlador = new Controlador(this);
            Factorizador factorizador = new Factorizador(controlador);
            factorizador.factorizar((String) o);
        }
    }

    public Vista getVista() { return vista; }

    public Modelo getModelo() {
        return modelo;
    }


}