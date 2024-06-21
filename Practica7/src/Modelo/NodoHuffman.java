package Modelo;

public class NodoHuffman implements Comparable<NodoHuffman> {

    private int frecuencia;
    private char dato;
    private NodoHuffman left, right;

    public NodoHuffman(int freq, char dato) {
        this.frecuencia = freq;
        this.dato = dato;
        left = right = null;
    }

    @Override
    public int compareTo(NodoHuffman o) {
        return this.frecuencia - o.frecuencia;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setRight(NodoHuffman right) {
        this.right = right;
    }

    public void setLeft(NodoHuffman left) {
        this.left = left;
    }

    public NodoHuffman getLeft() {
        return left;
    }

    public NodoHuffman getRight() {
        return right;
    }

    public char getDato() {
        return dato;
    }

    public void setDato(char dato) {
        this.dato = dato;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
