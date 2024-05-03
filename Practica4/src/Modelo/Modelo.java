package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    public Modelo (Main p) {
        prog = p;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETDISTRIBUCION -> {
            }
            case SETNBUCKET -> {
            }
            case SETN -> {
            }
        }
    }
}
