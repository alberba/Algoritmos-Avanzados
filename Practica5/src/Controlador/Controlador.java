package Controlador;

import Main.Main;
import Modelo.*;
import Notification.NotiEnum;
import Notification.Notificacion;

import java.util.*;

public class Controlador implements Notificacion {

    private final Modelo modelo;
    private String pathDic;

    public Controlador(Main p) {
        modelo = p.getModelo();
    }

    public void run() {
        detectarIdioma(modelo.getTexto().getTexto());
    }

    public void detectarIdioma(TreeMap<String,Integer> texto) {
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

    @Override
    public void notificar(NotiEnum s, Object message) {
        switch (s) {
            case SETDICPATH:
                pathDic = (String) message;
                break;
            default:
                break;
        }
    }
}
