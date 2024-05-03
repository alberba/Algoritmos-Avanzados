package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PanelGrafico extends JPanel {

    private final Main prog;
    private final Modelo modelo;
    private final ArrayList<Nodo> nodos;
    private final ArrayList<Arista> aristas;

    public PanelGrafico(Main p) {
        prog = p;
        modelo = prog.getModelo();
        nodos = new ArrayList<>();
        aristas = new ArrayList<>();
    }
    // Método que dibuja el contenido del panel
    /*
    @Override
    public void paint(Graphics g) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));
        generarNodos(this.getGraphics(), modelo.getGrafo().getPoblaciones().values());
        generarAristas(this.getGraphics());
    }
    */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));
        generarNodos(g, modelo.getGrafo().getPoblaciones().values());
        generarAristas(g);
    }

    // Método que genera los 20 nodos del grafo
    private void generarNodos(Graphics g, Collection<Poblacion> poblaciones) {
        // Márgen de píxeles a cada lado del panel
        int margenX = 20;
        int margenY = 20;
        g.fillOval(20, 20, 10, 10);
        g.fillOval(this.getWidth()-20, this.getHeight()-20, 10, 10);
        for (Poblacion pob : poblaciones) {
            // Calcular la posición del nodo como un porcentaje del tamaño del panel (con 20px de márgen a cada lado)
            int x = (int) (((pob.getLon() - Nodo.getMinLon())/Nodo.getRangoLon()) * (this.getWidth() - 2 * margenX)) + margenX;
            int y = (int) (((pob.getLat() - Nodo.getMinLat())/Nodo.getRangoLat()) * (this.getHeight() - 2 * margenY)) + margenY;
            // Print de los valores para debug
            System.out.println("Población: " + pob.getPoblacion() + ", x: " + x + ", y: " + y);
            // Crear el nodo y añadirlo a la lista
            this.nodos.add(new Nodo(x, y, pob.getPoblacion()));

            // Dibujar el nodo
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 10, 10);
            // Dibujar el nombre del nodo
            g.setColor(Color.BLACK);
            g.drawString(pob.getPoblacion(), x, y);
        }
    }

    private void generarAristas(Graphics g) {
        // Obtener las carreteras del modelo
        ArrayList<Carretera> carreteras = modelo.getGrafo().getCarreteras();
        for (Carretera car : carreteras) {
            // Obtener las coordenadas de los nodos origen y destino
            Nodo origen = nodos.get(obtenerIndiceNodo(car.getPob1().getPoblacion()));
            Nodo destino = nodos.get(obtenerIndiceNodo(car.getPob2().getPoblacion()));
            // Crear la arista y añadirla a la lista
            this.aristas.add(new Arista(origen, destino));
            // Dibujar la arista
            g.setColor(Color.BLACK);
            g.drawLine(origen.getX(), origen.getY(), destino.getX(), destino.getY());
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
        return new Dimension(800, 600);
    }

    /*
    @Override
    public void repaint() {
        Graphics g = this.getGraphics();
        if (g != null) {
            paint(g);
        }
    }
    */
    @Override
    public void repaint() {
        super.repaint();
    }

    public void clear() {
        Graphics g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}