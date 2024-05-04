package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PanelGrafico extends JPanel {

    private final Main prog;
    private final Modelo modelo;
    private ArrayList<Nodo> nodos;
    private ArrayList<Arista> aristas;
    private int anchura;
    private int altura;

    public PanelGrafico(Main p) {
        prog = p;
        modelo = prog.getModelo();
        nodos = new ArrayList<>();
        aristas = new ArrayList<>();
        anchura = 1200;
        altura = 1000;
        this.setPreferredSize(new Dimension(anchura, altura));

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
            //int x = (int) ((pob.getLon() - Modelo.getMinLon()) * 100 + margen);
            int y = (int) (((pob.getLat() - Modelo.getMinLat())/Modelo.getRangoLat()) * (this.getHeight() - 2 * margenY)) + margenY;
            //int y = (int) ((pob.getLat() - Modelo.getMinLat()) * 100 + margen);
            // Print de los valores para debug
            System.out.println("Población: " + pob.getPoblacion() + ", x: " + x + ", y: " + y);
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
        aristas = new ArrayList<>();
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Carretera> solucion = modelo.getSolucionPrim();
        // Obtener las carreteras del modelo
        ArrayList<Carretera> carreteras = modelo.getGrafo().getCarreteras();
        for (Carretera car : carreteras) {
            // Obtener las coordenadas de los nodos origen y destino
            Nodo origen = nodos.get(obtenerIndiceNodo(car.getPob1().getPoblacion()));
            Nodo destino = nodos.get(obtenerIndiceNodo(car.getPob2().getPoblacion()));
            // Crear la arista y añadirla a la lista
            this.aristas.add(new Arista(origen, destino));
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

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void clear() {
        Graphics g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}