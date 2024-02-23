package AlgoritmosN.Vista;

import AlgoritmosN.Main.Main;

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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        // IMPLEMENTAR AQUI LA GRÁFICA
        ArrayList<Integer> arrayN = prog.getModelo().getArrayN();
        ArrayList<ArrayList<Long>> arrayT = prog.getModelo().getTiemposN();
        Color [] colores = {Color.BLUE, Color.RED, Color.GREEN};

        if (arrayN.size() >= 2) {
            // Escalas para los ejes
            int maxX = arrayN.get(arrayN.size() - 1);
            long maxY = arrayT.stream().flatMapToLong(list -> list.stream().mapToLong(Long::longValue)).max().getAsLong();
            double scaleX = (double) this.getWidth() / maxX;
            double scaleY = (double) this.getHeight() / maxY;

            for(int i = 0; i < arrayT.size(); i++) {
                for(int j = 0; j < arrayT.get(i).size() - 1; j++) {
                    g.setColor(colores[i]);
                    int x1 = (int) (arrayN.get(j) * scaleX);
                    int y1 = (int) (arrayT.get(i).get(j) * scaleY);
                    int x2 = (int) (arrayN.get(j + 1) * scaleX);
                    int y2 = (int) (arrayT.get(i).get(j + 1) * scaleY);
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }

    }
}
