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
        modelo.añadirAlgoritmo(Algoritmo.Bucketsort);
    }

    private void bucketSort() {
        int nDatos = array.size();
        // Se generan los buckets
        List<TreeSet<Double>> buckets = new ArrayList<>();
        //int nBuckets = (int) Math.sqrt(nDatos); // Número de buckets más indicado = sqrt(n)
        for (int i = 0; i < nBuckets; i++) {
            buckets.add(new TreeSet<>());
        }
        // Según el tipo de distribución, se añaden los elementos a los buckets de una forma u otra
        if (dist == Distribucion.UNIFORME) {
            for (double element : array) {
                int indexBucket = (int) (element * nBuckets);
                buckets.get(indexBucket).add(element);
            }
        } else {
            int[] offsetIndiceBucket = {0, (int) (0.09 * nBuckets), (int) (0.25 * nBuckets), (int) (0.5 * nBuckets), (int) (0.75 * nBuckets), (int) (0.91 * nBuckets)}; // 6 valores, uno para cada franja de buckets
            int franja;
            Random rng = new Random();
            int indiceBucket; // Índice dentro de la franja
            for (double element : array) {
                // Se asigna el elemento a un bucket en función de la franja a la que pertenezca
                if (element < 0.3) {
                    // 9% de los buckets
                    franja = 0;
                    // Se normaliza para obtener el índice
                    // ((elemento - valor máximo anterior a la franja) / rango de valores (valMax - valMin)) * %buckets
                    indiceBucket = (int) (element / 0.3) * 9;
                } else if (element < 0.4) {
                    // 16% de los buckets
                    franja = 1;
                    indiceBucket = (int) ((element - 0.3) / 0.1) * 16;
                } else if (element < 0.5) {
                    // 25% de los buckets
                    franja = 2;
                    indiceBucket = (int) ((element - 0.4) / 0.1) * 25;
                } else if (element < 0.6) {
                    // 25% de los buckets
                    franja = 3;
                    indiceBucket = (int) ((element - 0.5) / 0.1) * 25;
                } else if (element < 0.7) {
                    // 16% de los buckets
                    franja = 4;
                    indiceBucket = (int) ((element - 0.6) / 0.1) * 16;
                } else {
                    // 9% de los buckets
                    franja = 5;
                    indiceBucket = (int) ((element - 0.7) / 0.3) * 9;
                }
                // Índice = índice dentro de la franja + índice de offset de la franja
                indiceBucket = indiceBucket + offsetIndiceBucket[franja];
                buckets.get(indiceBucket).add(element);
            }
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
