package Controlador;

import Controlador.TreeSort.TreeSort;
import Modelo.Distribucion;
import Modelo.Modelo;
import Modelo.Algoritmo;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.*;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private boolean interrumpir = false;
    private double pasos = 0;
    private static int nPasosTotal = 0;
    private ArrayList<Double> datos;

    public Controlador(Main p) {
        prog = p;
        prog.getModelo().reset();
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        Distribucion dist = modelo.getDistribucion();
        int n = modelo.getN();
        datos = new ArrayList<>();
        int nBuckets = modelo.getnBuckets();
        Random rand = new Random();
        // Se generan los datos en función de la distribución
        switch (dist) {
            case UNIFORME:
                for (int i = 0; i < n; i++) {
                    datos.add(Math.random());
                }
                break;
            case GAUSSIANA:
                double num;
                for (int i = 0; i < n; i++) {
                    num = rand.nextGaussian();
                    // Si el valor se aleja mucho de la media, se descarta el valor
                    while (num < -3 || num > 3) {
                        num = rand.nextGaussian();
                    }
                    // Se normalizan los datos, serán valores entre 0 y 1
                    datos.add((rand.nextGaussian() + 3) / 6);
                }
                break;

        }
        // Se ordenan los datos con cada uno de los algoritmos, se calcula el tiempo y se añade al modelo
        // Algoritmo 1
        // BUCKET //
        BucketSort bucketSort = new BucketSort(new ArrayList<>(datos), dist, nBuckets, modelo);
        bucketSort.start();
        // Algoritmo 2
        // TIMSORT //
        TimSort timSort = new TimSort(new ArrayList<>(datos), modelo);
        timSort.start();
        // Algoritmo 3
        // TREESORT //
        TreeSort treeSort = new TreeSort(new ArrayList<>(datos), modelo);
        treeSort.start();
        // Algoritmo 4
        // QUICKSORT //
        QuickSort quickSort = new QuickSort(new ArrayList<>(datos), modelo);
        quickSort.start();


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
