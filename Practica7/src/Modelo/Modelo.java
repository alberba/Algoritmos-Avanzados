package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.TreeMap;

public class Modelo implements Notificacion {
    private BigInteger expPrivado, expPublico, modulo;
    TreeMap<Integer, Long> tiempos; // Almacena los tiempos de factorizar según las cifras del valor factorizado
    private final Main prog;

    public Modelo (Main p) {
        this.prog = p;
        this.tiempos = new TreeMap<>();
    }

    // Bits necesarios para un valor de 600 dígitos (aprox)
    public int getBitLength() {
        return 1995;
    }
    public void setExpPrivado(BigInteger expPriv) {
        this.expPrivado = expPriv;
    }
    public void setExpPublico(BigInteger expPubli) {
        this.expPublico = expPubli;
    }
    public void setModulo(BigInteger modul){
        this.modulo = modul;
    }

    public ArrayList<Integer> getCifras() {
        // Se devuelven las keys del TreeMap tiempos
        return new ArrayList<>(this.tiempos.keySet());
    }

    public ArrayList<Long> getTiempos() {
        ArrayList<Long> tiempos = new ArrayList<>();
        // Se devuelven los values de cada key del TreeMap tiempos
        for (int key : this.tiempos.keySet()) {
            tiempos.add(this.tiempos.get(key));
        }
        return tiempos;
    }

    public BigInteger[] getClavePublica() {
        BigInteger[] clavePub = new BigInteger[2];
        clavePub[0] = this.expPublico;
        clavePub[1] = this.modulo;
        return clavePub;
    }

    public BigInteger[] getClavePrivada() {
        BigInteger[] clavePriv = new BigInteger[2];
        clavePriv[0] = this.expPrivado;
        clavePriv[1] = this.modulo;
        return clavePriv;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETCLAVES:
                ArrayList<BigInteger> claves = (ArrayList<BigInteger>) message;
                this.setExpPrivado(claves.get(0));
                this.setExpPublico(claves.get(1));
                this.setModulo(claves.get(2));
                break;
            case ENCRIPTAR:
                String[] mensaje = ((String) message).split("\\|");
                this.expPublico = new BigInteger(mensaje[0]);
                this.modulo = new BigInteger(mensaje[1]);
                break;
            case DESENCRIPTAR:
                String[] mensajeDes = ((String) message).split("\\|");
                this.expPrivado = new BigInteger(mensajeDes[0]);
                this.modulo = new BigInteger(mensajeDes[1]);
                break;
            case ADDTIEMPO:
                // si ya existe la key, simplemente se guarda el último tiempo obtenido
                long[] msj = (long[]) message;
                this.tiempos.put((int) msj[0], msj[1]);
                break;
        }

    }
}
