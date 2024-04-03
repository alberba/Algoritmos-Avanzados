package Practica3.Modelo;

import AlgoritmosN.Main.Main;
import AlgoritmosN.NotiEnum;
import Practica3.Distribucion;
import Practica3.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final AlgoritmosN.Main.Main prog;
    private final ArrayList<Integer> arrayN;
    private Distribucion distribucion;
    // Tendran la estructura de: <<0, t1N10, t1N100, ...>, <0, t2N10, t2N100,...>, ...>
    private final ArrayList<ArrayList<Long>> tiemposN;
    public Modelo (Main p, ArrayList<Integer> datos) {
        prog = p;
        arrayN = new ArrayList<>();
        tiemposN = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tiemposN.add(new ArrayList<>());
        }

    }

    /**
     * Añade los tiempos de los algoritmos con respecto a n
     * @param n tamaño del array
     * @param tiempo tiempos de los algoritmos
     */
    public void addTiempoN(int n, long tiempo, int indice) {
        if (!arrayN.contains(n))
            arrayN.add(n);

        tiemposN.get(indice).add(tiempo);
        // Notificamos a la vista para que se actualice
        prog.getVista().notificar(NotiEnum.DIBUJAR);
    }

    /**
     * Resetea los valores de los arrays
     */
    public void reset() {
        arrayN.clear();
        arrayN.add(0);
        for (int i = 0; i < 3; i++) {
            tiemposN.get(i).clear();
            tiemposN.get(i).add(0L);
        }
    }

    public ArrayList<Integer> getArrayN() {
        return arrayN;
    }

    public ArrayList<ArrayList<Long>> getTiemposN() {
        return tiemposN;
    }

    public Distribucion getDistribucion() { return distribucion; }

    public void setDistribucion(Distribucion d) { distribucion = d; }

    @Override
    public void notificar(NotiEnum s, Object message) {

    }
}
