package Modelo;

import java.util.*;

public class Grafo {
    private final HashMap<String, Poblacion> poblaciones;
    private final ArrayList<Carretera> carreteras;
    private int numMinCarreteras = 5;

    public Grafo(HashMap<String, Poblacion> poblaciones) {
        this.poblaciones = poblaciones;
        carreteras = new ArrayList<>();
        generarAristasCercanas();
    }

    public Grafo(HashMap<String, Poblacion> poblaciones, int numMinCarreteras) {
        this.poblaciones = poblaciones;
        carreteras = new ArrayList<>();
        this.numMinCarreteras = numMinCarreteras;
        generarAristasCercanas();
    }

    /**
     * Genera las carreteras más cercanas a cada población
     */
    private void generarAristasCercanas() {
        // Se recorren las poblaciones
        for (Poblacion origen : poblaciones.values()) {
            ArrayList<Carretera> carreterasPob = new ArrayList<>();

            // Si la población ya tiene mas carreteras qeu numMinCarreteras, se salta
            if (origen.getNumCarreteras() >= numMinCarreteras) {
                continue;
            }

            // Se obtiene la distancia entre la población y el resto
            for (Poblacion destino : poblaciones.values()) {
                if (!origen.equals(destino)) {
                    double distancia = Carretera.calcularDistancia(origen, destino);
                    carreterasPob.add(new Carretera(distancia, origen, destino));
                }
            }

            // Se ordenan las carreteras para obtener las numMinCarreteras menores
            carreterasPob.sort(Comparator.comparing(Carretera::getDistancia));

            // Se añaden las carreteras a la población y al grafo
            int offset = 0;
            for (int i = origen.getNumCarreteras(); i < (numMinCarreteras + offset) && offset < carreterasPob.size(); i++) {
                // Si la carretera ya existe, se salta hasta encontrar una nueva,
                // en caso de no encontrarla, el nodo tendrá menos aristas que el mínimo establecido
                // lo cual ocurrirá en casos en los que el número de aristas es muy cercano al número de nodos
                if (!añadirCarretera(origen, carreterasPob.get(offset).getPob2(), carreterasPob.get(offset))) {
                    offset++;
                }
            }
        }
    }

    /**
     * Añade una carretera al grafo
     * @param origen Población origen
     * @param destino Población destino
     * @param carretera Carretera a añadir
     */
    public boolean añadirCarretera(Poblacion origen, Poblacion destino, Carretera carretera) {

        if (!carreteras.contains(carretera)) {
            carreteras.add(carretera);

            origen.addCarretera(carretera);
            destino.addCarretera(carretera);
            return true;
        }
        return false;
    }

    public Poblacion getPoblacion(String nombre) {
        return poblaciones.get(nombre);
    }

    public ArrayList<Carretera> getCarreteras() {
        return carreteras;
    }

    public int getNumMinCarreteras() {
        return numMinCarreteras;
    }

    public HashMap<String, Poblacion> getPoblaciones() {
        return poblaciones;
    }
}