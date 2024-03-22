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

    public void paint(Graphics g) {
        Modelo modelo = prog.getModelo();
        ArrayList<Object> poligonos = modelo.getPoligonos();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < poligonos.size(); i++) { // Enhanced for parece dar error, es preferible no usarlo
            // Caso de cuadrados
            if (poligonos.get(i) instanceof Cuadrado cuadrado) {
                g.setColor(selectColor(cuadrado));
                g.drawRect(cuadrado.getPunto().getX(), cuadrado.getPunto().getY(), cuadrado.getLado(), cuadrado.getLado());
            } else if (poligonos.get(i) instanceof Triangulo triangulo) { // Caso de triángulos
                g.setColor(Color.BLACK);
                // Obtención de los vértices
                Polygon pol = new Polygon(triangulo.getX(), triangulo.getY(), Triangulo.getN_VERTICES());
                g.fillPolygon(pol);
            } else {
                System.out.println("Error: polígono no reconocido");
            }
        }
    }

    /**
     * Función que devuelve el color dependiendo del lado del cuadrado
     * @param cuadrado Cuadrado
     * @return Color
     */
    private Color selectColor(Cuadrado cuadrado) {
        return switch (cuadrado.getLado()) {
            case 256 -> Color.YELLOW;
            case 128 -> Color.GREEN;
            case 64 -> Color.BLUE;
            case 32 -> Color.RED;
            case 16 -> Color.MAGENTA;
            case 8 -> Color.CYAN;
            case 4 -> Color.ORANGE;
            case 2 -> Color.PINK;
            default -> throw new IllegalStateException("Unexpected value: " + cuadrado.getLado());
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