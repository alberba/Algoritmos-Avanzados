package Modelo;

import java.util.TreeMap;
import java.util.regex.Pattern;

public class Texto {
    private TreeMap<String, Integer> texto;
    private String textoOriginal;

    public Texto(String texto) {
        this.texto = textoToTreeMap(texto);
        this.textoOriginal = texto;
    }

    public TreeMap<String, Integer> textoToTreeMap(String texto) {

        TreeMap<String, Integer> wordCounts = new TreeMap<>();
        String[] words = texto.toLowerCase().split("(\\s|\\.|,|\n)+");

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
        // Reemplaza la primera aparición de la palabra1 (sin distinguir mayúsculas y minúsculas) por la palabra2
        textoOriginal = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(textoOriginal).replaceFirst(palabra2);
        // Actualiza el TreeMap. En caso de que exista, se le resta 1 a la palabra1, si no, se elimina
        int count = texto.get(palabra1) - 1;
        texto.put(palabra1, count);
        if(count == 0){
            texto.remove(palabra1);
        }

        // Actualiza el TreeMap. En caso de que exista, se le suma 1 a la palabra2, si no, se añade
        texto.merge(palabra2, 1, Integer::sum);
    }

}
