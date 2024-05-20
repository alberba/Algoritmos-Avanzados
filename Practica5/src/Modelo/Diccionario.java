package Modelo;

import java.util.ArrayList;

public class Diccionario {
    private final ArrayList<String> diccionario;

    public Diccionario(ArrayList<String> diccionario) {
        this.diccionario = diccionario;
    }

    public ArrayList<String> getDiccionario() {
        return diccionario;
    }

    public boolean contains(String palabra) {
        return diccionario.contains(palabra);
    }
}
