package Controlador;

import Modelo.Modelo;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.*;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private boolean interrumpir = false;
    private int pasos = 0;
    private ArrayList<Double> datos;

    public Controlador(Main p) {
        prog = p;
    }

    public void run() {

    }

    /**
     * Método encargado de actualizar el progreso en función de los pasos realizados hasta el momento
     * y el número de pasos totales a realizar
     */
    public void actualizarProgreso() {
        pasos++;
        if (pasos % 1000 == 0) {
            prog.getVista().notificar(NotiEnum.PROGRESO, null);
        }
    }


    @Override
    public void notificar(NotiEnum s, Object o) {
        if (s == NotiEnum.PARAR) {
            interrumpir = true;
        }
    }
}
