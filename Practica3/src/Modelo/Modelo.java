package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private int n;
    private int nBuckets;
    private Distribucion distribucion;
    private final ArrayList<Long> tiempos;
    private final ArrayList<Algoritmo> algoritmos;
    public Modelo (Main p) {
        prog = p;
        tiempos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        n = 30000; // Por defecto
        nBuckets = (int) Math.sqrt(n); // Por defecto
        distribucion = Distribucion.UNIFORME; // Por defecto
    }

    /**
     * Añade los tiempos de los algoritmos
     * @param tiempo tiempos de los algoritmos
     */
    public void addTiempo(long tiempo, Algoritmo algoritmo) {
        tiempos.add(tiempo);
        algoritmos.add(algoritmo);
        // Notificamos a la vista para que se actualice
        prog.getVista().notificar(NotiEnum.DIBUJAR, null);
    }

    /**
     * Resetea los valores de los arrays
     */
    public void reset() {
        algoritmos.clear();
        tiempos.clear();
    }

    public ArrayList<Long> getTiempos() {
        return tiempos;
    }

    public Algoritmo getAlgoritmo(int i) {
        return algoritmos.get(i);
    }

    public Distribucion getDistribucion() { return distribucion; }

    public int getnBuckets() { return nBuckets; }

    public void setDistribucion(Distribucion d) { distribucion = d; }

    public int getN() { return n; }

    public void setN(int n) { this.n = n; }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETDISTRIBUCION -> distribucion = (Distribucion) message;
            case SETNBUCKET -> nBuckets = (int) message;
            case SETN -> n = (int) message;
        }
    }
}
