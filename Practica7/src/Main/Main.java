package Main;

import Controlador.Controlador;
import Controlador.Factorizador;
import Modelo.Modelo;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;
import mesurament.Mesurament24;

import java.io.IOException;
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
    public void notificar(NotiEnum s, Object o)  {
        if(NotiEnum.FACTORIZAR == s){
            modelo.notificar(NotiEnum.FACTORIZAR, o);
            controlador = new Controlador(this);
            Factorizador factorizador = new Factorizador(this, controlador, (String) o);
            factorizador.start();
        } else if(NotiEnum.COMPRIMIR == s){
            try {
                controlador.comprimirArchivo((String) o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (NotiEnum.RSA == s) {
            controlador.RSA();
        } else if (NotiEnum.ENCRIPTAR == s) {
            controlador.encriptarArchivo((String) o);
        } else if (NotiEnum.DESENCRIPTAR == s) {
            controlador.desencriptarArchivo((String) o);
        }
    }

    public Vista getVista() { return vista; }

    public Modelo getModelo() {
        return modelo;
    }


}