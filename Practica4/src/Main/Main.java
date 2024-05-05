package Main;

import Controlador.Controlador;
import Modelo.Modelo;
import Modelo.Poblacion;
import Notification.NotiEnum;
import Notification.Notificacion;
import Vista.Vista;
import Modelo.Grafo;
import mesurament.Mesurament24;

import java.util.*;


public class Main implements Notificacion {
    private final Vista vista;
    private final Modelo modelo;
    private Controlador controlador;
    private final HashMap<String, Poblacion> totalPoblaciones;

    public Main() {
        // Lectura del XML
        ParserSAX parserSAX = new ParserSAX();
        totalPoblaciones = parserSAX.parse("src/poblaciones.xml");
        modelo = new Modelo(this, new Grafo(generarPoblacionesGrafo(totalPoblaciones, 20)));
        controlador = null;
        vista = new Vista("Grafo", this);
        vista.mostrar();
    }

    public static void main(String[] args) {
        Mesurament24.mesura();
        new Main();
    }

    /**
     * Método que recopila N poblaciones aleatorias del hashmap de poblaciones
     * @param poblaciones HashMap con todas las poblaciones
     * @param numPoblaciones Número de poblaciones a seleccionar
     * @return HashMap con las N poblaciones seleccionadas
     */
    public HashMap<String, Poblacion> generarPoblacionesGrafo(HashMap<String, Poblacion> poblaciones, int numPoblaciones) {
        HashMap<String, Poblacion> poblacionesGrafo = new HashMap<>();
        Set<Integer> elegidos = new HashSet<>();
        Random random = new Random();

        while (elegidos.size() < numPoblaciones) {
            int num = random.nextInt(poblaciones.size());
            elegidos.add(num);
        }

        // Añadir las poblaciones seleccionadas al hashmap de poblaciones del grafo
        int i = 0;
        for (Poblacion poblacion : poblaciones.values()) {
            poblacion.reset();
            if (elegidos.contains(i)) {
                poblacionesGrafo.put(poblacion.getPoblacion(), poblacion);
            }
            i++;
        }

        return poblacionesGrafo;
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case INICIAR:
                controlador = new Controlador(this);
                controlador.run();
                vista.resetPanel();
                break;
            default:
                break;
        }
    }

    public Vista getVista() {
        return vista;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public HashMap<String, Poblacion> getTotalPoblaciones() {
        return totalPoblaciones;
    }

}