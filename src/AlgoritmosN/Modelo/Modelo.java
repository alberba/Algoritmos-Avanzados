package AlgoritmosN.Modelo;

import AlgoritmosN.Main.Main;

import java.util.ArrayList;

public class Modelo {
    private Main prog;
    private ArrayList<Integer> arrayN;
    // Tendran la estructura de: <<t1N10, t1N100, ...>, <t2N10, t2N100,...>, ...>
    private ArrayList<ArrayList<Integer>> tiemposN;
    public Modelo (Main p) {
        prog = p;
        arrayN = new ArrayList<>();
        tiemposN = new ArrayList<>();

    }

    public void addTiempoN(int n, int[] tiempo){
        arrayN.add(n);
        tiemposN.get(0).add(tiempo[0]);
        tiemposN.get(1).add(tiempo[1]);
        tiemposN.get(2).add(tiempo[2]);
    }

    public ArrayList<Integer> getArrayN() {
        return arrayN;
    }

    public ArrayList<ArrayList<Integer>> getTiemposN() {
        return tiemposN;
    }
}
