package Vista;

import Main.Main;
import Modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGrafico extends JPanel {

    private final Main prog;
    private final Modelo modelo;
    private final ArrayList<Nodo> nodos;

    public PanelGrafico(Main p) {
        prog = p;
        modelo = prog.getModelo();
        nodos = new ArrayList<>();
        this.setPreferredSize(new Dimension(800, 600));
        generarNodos(this.getGraphics(), modelo.getPoblaciones());
    }
    // Método que dibuja el contenido del panel
    @Override
    public void paint(Graphics g) {
        // Obtener los datos del modelo
        Modelo modelo = prog.getModelo();
    }

    // Método que genera los 20 nodos del grafo
    private void generarNodos(Graphics g, ArrayList<String> nodos) {
        int x = 100;
        int y = 100;
        for (String nodo : nodos) {
            // Crear el nodo y añadirlo a la lista
            this.nodos.add(new Nodo(x, y, nodo));
            // Dibujar el nodo
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 10, 10);
            // Dibujar el nombre del nodo
            g.setColor(Color.BLACK);
            g.drawString(nodo, x, y);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }


    @Override
    public void repaint() {
        Graphics g = this.getGraphics();
        if (g != null) {
            paint(g);
        }
    }

    public void clear() {
        Graphics g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}