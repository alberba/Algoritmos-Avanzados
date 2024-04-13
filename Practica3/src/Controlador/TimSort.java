package Controlador;

import Modelo.Modelo;
import Modelo.Algoritmo;

import java.util.ArrayList;
import java.util.Collections;

public class TimSort extends Thread {

    ArrayList<Double> array;
    Modelo modelo;

    public TimSort(ArrayList<Double> array, Modelo modelo) {
        this.array = array;
        this.modelo = modelo;
    }

    public void run() {
        long t = System.nanoTime();
        Collections.sort(array);
        modelo.addTiempo(System.nanoTime() - t);
        modelo.añadirAlgoritmo(Algoritmo.TIMSORT);
    }

}
