package Controlador;

import Modelo.Modelo;
import Modelo.AlgoritmoEnum;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort extends Thread {

    ArrayList<Double> array;
    Modelo modelo;
    Controlador controlador;

    public QuickSort(ArrayList<Double> array, Modelo modelo, Controlador controlador) {
        this.array = array;
        this.modelo = modelo;
        this.controlador = controlador;
    }

    public void run() {
        long t = System.nanoTime();
        quickSort(array, 0, array.size() - 1);
        modelo.addTiempo(System.nanoTime() - t, AlgoritmoEnum.QUICKSORT);
        System.out.println("Tiempo Quicksort: " + (System.nanoTime() - t) + " ns");
    }

    public void quickSort(ArrayList<Double> arr, int begin, int end) {
        if (begin < end) {
            int indiceParticion = particion(arr, begin, end);

            quickSort(arr, begin, indiceParticion - 1);
            quickSort(arr, indiceParticion+1, end);
        }
    }

    private int particion(ArrayList<Double> arr, int begin, int end) {
        double pivot = arr.get(end);
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            controlador.actualizarProgreso();
            if (arr.get(j) <= pivot) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, end);

        return i+1;
    }

}
