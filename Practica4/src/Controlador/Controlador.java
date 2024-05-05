package Controlador;

import Main.Main;
import Modelo.*;

import java.util.*;

public class Controlador {

    private final Modelo modelo;

    public Controlador(Main p) {
        modelo = p.getModelo();
    }

    public void run() {
        ArrayList<Carretera> solucion;
        if (modelo.getAlgoritmo() == Algoritmo.DIJKSTRA) {
            solucion = dijkstra(modelo.getOrigen(), modelo.getDestino());
        } else {
            solucion = prim();
        }
        modelo.setSolucion(solucion);
    }

    /**
     * Función que se encargará de encontrar el camino con menor distancia entre dos pueblos pasados por parámetro.
     * Se usará el algoritmo de Dijkstra para encontrar el camino
     * @param poblacionOrigen Población donde empezará el camino
     * @param poblacionFinal Población donde terminará el camino
     * @return Lista del camino con menor coste
     */
    public ArrayList<Carretera> dijkstra(Poblacion poblacionOrigen, Poblacion poblacionFinal) {
        // Mapa para almacenar las distancias mínimas desde el nodo de origen a cada nodo
        Map<Poblacion, Double> distancias = new HashMap<>();
        // Mapa para almacenar el camino más corto. La clave será el último pueblo del camino
        Map<Poblacion, ArrayList<Carretera>> camino = new HashMap<>();
        // Conjunto de pueblos no visitados. Usado para evitar revisitas innecesarias
        Set<Poblacion> noVisitados = new HashSet<>(modelo.getGrafo().getPoblaciones().values());

        // Inicializar las distancias a todos los nodos como infinito
        for (Poblacion poblacion : modelo.getGrafo().getPoblaciones().values()) {
            distancias.put(poblacion, Double.MAX_VALUE);
        }

        // La distancia al nodo de origen es 0
        distancias.put(poblacionOrigen, 0.0);
        camino.put(poblacionOrigen, new ArrayList<>());

        dijkstraRecursivo(poblacionOrigen, poblacionFinal, distancias, camino, noVisitados);

        // El valor de poblacionFinal del Map camino, será lo que buscamos
        ArrayList<Carretera> caminoMinimo = camino.get(poblacionFinal);

        // Invertir el camino para que vaya desde el origen al destino
        Collections.reverse(caminoMinimo);
        return caminoMinimo;
    }

    /**
     * Método recursivo del algoritmo Dijkstra
     * @param poblacion1 Población a la que aplicar el algoritmo
     * @param poblacionFinal Población objetivo
     * @param distancias Conjunto mapeado del pueblo junto a su coste mínimo encontrado para llegar hasta él
     * @param camino Conjunto mapeado del mejor camino encontrado para cado pueblo
     * @param noVisitados Conjunto de pueblos no visitados
     * @return True en caso de encontrar el mejor camino. False en caso contrario
     */
    private boolean dijkstraRecursivo
    (Poblacion poblacion1, Poblacion poblacionFinal, Map<Poblacion, Double> distancias, Map<Poblacion,
        ArrayList<Carretera>> camino, Set<Poblacion> noVisitados) {
        // Eliminamos para no revisitarlo
        noVisitados.remove(poblacion1);

        //Comprueba si la población actual es el destino
        if (poblacion1.equals(poblacionFinal)) {

            for (Poblacion poblacion : noVisitados) {

                if (distancias.get(poblacion) < distancias.get(poblacionFinal)) {
                    // Hay un nodo no visitado que tiene un menor coste a la población final. Esto significa que puede
                    // existir un mejor camino a él
                    return false;
                }
            }

            // Todos los pueblos no visitados tienen un coste mayor a la población final, significando que no habrá un
            // mejor camino para llegar hasta él
            return true;
        }

        asignarCosteVecinos(poblacion1, distancias, camino);

        // Mientras la cola de prioridad no esté vacía
        for(Carretera carretera : poblacion1.getCarreteras()) {

            Poblacion pobDestCamino = carretera.getContrario(poblacion1);

            if (noVisitados.contains(pobDestCamino)) {

                if (dijkstraRecursivo(pobDestCamino, poblacionFinal, distancias, camino, noVisitados)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Método encargado de obtener el coste de llegar hasta cada uno de sus vecinos, substituyendo en caso de encontrar
     * un camino hasta el vecino con coste más bajo
     * @param poblacion Población en el que se aplica el método
     * @param distancias Conjunto mapeado del pueblo junto a su coste mínimo encontrado para llegar hasta él
     * @param camino Conjunto mapeado del mejor camino encontrado para cada población
     */
    private void asignarCosteVecinos
    (Poblacion poblacion, Map<Poblacion, Double> distancias, Map<Poblacion, ArrayList<Carretera>> camino) {
        // Recorrer las carreteras adjuntas a poblacion
        for (Carretera carretera : poblacion.getCarreteras()) {
            Poblacion vecino = carretera.getContrario(poblacion);

            double distancia = distancias.get(poblacion) + carretera.getDistancia();
            if (distancia < distancias.get(vecino)) {
                // Existe un camino más corto para llegar hasta él
                distancias.put(vecino, distancia);
                ArrayList<Carretera> caminoVecino = new ArrayList<>(camino.get(poblacion));
                caminoVecino.add(carretera);
                camino.put(vecino, caminoVecino);
            }
        }
    }

    /**
     * Método que se encargará de encontrar el árbol de expansión mínima de un grafo
     * @return Lista del camino con menor coste
     */
    public ArrayList<Carretera> prim() {
        // Crear un mapa para almacenar el nodo anterior en el camino más corto
        ArrayList<Carretera> camino = new ArrayList<>();
        ArrayList<Poblacion> poblacionesEnArbol = new ArrayList<>();
        ArrayList<Poblacion> poblaciones = new ArrayList<>(modelo.getGrafo().getPoblaciones().values());
        double minDist;
        poblacionesEnArbol.add(poblaciones.get(0));

        // No acabará hasta que no se hayan añadido todos los nodos al árbol
        while (poblacionesEnArbol.size() < poblaciones.size()) {
            minDist = Double.MAX_VALUE;
            Carretera mejorCarretera = null;
            Poblacion mejorPoblacion = null;

            // Recorrer todas las poblaciones en el árbol en busca de la carretera más corta disponible
            for(int i = 0; i < poblacionesEnArbol.size(); i++) {
                Poblacion poblacionActual = poblacionesEnArbol.get(i);
                for(Carretera carretera : poblacionActual.getCarreteras()) {
                    if (!poblacionesEnArbol.contains(carretera.getContrario(poblacionActual))) {
                        if (carretera.getDistancia() < minDist) {
                            minDist = carretera.getDistancia();
                            mejorCarretera = carretera;
                            mejorPoblacion = carretera.getContrario(poblacionActual);
                        }
                    }
                }
            }

            // Añadir la mejor carretera
            poblacionesEnArbol.add(mejorPoblacion);
            camino.add(mejorCarretera);

        }

        return camino;
    }

}
