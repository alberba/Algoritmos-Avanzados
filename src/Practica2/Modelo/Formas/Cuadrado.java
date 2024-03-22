package Practica2.Modelo.Formas;

public class Cuadrado {
    private Punto punto;
    private int lado;

    public Cuadrado(Punto punto, int lado) {
        this.punto = punto;
        this.lado = lado;
    }

    public Punto getPunto() {
        return punto;
    }

    public int getLado() {
        return lado;
    }

}
