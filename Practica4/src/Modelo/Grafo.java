package Modelo;

import java.util.*;

public class Grafo {
    private final HashMap<String, Poblacion> poblaciones;
    private final ArrayList<Carretera> carreteras;
    private final int numMinCarreteras;

    public Grafo() {
        poblaciones = new HashMap<>();
        carreteras = new ArrayList<>();
        numMinCarreteras = 5;
    }

    public Grafo(HashMap<String, Poblacion> poblaciones) {
        this.poblaciones = poblaciones;
        carreteras = new ArrayList<>();
        numMinCarreteras = 5;
        generarAristasCercanas();
    }

    public Grafo(HashMap<String, Poblacion> poblaciones, int numMinCarreteras) {
        this.poblaciones = poblaciones;
        carreteras = new ArrayList<>();
        this.numMinCarreteras = numMinCarreteras;
        generarAristasCercanas();
    }

    public void afegirCarretera(Poblacion origen, Poblacion destino, Carretera carretera) {
        if (!carreteras.contains(carretera))
            carreteras.add(carretera);
        origen.addCarretera(carretera);
        destino.addCarretera(carretera);
    }

    private void generarAristasCercanas() {
        // Se recorren las poblaciones
        for (Poblacion origen : poblaciones.values()) {
            ArrayList<Carretera> carreterasPob = new ArrayList<>();
            // Si la población ya tiene 5 o más carreteras, se salta
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
            // Se ordenan las carreteras para obtener las 5 menores
            carreterasPob.sort(Comparator.comparing(Carretera::getDistancia));

            for (int i = origen.getNumCarreteras(); i < numMinCarreteras; i++) {
                afegirCarretera(origen, carreterasPob.get(i).getPob2(), carreterasPob.get(i));
            }
        }
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