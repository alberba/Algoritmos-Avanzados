package Modelo;

import java.util.TreeMap;

public class Texto {
    private TreeMap<String, Integer> texto;
    private String textoOriginal;

    public Texto(String texto) {
        this.texto = textoToTreeMap(texto);
        this.textoOriginal = texto;
    }

    public TreeMap<String, Integer> textoToTreeMap(String texto) {
        TreeMap<String, Integer> wordCounts = new TreeMap<>();
        String[] words = texto.toLowerCase().split("");

        for (String word : words) {
            if (wordCounts.containsKey(word)) {
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                wordCounts.put(word, 1);
            }
        }
        return wordCounts;
    }

    public void detectarIdioma() {

    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public TreeMap<String, Integer> getTexto() {
        return texto;
    }

}
