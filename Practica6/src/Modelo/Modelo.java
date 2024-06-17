package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;
import java.util.TreeMap;

public class Modelo implements Notificacion {
    private final Main prog;

    public Modelo (Main p) {
        this.prog = p;
    }

    /**
     * Constructor objeto Modelo
     * @param p Objeto Main
     * @param texto Texto modelo
     */
    public Modelo(Main p, String texto) {
        this.prog = p;
    }


    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {

        }
    }
}
