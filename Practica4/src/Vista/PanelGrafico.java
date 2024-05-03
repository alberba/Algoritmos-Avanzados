package Vista;

import Main.Main;
import Modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGrafico extends JPanel {

    private final Main prog;

    public PanelGrafico(Main p) {
        prog = p;
        this.setPreferredSize(new Dimension(800, 600));
    }
    // Método que dibuja el contenido del panel
    @Override
    public void paint(Graphics g) {
        // Obtener los datos del modelo
        Modelo modelo = prog.getModelo();
    }

    // Método para obtener el valor máximo de los tiempos de ejecución
    private long getMaxValue(ArrayList<Long> tiemposN) {
        long max = 0;
        // Iterar sobre todos los tiempos para encontrar el máximo
        for (Long tiempo : tiemposN) {
            if (tiempo > max) {
                max = tiempo;
            }
        }
        return max; // Devolver el máximo valor encontrado
    }

    // Método para obtener el valor máximo de los tiempos de ejecución
    private long getMinValue(ArrayList<Long> tiemposN) {
        long min = Long.MAX_VALUE;
        // Iterar sobre todos los tiempos para encontrar el máximo
        for (Long tiempo : tiemposN) {
            if (tiempo < min) {
                min = tiempo;
            }
        }
        return min; // Devolver el máximo valor encontrado
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