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
    public Modelo (Main p, ArrayList<Integer> datos) {
        prog = p;
        tiempos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        n = 30000; // Por defecto
        nBuckets = (int) Math.sqrt(n); // Por defecto
        distribucion = Distribucion.UNIFORME; // Por defecto
    }

    /**
     * Añade los tiempos de los algoritmos con respecto a n
     * @param n tamaño del array
     * @param tiempo tiempos de los algoritmos
     */
    public void addTiempo(long tiempo) {
        tiempos.add(tiempo);
        // Notificamos a la vista para que se actualice
        //prog.getVista().notificar(NotiEnum.DIBUJAR, null);No sé si dejarlo ¿?
    }

    /**
     * Resetea los valores de los arrays
     */
    public void reset() {
        tiempos.clear();
    }

    public ArrayList<Long> getTiempos() {
        return tiempos;
    }

    public void añadirAlgoritmo(Algoritmo algoritmo) {
        algoritmos.add(algoritmo);
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
