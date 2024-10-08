package Main;

import Controlador.Controlador;
import Modelo.Modelo;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;
import mesurament.Mesurament24;


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
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case INICIAR:
                if (controlador == null) {
                    controlador = new Controlador(this);
                    controlador.start();
                    vista.resetPanel();
                }
                break;
            case PARAR:
                // Se detiene el controlador
                if (controlador != null) {
                    controlador.notificar(NotiEnum.PARAR, null);
                    controlador = null;
                    vista.setValueProgreso(100);
                    vista.notificar(NotiEnum.DIBUJAR, null);
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