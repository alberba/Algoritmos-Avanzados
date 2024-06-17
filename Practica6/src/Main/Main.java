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

    public Main() {
        this.modelo = new Modelo(this);
        vista = new Vista("Algoritmos probabilistas", this);
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
        if (Objects.requireNonNull(s) == NotiEnum.INICIAR) {
            this.modelo = new Modelo(this, (String) o);
            controlador = new Controlador(this);
            controlador.run();
        }
    }

    public Vista getVista() { return vista; }

    public Modelo getModelo() {
        return modelo;
    }


}