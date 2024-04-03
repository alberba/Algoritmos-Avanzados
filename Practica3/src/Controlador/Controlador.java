package Controlador;

import Modelo.Distribucion;
import Modelo.Modelo;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;
import java.util.Random;

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
        int n = modelo.getN();
        datos = new ArrayList<>();
        Random rand = new Random();

        switch (dist) { // Se generan los datos en función de la distribución
            case UNIFORME:
                for (int i = 0; i < n; i++) {
                    datos.add((int) (Math.random() * 100));
                }
                break;
            case GAUSSIANA:
                for (int i = 0; i < n; i++) {
                    datos.add((int) (rand.nextGaussian() * 100));
                }
                break;
            case EXPONENCIAL:

                break;
        }

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
            prog.notificar(NotiEnum.PARAR, null);
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
