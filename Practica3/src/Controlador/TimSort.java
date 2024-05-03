package Controlador;

import Modelo.Modelo;
import Modelo.AlgoritmoEnum;

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
        modelo.addTiempo(System.nanoTime() - t, AlgoritmoEnum.TIMSORT);
        System.out.println("Tiempo Timsort: " + (System.nanoTime() - t) + " ns");
    }

}
