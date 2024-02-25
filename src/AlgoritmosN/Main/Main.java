package AlgoritmosN.Main;

import AlgoritmosN.NotiEnum;
import AlgoritmosN.Notificacion;
import AlgoritmosN.Vista.Vista;
import AlgoritmosN.Modelo.Modelo;
import AlgoritmosN.Controlador.Controlador;
import mesurament.Mesurament24;


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
                controlador = null;
                vista.repaint();
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