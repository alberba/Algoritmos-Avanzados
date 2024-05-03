package Modelo;

import java.util.*;

public class Grafo {
    private final Map<String, Poblacion> poblaciones;
    private final ArrayList<Carretera> carreteras;

    public Grafo(HashMap<String, Poblacion> poblaciones) {
        this.poblaciones = poblaciones;
        carreteras = new ArrayList<>();
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
            if (origen.getNumCarreteras() >= 5) {
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

            for (int i = origen.getNumCarreteras(); i < 5; i++) {
                afegirCarretera(origen, carreterasPob.get(i).getPob2(), carreterasPob.get(i));
            }
        }
    }

    /**
     * Función que se encargará de encontrar el camino con menor distancia entre dos pueblos pasados por parámetro.
     * Se usará el algoritmo de Dijkstra para encontrar el camino
     * @param poblacionOrigen Población donde empezará el camino
     * @param poblacionFinal Población donde terminará el camino
     * @return Lista del camino con menor coste
     */
    public List<Carretera> dijkstra(Poblacion poblacionOrigen, Poblacion poblacionFinal) {
        // Mapa para almacenar las distancias mínimas desde el nodo de origen a cada nodo
        Map<Poblacion, Double> distancias = new HashMap<>();
        // Mapa para almacenar el camino más corto. La clave será el último pueblo del camino
        Map<Poblacion, ArrayList<Carretera>> camino = new HashMap<>();
        // Conjunto de pueblos no visitados. Usado para evitar revisitas innecesarias
        HashSet noVisitados = new HashSet<>(poblaciones.values());

        // Inicializar las distancias a todos los nodos como infinito
        for (Poblacion poblacion : poblaciones.values()) {
            distancias.put(poblacion, Double.MAX_VALUE);
        }

        // La distancia al nodo de origen es 0
        distancias.put(poblacionOrigen, 0.0);

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
            ArrayList<Carretera>> camino, HashSet<Poblacion> noVisitados) {
        // Eliminamos para no volver a revisitarlo
        noVisitados.remove(poblacion1);

        //Comprueba si el poblado actual es el final
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
     * @param camino Conjunto mapeado del mejor camino encontrado para cado pueblo
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

    // Algoritmo de prim donde las keys son strings y los values son doubles
    public HashMap<Poblacion, Double> prim() {
        // Crear un mapa para almacenar los nodos visitados
        HashMap<Poblacion, Boolean> visitados = new HashMap<>();
        // Crear un mapa para almacenar las distancias mínimas desde el nodo de origen a cada nodo
        HashMap<Poblacion, Double> distancias = new HashMap<>();
        // Crear un mapa para almacenar el nodo anterior en el camino más corto
        HashMap<Poblacion, Poblacion> camino = new HashMap<>();

        // Inicializar las distancias a todos los nodos como infinito
        for (Poblacion poblacion : poblaciones.values()) {
            distancias.put(poblacion, Double.MAX_VALUE);
        }

        // La distancia al nodo de origen es 0
        distancias.put(poblaciones.values().iterator().next(), 0.0);

        // Mientras haya nodos por visitar
        while (hayNodosPorVisitar(visitados)) {
            // Obtener el nodo más cercano
            Poblacion nodo = obtenerNodoMasCercano(distancias, visitados);
            // Marcar el nodo como visitado
            visitados.put(nodo, true);
            // Actualizar las distancias a los vecinos del nodo
            actualizarDistancias(nodo, distancias, camino, visitados);
        }

        // Devolver el camino más corto
        return distancias;
    }

    private boolean hayNodosPorVisitar(HashMap<Poblacion, Boolean> visitados) {
        for (boolean visitado : visitados.values()) {
            if (!visitado) {
                return true;
            }
        }
        return false;
    }

    private Poblacion obtenerNodoMasCercano(HashMap<Poblacion, Double> distancias, HashMap<Poblacion, Boolean> visitados) {
        double minDistancia = Double.MAX_VALUE;
        Poblacion nodoMasCercano = null;
        // Se compara entre todos los nodos no visitados para obtener el de menor distancia
        // (si todos tienen la misma, se devuelve el primero de forma arbitraria)
        for (HashMap.Entry<Poblacion, Double> entry : distancias.entrySet()) {
            Poblacion nodo = entry.getKey();
            double distancia = entry.getValue();
            if (!visitados.get(nodo) && distancia < minDistancia) {
                minDistancia = distancia;
                nodoMasCercano = nodo;
            }
        }
        return nodoMasCercano;
    }

    private void actualizarDistancias(Poblacion nodo, HashMap<Poblacion, Double> distancias, HashMap<Poblacion, Poblacion> camino, HashMap<Poblacion, Boolean> visitados) {
        for (Carretera carretera : nodo.getCarreteras()) {
            Poblacion vecino = carretera.getContrario(nodo);
            double distancia = distancias.get(nodo) + carretera.getDistancia();
            // double distancia = carretera.getDistancia();
            if (!visitados.get(vecino) && distancia < distancias.get(vecino)) {
                distancias.put(vecino, distancia);
                camino.put(vecino, nodo);
            }
        }
    }

    /*
    @SuppressWarnings("unchecked")
    public ArrayList<Carretera> arbreRecobrimentMinim() {
        ArrayList<Carretera> solucion = new ArrayList<>();
        TreeMap<Poblacion, Integer> poblacionesValores = new TreeMap<Poblacion, Integer>();
        // Valor inicial máximo para todos los nodos excepto para uno (arbitrario)
        Random rand = new Random();
        int inicial = rand.nextInt(poblaciones.values().size());
        int i = 0;
        for (Poblacion pob : poblaciones.values()) {
            if (i == inicial) {
                poblacionesValores.put(pob, 0);
            } else {
                poblacionesValores.put(pob, Integer.MAX_VALUE);
            }
            i++;
        }

        TreeMap<Poblacion, Integer> lista = (TreeMap<Poblacion, Integer>) poblacionesValores.clone();

        while (!lista.isEmpty()) {
            Poblacion u = lista.firstEntry().getKey();
            lista.remove(u);
            if ()
        }

        return null; // Temporal
    }
     */
}