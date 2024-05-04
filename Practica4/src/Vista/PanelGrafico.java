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
    

    // Método que genera los 20 nodos del grafo
    private void generarNodos(Graphics g, Collection<Poblacion> poblaciones) {
        nodos = new ArrayList<>();
        // Márgen de píxeles a cada lado del panel
        int margenX = 100;
        int margenY = 20;
        // Círculos de test
        // g.fillOval(20, 20, 10, 10);
        // g.fillOval(this.getWidth()-20, this.getHeight()-20, 10, 10);
        int i = 0;
        for (Poblacion pob : poblaciones) {
            // Calcular la posición del nodo como un porcentaje del tamaño del panel (con 20px de márgen a cada lado)
            int x = (int) (((pob.getLon() - Modelo.getMinLon())/Modelo.getRangoLon()) * (this.getWidth() - 2 * margenX)) + margenX;
            int y = (int) (((pob.getLat() - Modelo.getMinLat())/Modelo.getRangoLat()) * (this.getHeight() - 2 * margenY)) + margenY;
            // Crear el nodo y añadirlo a la lista
            this.nodos.add(new Nodo(x, y, pob.getPoblacion(), i + 1));
            i++;

            // Dibujar el nodo
            g.setColor(Color.BLACK);
            g.fillOval(x - 15, y - 15, 30, 30);
            // Dibujar el nombre del nodo
            Font boldFont = g.getFont().deriveFont(Font.BOLD);
            g.setFont(boldFont);
        }
    }

    private void generarAristas(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Carretera> solucion = modelo.getSolucionPrim();
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

    private void escribirNumeros(Graphics g) {
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