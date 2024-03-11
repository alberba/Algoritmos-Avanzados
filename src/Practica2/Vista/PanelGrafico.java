package Practica2.Vista;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.*;
import Practica2.Modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGrafico extends JPanel {

    private final Main prog;
    public static final int SIZE = 768;

    public PanelGrafico(Main p) {
        prog = p;
        this.setPreferredSize(new Dimension(SIZE, SIZE));
    }

    /*public void paintComponent(Graphics g, int profundidad) {
        super.paintComponent(g);

        // Tamaño del cuadrado inicial
        int size = 200;
        // Dimensiones del panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Coordenadas del centro del cuadrado inicial
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        // Colores para cada nivel
        Color[] colors = {Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED};

        // Dibujar contornos de cuadrados
        drawSquareOutline(g, centerX, centerY, size, profundidad, colors);
    }*/

    public void paint(Graphics g) {
        Modelo modelo = prog.getModelo();
        ArrayList<Object> poligonos = modelo.getPoligonos();
        Color[] colores = {Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK};

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < poligonos.size(); i++) { // Enhanced for parece dar error, es preferible no usarlo
            // Caso de cuadrados
            if (poligonos.get(i) instanceof Cuadrado cuadrado) {
                g.setColor(selectColor(cuadrado, colores));
                g.drawRect(cuadrado.getPunto().getX(), cuadrado.getPunto().getY(), cuadrado.getLado(), cuadrado.getLado());
            } else if (poligonos.get(i) instanceof Triangulo triangulo) { // Caso de triángulos
                g.setColor(Color.BLACK);
                // Obtención de los vértices
                Punto[] puntos = triangulo.getPuntos();
                // Se dibujan los lados del triángulo
                g.drawLine(puntos[0].getX(), puntos[0].getY(), puntos[1].getX(), puntos[1].getY());
                g.drawLine(puntos[1].getX(), puntos[1].getY(), puntos[2].getX(), puntos[2].getY());
                g.drawLine(puntos[2].getX(), puntos[2].getY(), puntos[0].getX(), puntos[0].getY());
            } else {
                System.out.println("Error: polígono no reconocido");
            }
        }
    }

    private Color selectColor(Cuadrado cuadrado, Color[] colores) {
        return switch (cuadrado.getLado()) {
            case 128 -> colores[1];
            case 64 -> colores[2];
            case 32 -> colores[3];
            case 16 -> colores[4];
            case 8 -> colores[5];
            case 4 -> colores[6];
            case 2 -> colores[7];
            default -> colores[0];
        };
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