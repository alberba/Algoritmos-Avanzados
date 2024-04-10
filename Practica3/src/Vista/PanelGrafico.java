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
        ArrayList<Long> tiempos = modelo.getTiempos(); // Tiempos de ejecución

        // Calcular dimensiones para dibujar las barras
        int barWidth = (getWidth() - 100) / tiempos.size(); // Ancho de cada barra
        int maxValue = getMaxValue(tiempos); // Valor máximo de los tiempos
        int panelHeight = getHeight() - 50; // Altura del panel
        int scaleStep = panelHeight / 10; // El divisor indica el número de valores indicados en la escala

        // Dibujar la escala de valores en el lado izquierdo del panel
        g.setColor(Color.BLACK);
        for (int i = 0; i <= 10; i++) {
            int y = panelHeight - i * scaleStep; // Calcular posición en función del paso
            g.drawString(String.valueOf(i * 10), 5, y + 5); // Mostrar el valor de la escala
            g.drawLine(30, y, 35, y); // Dibujar una línea para indicar el paso
        }

        // Dibujar las barras para cada tamaño de array
        int x = 50; // Posición inicial de la primera barra
        for (int i = 0; i < tiempos.size(); i++) {
            Long tiempo = tiempos.get(i); // Obtener los tiempos para este tamaño de array
            int barHeight = (int) ((double) tiempo / maxValue * panelHeight); // Calcular altura de la barra en función del tiempo
            int y = panelHeight - barHeight; // Calcular posición vertical de la barra
            g.setColor(Color.BLUE); // Establecer el color de la barra
            g.fillRect(x, y, barWidth, barHeight); // Dibujar la barra
            g.setColor(Color.BLACK); // Establecer color del borde de la barra
            g.drawRect(x, y, barWidth, barHeight); // Dibujar borde de la barra


            // Agregar el nombre del algoritmo debajo de la barra
            String nombreAlgoritmo = modelo.getAlgoritmo(i);
            g.drawString(nombreAlgoritmo, x + barWidth / 2 - g.getFontMetrics().stringWidth(nombreAlgoritmo) / 2, getHeight() - 5);


            x += barWidth + 5; // Mover posición horizontal para dibujar la próxima barra (con separación)
        }
    }

    // Método para obtener el valor máximo de los tiempos de ejecución
    private int getMaxValue(ArrayList<Long> tiemposN) {
        long max = Long.MIN_VALUE;
        // Iterar sobre todos los tiempos para encontrar el máximo
        for (Long tiempo : tiemposN) {
            if (tiempo > max) {
                max = tiempo;
            }
        }
        return (int) max; // Devolver el máximo valor encontrado
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