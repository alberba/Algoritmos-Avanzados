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
        String[] words = texto.toLowerCase().split(" ");

        for (String word : words) {
            if (wordCounts.containsKey(word)) {
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                wordCounts.put(word, 1);
            }
        }
        return wordCounts;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public TreeMap<String, Integer> getTexto() {
        return texto;
    }

    public void cambiarPalabra(String palabra1, String palabra2){
        String regex = "\\b" + palabra1 + "\\b";
        textoOriginal = textoOriginal.replaceFirst(regex, palabra2);
        texto.merge(palabra2, 1, Integer::sum);

        int count = texto.get(palabra1) - 1;
        texto.put(palabra1, count);
        if(count == 0){
            texto.remove(palabra1);
        }
    }

}
