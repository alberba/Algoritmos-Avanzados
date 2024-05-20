package Modelo;

import Controlador.ParserDic;
import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.ArrayList;

public class Modelo implements Notificacion {
    private final Main prog;
    private static final int NDICCIONARIOS = 3;
    private ArrayList<Diccionario> diccionarios; // 0: catalan, 1: español 2: inglés
    private Texto texto;
    private Idioma idiomaPredominante;

    public Modelo (Main p) {
        this.prog = p;
        texto = new Texto("Today es un día Jorge day gay crec que no és possible anar a cercar el coche it is not possible");
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

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETPREDOMINANTE:
                setPredominante((Idioma) message);
                break;
        }
    }
}
