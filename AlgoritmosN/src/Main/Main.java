package Main;

import Controlador.Controlador;
import mesurament.Mesurament24;
import Modelo.Modelo;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;


public class Main implements Notificacion {
    private final Vista vista;
    private final Modelo modelo;
    private Controlador controlador;

    public Main() {
        controlador = null;
        modelo = new Modelo(this);
        vista = new Vista("AlgoritmosN", this);
        vista.mostrar();
    }

    public static void main(String[] args) {
        Mesurament24.mesura();
        new Main();
    }

    @Override
    public void notificar(NotiEnum s) {
        switch (s) {
            case INICIAR:
                if (controlador == null) {
                    controlador = new Controlador(this);
                    controlador.start();
                }
                break;
            case PARAR:
                // Se detiene el controlador
                if (controlador != null) {
                    controlador.notificar(NotiEnum.PARAR);
                    controlador = null;
                    vista.setValueProgreso(100);
                    vista.notificar(NotiEnum.DIBUJAR);
                }
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