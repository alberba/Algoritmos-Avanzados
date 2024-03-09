package Practica2.Modelo;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.Cuadrado;
import Practica2.NotiEnum;
import Practica2.Notificacion;
import Practica2.Vista.dialogos.EnumPolygon;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private int profundidad;
    private EnumPolygon tipo;
    private ArrayList<Object> poligonos;

    public Modelo (Main p) {
        prog = p;
        profundidad = 3;
        tipo = EnumPolygon.CUADRADO;
        poligonos = new ArrayList<>();
    }

    /**
     * Resetea los polígonos registrados
     */
    public void reset() {
        poligonos = new ArrayList<>();
    }

    public int getProfundidad() {
        return profundidad;
    }

    public EnumPolygon getTipo() {
        return tipo;
    }

    public ArrayList<Object> getPoligonos() {
        return poligonos;
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        if (s == NotiEnum.SETPARAM) {
            if (o instanceof Integer){
                profundidad = (Integer) o;
            } else {
                tipo = (EnumPolygon) o;
            }
        } else if (s == NotiEnum.ADDCUADRADO) {
            poligonos.add(o);
            prog.getVista().notificar(NotiEnum.DIBUJAR, null);
        }
    }
}
