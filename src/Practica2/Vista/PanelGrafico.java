package Practica2.Vista;

import Practica2.Main.Main;
import Practica2.Modelo.Formas.Cuadrado;
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

    // Función recursiva para dibujar contornos de cuadrados
    public void drawSquareOutline(Graphics g, int centerX, int centerY, int size, int profundidad, Color[] colors) {
        // Dibujar el contorno del cuadrado actual con el color correspondiente al nivel
        int topLeftX = centerX - size / 2;
        int topLeftY = centerY - size / 2;
        g.setColor(colors[profundidad % 4]);
        g.drawRect(topLeftX, topLeftY, size, size);

        // Caso base: si el nivel es 0, detener la recursión
        if (profundidad == 0)
            return;

        // Calcular el tamaño de los cuadrados más pequeños
        int smallerSize = size / 2;

        // Calcular las coordenadas de los centros de los cuadrados más pequeños
        int[] smallerCenterX = {centerX - size / 2, centerX + size / 2, centerX + size / 2, centerX - size / 2};
        int[] smallerCenterY = {centerY - size / 2, centerY - size / 2, centerY + size / 2, centerY + size / 2};

        // Dibujar los contornos de los cuadrados más pequeños recursivamente
        for (int i = 0; i < 4; i++) {
            drawSquareOutline(g, smallerCenterX[i], smallerCenterY[i], smallerSize, profundidad - 1, colors);
        }
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

        for (Object poligono : poligonos) {
            if (poligono instanceof Cuadrado cuadrado) {
                g.setColor(selectColor(cuadrado, colores));
                g.drawRect(cuadrado.getPunto().getX(), cuadrado.getPunto().getY(), cuadrado.getLado(), cuadrado.getLado());

            }
        }
    }

    private Color selectColor(Cuadrado cuadrado, Color[] colores) {
        switch (cuadrado.getLado()) {
            case 256:
                return colores[0];
            case 128:
                return colores[1];
            case 64:
                return colores[2];
            case 32:
                return colores[3];
            case 16:
                return colores[4];
            case 8:
                return colores[5];
            case 4:
                return colores[6];
            case 2:
                return colores[7];
        }
        return colores[0];
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