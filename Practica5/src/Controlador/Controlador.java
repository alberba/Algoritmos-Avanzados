package Controlador;

import Main.Main;
import Modelo.*;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.*;

public class Controlador implements Notificacion {

    private final Modelo modelo;
    private final Main main;

    public Controlador(Main p) {
        modelo = p.getModelo();
        main = p;
    }

    public void run() {
        detectarIdioma(modelo.getTexto().getTexto());
        corregirTexto(modelo.getTexto().getTexto(), modelo.getIdiomaPredominante());
    }

    public void detectarIdioma(TreeMap<String, Integer> texto) {
        Idioma idiomaPredomintante;
        ArrayList<Diccionario> diccionarios = modelo.getDiccionarios();

        Map<Idioma, Integer> contadores = new HashMap<>();
        contadores.put(Idioma.CAT, 0);
        contadores.put(Idioma.ESP, 0);
        contadores.put(Idioma.ING, 0);

        // Recorrido del texto palabra a palabra
        for (String palabra : texto.keySet()) {
            // Se busca la palabra en todos los diccionarios
            for (int i = 0; i < diccionarios.size(); i++) {
                if (diccionarios.get(i).contains(palabra)) { // Si aparece en uno, se suma el n.º de apariciones al contador de ese idioma
                    Idioma idioma = Idioma.values()[i];
                    contadores.put(Idioma.values()[i], contadores.get(idioma) + texto.get(palabra));
                }
            }
        }

        idiomaPredomintante = Collections.max(contadores.entrySet(), Map.Entry.comparingByValue()).getKey();
        modelo.notificar(NotiEnum.SETPREDOMINANTE, idiomaPredomintante);
    }

    public void corregirTexto(TreeMap<String, Integer> texto, Idioma idioma) {
        Diccionario diccionario = modelo.getDiccionario(idioma);
        ArrayList <String> palabras = new ArrayList<>(texto.keySet());
        TreeMap<String, ArrayList<Candidato>> correcciones = new TreeMap<>();
        // Se recorren las palabras del texto y se almacenan las incorrectas
        for (String palabra : palabras) {
            if (!diccionario.contains(palabra)) {
                correcciones.put(palabra, new ArrayList<>());
            }
        }
        // Se obtienen las 5 mejores correcciones para cada una
        // el arraylist tendrá un orden descendente, es decir, comenzará por el mejor candidato
        for (String palabra : correcciones.keySet()) {
            ArrayList<Candidato> candidatos = buscarSimilitudes(palabra, diccionario);
            candidatos.sort(Comparator.comparingInt(Candidato::getDistancia));
            correcciones.put(palabra, candidatos);
        }

        modelo.notificar(NotiEnum.CORRECIONES, correcciones);
    }

    /**
     * Método que, dada una palabra incorrecta, escoge las 5 palabras correctas más similares a ella (candidatos)
     * @param palabra Palabra a comprobar
     * @param diccionario Diccionario en el que buscar
     * @return ArrayList con los 5 candidatos más similares
     */
    private ArrayList<Candidato> buscarSimilitudes(String palabra, Diccionario diccionario) {
        PriorityQueue<Candidato> similitudes = new PriorityQueue<>(Comparator.comparingInt(Candidato::getDistancia).reversed());
        ArrayList<String> palabrasDic = diccionario.getDiccionario();

        // Inicialización a infinito
        for (int i = 0; i < 5; i++) {
            similitudes.add(new Candidato("", Integer.MAX_VALUE));
        }
        // Recorrido del diccionario
        for (String palabraDic : palabrasDic) {
            int distancia = levenshtein(palabra, palabraDic);
            // Se comprueba si la distancia es menor que la mayor de las 5 menores
            assert similitudes.peek() != null;
            if (distancia < similitudes.peek().getDistancia()) {
                // Si lo es, esta se sustituye y se reordena el array (para que siempre la mayor distancia esté en la última posición)
                similitudes.poll();
                similitudes.add(new Candidato(palabraDic, distancia));
            }
        }

        return new ArrayList<>(similitudes);
    }

    private int levenshtein(String palabra1, String palabra2) {
        // Crear una matriz para almacenar los resultados de los subproblemas
        int[][] matrizDistancias = new int[palabra1.length() + 1][palabra2.length() + 1];

        // Inicializar la primera columna de la matriz
        for (int i = 0; i <= palabra1.length(); i++) {
            matrizDistancias[i][0] = i;
        }

        // Inicializar la primera fila de la matriz
        for (int j = 0; j <= palabra2.length(); j++) {
            matrizDistancias[0][j] = j;
        }

        // Llenar el resto de la matriz
        for (int i = 1; i <= palabra1.length(); i++) {
            for (int j = 1; j <= palabra2.length(); j++) {
                // Si los caracteres son iguales, la distancia es la misma que para las subcadenas anteriores
                if (palabra1.charAt(i - 1) == palabra2.charAt(j - 1)) {
                    matrizDistancias[i][j] = matrizDistancias[i - 1][j - 1];
                } else {
                    // Si los caracteres son diferentes, la distancia es 1 más el mínimo de las distancias para las tres operaciones posibles
                    matrizDistancias[i][j] = 1 + Math.min(matrizDistancias[i - 1][j - 1], Math.min(matrizDistancias[i - 1][j], matrizDistancias[i][j - 1]));
                }
            }
        }

        // Devolver la distancia de Levenshtein para las cadenas completas, que es el valor en la esquina inferior derecha de la matriz
        return matrizDistancias[palabra1.length()][palabra2.length()];
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            default:
                break;
        }
    }
}
