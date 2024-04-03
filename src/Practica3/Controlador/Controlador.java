package Practica3.Controlador;

import Practica3.Distribucion;
import Practica3.Modelo.Modelo;
import Practica3.Main.Main;
import Practica3.NotiEnum;
import Practica3.Notificacion;

import java.util.ArrayList;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private boolean interrumpir = false;
    private double pasos = 0;
    private static int nPasosTotal = 0;
    private ArrayList<Integer> datos;

    public Controlador(Main p) {
        prog = p;
        prog.getModelo().reset();
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        Distribucion dist = modelo.getDistribucion();

        datos = modelo.getDatos().clone;

        // Se ordenan los datos con cada uno de los algoritmos, se calcula el tiempo y se añade al modelo
        // TIN //
        // Se obtiene el tiempo actual
        long tin = System.nanoTime();
        // Se ordenan los datos

        // Se obtiene el tiempo actual
        long tiempo = System.nanoTime() - tin;
        switch (dist) {
            case UNIFORME: // Buckets uniformes

                break;
            case GAUSSIANA: // Buckets adecuados a la gaussiana

                break;
            case EXPONENCIAL:
                break;
        }

        // METODOS DE ORDENACIÓN
        long tini = System.nanoTime();


        if (!interrumpir) {
            prog.notificar(Practica3.NotiEnum.PARAR, null);
            System.out.println("Fin de las ejecuciones");
        } else {
            // Se notifica mediante la consola de salida que se ha interrumpido el proceso
            System.out.println("Interrumpido");
        }
    }

    /**
     * Método encargado de actualizar el progreso en función de los pasos realizados hasta el momento
     * y el número de pasos totales a realizar
     */
    public void actualizarProgreso() {
        pasos++;
        while (pasos >=  (nPasosTotal * 1.0) / 100) {
            // Aumentamos el porcentaje tantas veces como sea necesario
            // Esto puede suceder en caso de que los polígonos totales a dibujar
            // sea menor a 100
            pasos -= (nPasosTotal * 1.0) / 100;
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
