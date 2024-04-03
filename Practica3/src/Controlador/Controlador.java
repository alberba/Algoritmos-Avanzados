package Controlador;

import Controlador.TreeSort.TreeSort;
import Modelo.Distribucion;
import Modelo.Modelo;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Controlador extends Thread implements Notificacion {

    private final Main prog;
    private boolean interrumpir = false;
    private double pasos = 0;
    private static int nPasosTotal = 0;
    private ArrayList<Double> datos;
    private long [] tiempos;

    public Controlador(Main p) {
        prog = p;
        prog.getModelo().reset();
        tiempos = new long[4];
    }

    public void run() {
        Modelo modelo = prog.getModelo();
        Distribucion dist = modelo.getDistribucion();
        int n = modelo.getN();
        datos = new ArrayList<>();
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
            case EXPONENCIAL:

                break;
        }
        // Se ordenan los datos con cada uno de los algoritmos, se calcula el tiempo y se añade al modelo
        // TIN //
        long t = System.nanoTime();
        timsort(new ArrayList<>(datos));
        tiempos[0] = System.nanoTime() - t;

        // BUCKET //
        t = System.nanoTime();
        bucketSort(new ArrayList<>(datos), dist);
        tiempos[1] = System.nanoTime() - t;

        // TREESORT //
        t = System.nanoTime();
        TreeSort treeSort = new TreeSort();
        treeSort.sort(new ArrayList<>(datos));
        tiempos[2] = System.nanoTime() - t;

        if (!interrumpir) {
            prog.notificar(NotiEnum.PARAR, null);
            System.out.println("Fin de las ejecuciones");
        } else {
            // Se notifica mediante la consola de salida que se ha interrumpido el proceso
            System.out.println("Interrumpido");
        }
    }

    private void timsort(ArrayList<Double> arr) {
        Collections.sort(arr);
    }

    private void bucketSort(ArrayList<Double> arr, Enum<Distribucion> dist) {
        int nDatos = arr.size();
        // Se generan los buckets
        List<List<Double>> buckets = new ArrayList<>();
        int numBuckets = (int) Math.sqrt(nDatos); // Número de buckets más indicado = sqrt(n)
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<>());
        }
        // Según el tipo de distribución, se añaden los elementos a los buckets de una forma u otra
        if (dist == Distribucion.UNIFORME) {
            for (double element : arr) {
                int indexBucket = (int) element * nDatos;
                buckets.get(indexBucket).add(element);
            }
        } else {
            for (double element : arr) {
                if (element < 0.3) {
                    // 9% de los buckets
                } else if (element < 0.4) {
                    // 16% de los buckets
                } else if (element < 0.5) {
                    // 25% de los buckets
                } else if (element < 0.6) {
                    // 25% de los buckets
                } else if (element < 0.7) {
                    // 16% de los buckets
                } else {
                    // 9% de los buckets
                }
                int indexBucket = (int) Math.pow(element, 2) * numBuckets;
                buckets.get(indexBucket).add(element);
            }
        }


        for (int i = 0; i < nDatos; i++) {
            Collections.sort(buckets.get(i));
        }

        int index = 0;
        for (int i = 0; i < nDatos; i++) {
            for (int j = 0; j < nDatos; j++) {
                arr.set(index++, buckets.get(i).get(j));
            }
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
