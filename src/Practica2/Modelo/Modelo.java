package Practica2.Modelo;

import Practica2.Main.Main;
import Practica2.NotiEnum;
import Practica2.Notificacion;
import Practica2.Vista.dialogos.EnumPolygon;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private int profundidad;
    private double c;
    private EnumPolygon tipo;
    private ArrayList<Object> poligonos;

    public Modelo (Main p) {
        prog = p;
        profundidad = 3;
        tipo = EnumPolygon.CUADRADO;
        poligonos = new ArrayList<>();
        c = 0;
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

    public double getC() { return c; }

    public void setC(double c) { this.c = c; }

    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case SETPARAM:
                if (o instanceof Integer){
                    profundidad = (Integer) o;
                } else {
                    tipo = (EnumPolygon) o;
                    // Se reinicia la c al cambiar el tipo de polígono para volver a calcularla más tarde
                    c = 0.0;
                }
                break;
            case ADDPOLIGONO:
                // En caso de añadir un cuadrado o un triángulo
                poligonos.add(o);
                prog.getVista().notificar(NotiEnum.DIBUJAR, null);
                break;
            default:
                break;
        }
    }
}
