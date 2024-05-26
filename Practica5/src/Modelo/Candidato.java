package Modelo;

public class Candidato implements Comparable<Candidato> {
    String palabra;
    int distancia;

    public Candidato(String palabra, int distancia) {
        this.palabra = palabra;
        this.distancia = distancia;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getDistancia() {
        return distancia;
    }

    @Override
    public int compareTo(Candidato otro) { // Tiene más prioridad aquel candidato con menor distancia
        return Integer.compare(otro.distancia, this.distancia);
    }
}
