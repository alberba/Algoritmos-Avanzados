package Vista;

import Main.Main;
import Modelo.Modelo;
import Modelo.AlgoritmoEnum;

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

        if (tiempos.isEmpty()) {
            return;
        }
        // Calcular dimensiones para dibujar las barras
        int barWidth = (getWidth() - 120) / tiempos.size(); // Ancho de cada barra
        long valorMax = getMaxValue(tiempos); // Valor máximo de los tiempos
        long franjas = valorMax / 10; // Rango de valores
        int panelHeight = getHeight() - 50; // Altura del panel
        int stepsEscala = panelHeight / 10; // El divisor indica el número de valores indicados en la escala

        // Dibujar la escala de valores en el lado izquierdo del panel
        g.setColor(Color.BLACK);
        for (int i = 0; i <= 10; i++) {
            int y = panelHeight - i * stepsEscala; // Calcular posición en función del paso
            g.drawString(String.valueOf((i * franjas) / 1000000) + "ms", 5, y + 5); // Mostrar el valor de la escala
            g.drawLine(45, y, 50, y); // Dibujar una línea para indicar el step
        }

        // Dibujar las barras para cada tamaño de array
        int x = 60; // Posición inicial de la primera barra
        for (int i = 0; i < tiempos.size(); i++) {
            Long tiempo = tiempos.get(i); // Obtener los tiempos para este tamaño de array
            int barHeight = (int) ((double) tiempo / valorMax * panelHeight); // Calcular altura de la barra en función del tiempo
            int y = panelHeight - barHeight; // Calcular posición vertical de la barra
            g.setColor(selectColor(modelo.getAlgoritmo(i))); // Establecer el color de la barra
            g.fillRect(x, y, barWidth, barHeight); // Dibujar la barra
            g.setColor(Color.BLACK); // Establecer color del borde de la barra
            g.drawRect(x, y, barWidth, barHeight); // Dibujar borde de la barra


            // Agregar el nombre del algoritmo debajo de la barra
            String nombreAlgoritmo = obtenerNombre(prog.getModelo(), i);
            g.drawString(nombreAlgoritmo, x + barWidth / 2 - g.getFontMetrics().stringWidth(nombreAlgoritmo) / 2, getHeight() - 5);


            x += barWidth + 5; // Mover posición horizontal para dibujar la próxima barra (con separación)
        }
    }

    /**
     * Función que devuelve el color dependiendo del tipo de algoritmo
     * @param algoritmoEnum
     * @return Color
     */
    private Color selectColor(AlgoritmoEnum algoritmoEnum) {
        return switch (algoritmoEnum) {
            case BUCKETSORT -> Color.YELLOW;
            case QUICKSORT -> Color.GREEN;
            case TIMSORT -> Color.BLUE;
            case TREESORT -> Color.RED;
            default -> throw new IllegalStateException("Unexpected value " + algoritmoEnum);
        };
    }

    /**
     * Obtiene el nombre del algoritmo dado su índice y le da formato (primera letra mayúscula, el resto en minúsculas)
     * @param modelo
     * @param i
     * @return
     */
    private String obtenerNombre(Modelo modelo, int i) {
        String nombre = modelo.getAlgoritmo(i).name();
        nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        return nombre;
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