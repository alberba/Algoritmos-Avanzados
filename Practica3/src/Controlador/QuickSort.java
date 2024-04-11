package Controlador;

import Modelo.Modelo;
import Modelo.Algoritmo;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort extends Thread {

    ArrayList<Double> array;
    Modelo modelo;

    public QuickSort(ArrayList<Double> array, Modelo modelo) {
        this.array = array;
        this.modelo = modelo;
    }

    public void run() {
        long t = System.nanoTime();
        quickSort(array, 0, array.size() - 1);
        modelo.addTiempo(System.nanoTime() - t);
        modelo.añadirAlgoritmo(Algoritmo.QUICKSORT);
    }

    public void quickSort(ArrayList<Double> arr, int begin, int end) {
        if (begin < end) {
            int indiceParticion = particion(arr, begin, end);

            quickSort(arr, begin, indiceParticion - 1);
            quickSort(arr, indiceParticion+1, end);
        }
    }

    private int particion(ArrayList<Double> arr, int begin, int end) {
        double pivot = arr.get(arr.size() - 1);
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j) <= pivot) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, arr.size() - 1);

        return i+1;
    }

}
