package Practica2.Modelo.Formas;

import Practica2.Vista.PanelGrafico;

public class Triangulo {
    private final Punto[] puntos = new Punto[3];
    private static final int N_VERTICES = 3;

    public Triangulo(Punto p1, Punto p2, Punto p3) {
        this.puntos[0] = p1;
        this.puntos[1] = p2;
        this.puntos[2] = p3;
    }

    public Punto[] getPuntos() {
        return puntos;
    }

    // Devuelve las coordenadas x de los puntos
    public int[] getX() {
        return new int[]{puntos[0].getX(), puntos[1].getX(), puntos[2].getX()};
    }

    // Devuelve las coordenadas y de los puntos
    public int[] getY() {
        return new int[] {puntos[0].getY(), puntos[1].getY(), puntos[2].getY()};
    }

    public static int getN_VERTICES() {
        return N_VERTICES;
    }

    public static Punto[] getPuntosIniciales() {
        Punto[] puntos = new Punto[3];
        puntos[0] = new Punto(PanelGrafico.SIZE / 2, 0);
        puntos[1] = new Punto(0, PanelGrafico.SIZE);
        puntos[2] = new Punto(PanelGrafico.SIZE, PanelGrafico.SIZE);
        return puntos;
    }

}
