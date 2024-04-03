package Vista;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGrafico extends JPanel {

    private final Main prog;

    public PanelGrafico(Main p) {
        prog = p;
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void repaint() {
        Graphics g = this.getGraphics();
        if (g != null) {
            paint(g);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void paint(Graphics g) {
        // Se pinta el fondo de blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Integer> arrayN = prog.getModelo().getArrayN();
        ArrayList<ArrayList<Long>> arrayT = prog.getModelo().getTiemposN();
        // Para que la gráfica sea más visual, se usan colores para cada tipo de algoritmo
        Color [] colores = {Color.BLUE, Color.RED, Color.GREEN};

        if (arrayN.size() >= 2) {
            // Escalas para los ejes
            int maxX = arrayN.get(arrayN.size() - 1);
            long maxY = arrayT.stream().flatMapToLong(list -> list.stream().mapToLong(Long::longValue)).max().getAsLong();
            double scaleX = (double) this.getWidth() / maxX;
            double scaleY = (double) this.getHeight() / maxY;

            // Recorrido para pintar la gráfica
            // Se recorren los tiempos de cada algoritmo
            for(int i = 0; i < arrayT.size(); i++) {
                // Se recorren los tiempos para cada N
                for(int j = 0; j < arrayT.get(i).size() - 1; j++) {
                    g.setColor(colores[i]);
                    int x1 = (int) (arrayN.get(j) * scaleX);
                    // Se invierte el eje Y para que la gráfica tenga una visión más intuitiva
                    int y1 = (int) (this.getHeight() - (arrayT.get(i).get(j) * scaleY));
                    int x2 = (int) (arrayN.get(j + 1) * scaleX);
                    // Se invierte el eje Y para que la gráfica tenga una visión más intuitiva
                    int y2 = (int) (this.getHeight() - (arrayT.get(i).get(j + 1) * scaleY));
                    // Une los puntos con una línea
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }

    }
}