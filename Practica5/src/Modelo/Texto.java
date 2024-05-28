package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Texto {
    private final TreeMap<String, Integer> texto;
    private String textoOriginal;

    public Texto(String texto) {
        this.texto = textoToTreeMap(texto);
        this.textoOriginal = texto;
    }

    public Texto(File fichero) {
        this.textoOriginal = leerFichero(fichero);
        this.texto = textoToTreeMap(textoOriginal);
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

    /**
     * Lee un fichero y devuelve su contenido en forma de String
     * @param fichero Fichero a leer
     * @return Contenido del fichero
     */
    public String leerFichero(File fichero) {
        if (!fichero.getName().split("\\.")[1].equals("txt")) {
            return "Fichero incorrecto. Selecciona un fichero .txt";
        }
        StringBuilder texto = new StringBuilder();
        try {
            Scanner myReader = new Scanner(fichero);
            while (myReader.hasNextLine()) {
                texto.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return texto.toString();
    }

}
