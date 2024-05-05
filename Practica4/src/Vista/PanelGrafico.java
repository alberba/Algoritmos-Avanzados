package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class PanelGrafico extends JPanel {

    private final Modelo modelo;
    private ArrayList<Nodo> nodos;
    private static final int anchura = 1200;
    private static final int altura = 1000;

    public PanelGrafico(Main p) {
        modelo = p.getModelo();
        nodos = new ArrayList<>();
        this.setPreferredSize(new Dimension(anchura, altura));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        generarNodos(g, modelo.getGrafo().getPoblaciones().values());
        generarAristas(g);
        escribirNumeros(g);

    }


    /**
     * Método que genera los nodos en el panel
     * @param g
     * @param poblaciones Colección de poblaciones
     */
    private void generarNodos(Graphics g, Collection<Poblacion> poblaciones) {
        nodos = new ArrayList<>();
        // Márgen de píxeles a cada lado del panel
        int margenX = 100;
        int margenY = 20;
        int i = 0;
        for (Poblacion pob : poblaciones) {
            // Calcular la posición del nodo normalizandolo al tamaño del panel
            int x = (int) (((pob.getLon() - Modelo.getMinLon())/Modelo.getRangoLon()) * (this.getWidth() - 2 * margenX)) + margenX;
            int y = (int) (((pob.getLat() - Modelo.getMinLat())/Modelo.getRangoLat()) * (this.getHeight() - 2 * margenY)) + margenY;
            // Crear el nodo y añadirlo a la lista
            this.nodos.add(new Nodo(x, y, pob.getPoblacion(), i + 1));
            i++;

            // Dibujar el nodo
            g.setColor(Color.BLACK);
            g.fillOval(x - 15, y - 15, 30, 30);
        }
    }

    /**
     * Método que se encarga de dibujar las aristas en el panel
     * @param g
     */
    private void generarAristas(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Carretera> solucion = modelo.getSolucion();
        // Obtener las carreteras del modelo
        ArrayList<Carretera> carreteras = modelo.getGrafo().getCarreteras();
        for (Carretera car : carreteras) {
            // Obtener las coordenadas de los nodos origen y destino
            Nodo origen = nodos.get(obtenerIndiceNodo(car.getPob1().getPoblacion()));
            Nodo destino = nodos.get(obtenerIndiceNodo(car.getPob2().getPoblacion()));
            // Si se encuentra en la solución, se dibuja en azul
            if (solucion != null && solucion.contains(car)) {
                g2.setStroke(new BasicStroke(2.0f));
                g2.setColor(Color.BLUE);
                g2.drawLine(origen.getX(), origen.getY(), destino.getX(), destino.getY());
            } else {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawLine(origen.getX(), origen.getY(), destino.getX(), destino.getY());
            }

        }
    }

    /**
     * Método que escribe los números de los nodos encima de ellos
     * @param g Objeto Graphics
     */
    private void escribirNumeros(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        for (Nodo nodo : nodos) {
            g.drawString("" + nodo.getIndice(), nodo.getX() - 5, nodo.getY() + 5);
        }
    }

    private int obtenerIndiceNodo(String nombre) {
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(anchura, altura);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

}