package Vista;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGraficoDibujo extends JPanel {

    private final Main prog;

    public PanelGraficoDibujo(Main p) {
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

        ArrayList<Integer> arrayCifras = prog.getModelo().getCifras();
        ArrayList<Long> arrayTiempos = prog.getModelo().getTiempos();

        if (arrayCifras.size() >= 2) {
            // Escalas para los ejes
            //int margen = 10;
            int maxX = arrayCifras.stream().max(Integer::compareTo).get();
            long maxY = arrayTiempos.stream().max(Long::compareTo).get();
            double scaleX = (double) this.getWidth() / maxX;
            double scaleY = (double) this.getHeight() / maxY;

            // Recorrido para pintar la gráfica
            // Se recorren los tiempos de cada algoritmo
            g.setColor(Color.BLUE);
            for(int i = 0; i < (arrayTiempos.size() - 1); i++) {
                int x1 = (int) (arrayCifras.get(i) * scaleX);
                // Se invierte el eje Y para que la gráfica tenga una visión más intuitiva
                int y1 = (int) (this.getHeight() - (arrayTiempos.get(i) * scaleY));
                int x2 = (int) (arrayCifras.get(i + 1) * scaleX);
                // Se invierte el eje Y para que la gráfica tenga una visión más intuitiva
                int y2 = (int) (this.getHeight() - (arrayTiempos.get(i + 1) * scaleY));
                // Une los puntos con una línea
                g.drawLine(x1, y1, x2, y2);
            }
        }

    }
}