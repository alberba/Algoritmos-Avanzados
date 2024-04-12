package Controlador;

import Modelo.Distribucion;

import java.util.*;
import Modelo.Modelo;
import Modelo.Algoritmo;

public class BucketSort extends Thread {

    ArrayList<Double> array;
    Distribucion dist;
    int nBuckets;
    Modelo modelo;

    public BucketSort(ArrayList<Double> array, Distribucion dist, int nBuckets, Modelo modelo) {
        this.array = array;
        this.dist = dist;
        this.nBuckets = nBuckets;
    }

    public void run() {
        long t = System.nanoTime();
        bucketSort();
        modelo.addTiempo(System.nanoTime() - t);
        modelo.añadirAlgoritmo(Algoritmo.BUCKETSORT);
    }

    private void bucketSort() {
        int nDatos = array.size();
        // Se generan los buckets
        List<List<Double>> buckets = new ArrayList<>();
        //int nBuckets = (int) Math.sqrt(nDatos); // Número de buckets más indicado = sqrt(n)
        for (int i = 0; i < nBuckets; i++) {
            buckets.add(new ArrayList<Double>());
        }
        // Según el tipo de distribución, se añaden los elementos a los buckets de una forma u otra
        if (dist == Distribucion.UNIFORME) {
            for (double element : array) {
                int indexBucket = (int) (element * nBuckets);
                buckets.get(indexBucket).add(element);
            }
        } else {
            int[] offsetIndiceBucket = {0, (int) (0.023 * nBuckets), (int) (0.159 * nBuckets), (int) (0.5 * nBuckets), (int) (0.841 * nBuckets), (int) (0.977 * nBuckets)}; // 6 valores, uno para cada franja de buckets
            int franja;
            int indiceBucket; // Índice dentro de la franja
            for (double element : array) {
                // Se asigna el elemento a un bucket en función de la franja a la que pertenezca
                if (element < 0.3) {
                    // 2.2% de los buckets
                    franja = 0;
                    // Se normaliza para obtener el índice
                    // ((elemento - valor máximo anterior a la franja) / rango de valores (valMax - valMin)) * %buckets
                    indiceBucket = (int) ((element / 0.3) * 0.023 * nBuckets);
                } else if (element < 0.4) {
                    // 13.6% de los buckets
                    franja = 1;
                    indiceBucket = (int) (((element - 0.3) / 0.1) * 0.136 * nBuckets);
                } else if (element < 0.5) {
                    // 34.1% de los buckets
                    franja = 2;
                    indiceBucket = (int) (((element - 0.4) / 0.1) * 0.341 * nBuckets);
                } else if (element < 0.6) {
                    // 34.1% de los buckets
                    franja = 3;
                    indiceBucket = (int) (((element - 0.5) / 0.1) * 0.341 * nBuckets);
                } else if (element < 0.7) {
                    // 13.6% de los buckets
                    franja = 4;
                    indiceBucket = (int) (((element - 0.6) / 0.1) * 0.136 * nBuckets);
                } else {
                    // 2.2% de los buckets
                    franja = 5;
                    indiceBucket = (int) (((element - 0.7) / 0.3) * 0.023 * nBuckets);
                }
                // Índice = índice dentro de la franja + índice de offset de la franja
                indiceBucket = indiceBucket + offsetIndiceBucket[franja];
                buckets.get(indiceBucket).add(element);
            }


            // Se ordenan los buckets
            for (int i = 0; i < nBuckets; i++) {
                Collections.sort(buckets.get(i));
            }

            int index = 0;
            for (int i = 0; i < nDatos; i++) {
                Iterator<Double> iterator = buckets.get(i).iterator();
                for (int j = 0; j < nDatos; j++) {
                    array.set(index++, iterator.next());
                }
            }
        }
    }

}
