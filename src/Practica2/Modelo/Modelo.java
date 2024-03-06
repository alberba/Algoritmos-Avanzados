package Practica2.Modelo;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.Cuadrado;
import Practica2.NotiEnum;
import Practica2.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private int profundidad;
    private ArrayList<Cuadrado> cuadrados;
    public Modelo (Main p) {
        prog = p;
        profundidad = 3;
        cuadrados = new ArrayList<>();
    }

    public int getProfundidad() {
        return profundidad;
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        if (s == NotiEnum.SETPARAM) {
            profundidad = (Integer) o;
        } else if (s == NotiEnum.ADDCUADRADO) {
            cuadrados.add((Cuadrado) o);
            prog.getVista().notificar(NotiEnum.DIBUJAR, null);
        }
    }
}
