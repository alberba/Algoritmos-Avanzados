package Modelo;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;
import java.math.BigInteger;
import java.util.Random;

public class Modelo implements Notificacion {
    private BigInteger expPrivado, expPublico, Modulo;
    private Random random = new Random();
    private final Main prog;

    public Modelo (Main p) {
        this.prog = p;
    }

    public Modelo(Main p, String texto) {
        this.prog = p;
    }

    public int getBitLength() {
        return 1995;
    }
    public void setExpPrivado(BigInteger expPriv){
        this.expPrivado = expPriv;
    }
    public void setExpPublico(BigInteger expPubli){
        this.expPublico = expPubli;
    }
    public void setModulo(BigInteger modul){
        this.Modulo=modul;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETCLAVES:
                this.setExpPrivado(((BigInteger)message) );
                //this.setExpPublico(message[1]);
                //this.setModulo(message[2]);
                break;

        }

    }
}
