package Modelo;

import Controlador.ParserDic;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;
import java.util.TreeMap;

public class Modelo implements Notificacion {
    private final Main prog;
    private static final int NDICCIONARIOS = 3;
    private ArrayList<Diccionario> diccionarios; // 0: catalan, 1: español 2: inglés
    private Texto texto;
    private Idioma idiomaPredominante;
    private TreeMap<String, ArrayList<Candidato>> correcciones;

    public Modelo (Main p) {
        this.prog = p;
        texto = new Texto("Today es un día Jorge day gay crec que no és possible anar a cercar el coche it is not possible");
        diccionarios = new ParserDic(new String[]{"ca.dic", "es_ES.dic", "en_GB.dic"}).parse();
    }

    /**
     * Constructor objeto Modelo
     * @param p Objeto Main
     * @param texto Texto modelo
     */
    public Modelo(Main p, String texto) {
        this.prog = p;
        this.texto = new Texto(texto);
        diccionarios = new ParserDic(new String[]{"ca.dic", "es_ES.dic", "en_GB.dic"}).parse();
    }

    public ArrayList<Diccionario> getDiccionarios() {
        return diccionarios;
    }

    public void setPredominante(Idioma idioma) {
        idiomaPredominante = idioma;
    }

    public Idioma getIdiomaPredominante() {
        return idiomaPredominante;
    }

    public Texto getTexto() {
        return texto;
    }

    public TreeMap<String, ArrayList<Candidato>> getCorrecciones() {
        return correcciones;
    }

    public Diccionario getDiccionario(Idioma idioma) {
        return switch (idioma) {
            case CAT -> diccionarios.get(0);
            case ESP -> diccionarios.get(1);
            case ING -> diccionarios.get(2);
        };
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETPREDOMINANTE:
                setPredominante((Idioma) message);
                break;
            case CORRECIONES:
                @SuppressWarnings("unchecked")
                TreeMap<String, ArrayList<Candidato>> correcciones = (TreeMap<String, ArrayList<Candidato>>) message;
                this.correcciones = correcciones;
                prog.getVista().repaint();
                break;
            case CORREGIR:
                    ArrayList<String> palabras = (ArrayList<String>) message;
                    if (palabras.size() == 2) {
                        String palabraOriginal = palabras.get(0);
                        String palabraCorregida = palabras.get(1);
                        texto.corregirPalabra(palabraOriginal, palabraCorregida);
                    }

                break;

        }
    }
}
