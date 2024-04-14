package Controlador;

import Modelo.Distribucion;

import java.util.*;
import Modelo.Modelo;
import Modelo.AlgoritmoEnum;

public class BucketSort extends Thread {

    ArrayList<Double> array;
    Distribucion dist;
    int nBuckets;
    Modelo modelo;
    Controlador controlador;

    public BucketSort(ArrayList<Double> array, Distribucion dist, int nBuckets, Modelo modelo, Controlador controlador) {
        this.array = array;
        this.dist = dist;
        this.nBuckets = nBuckets;
        this.modelo = modelo;
        this.controlador = controlador;
    }

    public void run() {
        long t = System.nanoTime();
        bucketSort();
        modelo.addTiempo(System.nanoTime() - t, AlgoritmoEnum.BUCKETSORT);
        System.out.println("Tiempo Bucketsort: " + (System.nanoTime() - t) + " ns");
    }

    private void bucketSort() {
        // Se generan los buckets
        List<List<Double>> buckets = new ArrayList<>();
        //int nBuckets = (int) Math.sqrt(nDatos); // Número de buckets más indicado = sqrt(n)
        for (int i = 0; i < nBuckets; i++) {
            buckets.add(new ArrayList<Double>());
        }
        // Según el tipo de distribución, se añaden los elementos a los buckets de una forma u otra
        if (dist == Distribucion.UNIFORME) {
            for (double elemento : array) {
                controlador.actualizarProgreso();
                int indexBucket = (int) (elemento * nBuckets);
                if (indexBucket == nBuckets) { // Caso de elemento == 1.0
                    indexBucket--;
                }
                buckets.get(indexBucket).add(elemento);
            }
        } else {
            int[] offsetIndiceBucket = {0, (int) (0.023 * nBuckets), (int) (0.159 * nBuckets), (int) (0.5 * nBuckets), (int) (0.841 * nBuckets), (int) (0.977 * nBuckets)}; // 6 valores, uno para cada franja de buckets
            int franja;
            int indiceBucket; // Índice dentro de la franja
            for (double elemento : array) {
                controlador.actualizarProgreso();
                // Se asigna el elemento a un bucket en función de la franja a la que pertenezca
                if (elemento < 0.16667) {
                    // 2.2% de los buckets
                    franja = 0;
                    // Se normaliza para obtener el índice
                    // ((elemento - valor máximo anterior a la franja) / rango de valores (valMax - valMin)) * %buckets
                    indiceBucket = (int) ((elemento / 0.16667) * 0.023 * nBuckets);
                } else if (elemento < 0.33333) {
                    // 13.6% de los buckets
                    franja = 1;
                    indiceBucket = (int) (((elemento - 0.16667) / 0.16667) * 0.136 * nBuckets);
                } else if (elemento < 0.5) {
                    // 34.1% de los buckets
                    franja = 2;
                    indiceBucket = (int) (((elemento - 0.33333) / 0.16667) * 0.341 * nBuckets);
                } else if (elemento < 0.66667) {
                    // 34.1% de los buckets
                    franja = 3;
                    indiceBucket = (int) (((elemento - 0.5) / 0.16667) * 0.341 * nBuckets);
                } else if (elemento < 0.83333) {
                    // 13.6% de los buckets
                    franja = 4;
                    indiceBucket = (int) (((elemento - 0.66667) / 0.16667) * 0.136 * nBuckets);
                } else {
                    // 2.2% de los buckets
                    franja = 5;
                    indiceBucket = (int) (((elemento - 0.83333) / 0.16667) * 0.023 * nBuckets);
                }
                // Índice = índice dentro de la franja + índice de offset de la franja
                indiceBucket = indiceBucket + offsetIndiceBucket[franja];
                if (indiceBucket == nBuckets) { // Caso elemento == 1.0
                    indiceBucket--;
                }
                buckets.get(indiceBucket).add(elemento);
            }
        }

        // Se ordenan los buckets
        for (int i = 0; i < nBuckets; i++) {
            Collections.sort(buckets.get(i));
        }

        int index = 0;
        for (int i = 0; i < nBuckets; i++) {
            controlador.actualizarProgreso();
            Iterator<Double> iterator = buckets.get(i).iterator();
            for (int j = 0; j < buckets.get(i).size(); j++) {
                array.set(index++, iterator.next());
            }
        }

    }

}
