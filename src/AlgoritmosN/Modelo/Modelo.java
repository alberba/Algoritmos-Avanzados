package AlgoritmosN.Modelo;

import AlgoritmosN.Main.Main;

import java.util.ArrayList;

public class Modelo {
    private Main prog;
    private final ArrayList<Integer> arrayN;
    // Tendran la estructura de: <<0, t1N10, t1N100, ...>, <0, t2N10, t2N100,...>, ...>
    private final ArrayList<ArrayList<Long>> tiemposN;
    public Modelo (Main p) {
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
    public void addTiempoN(int n, long[] tiempo){
        arrayN.add(n);

        tiemposN.get(0).add(tiempo[0]);
        tiemposN.get(1).add(tiempo[1]);
        tiemposN.get(2).add(tiempo[2]);
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
}
