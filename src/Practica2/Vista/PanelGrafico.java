package Practica2.Vista;

import Practica2.Main.Main;

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

    public void paintComponent(Graphics g, int profundidad) {
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
    }
    @Override
    public void repaint() {
        Graphics g = this.getGraphics();
        if (g != null) {
            paint(g);
        }
    }

}