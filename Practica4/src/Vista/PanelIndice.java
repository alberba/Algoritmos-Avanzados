package Vista;
import Main.Main;
import Modelo.Poblacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelIndice extends JPanel {

    private final Main prog;
    private final JScrollPane scrollPane;
    private JTextArea textArea;

    public PanelIndice(Main prog) {
        this.prog = prog;
        this.setPreferredSize(new Dimension(200, 1000));

        textArea = new JTextArea();
        textArea.setEditable(false);

        // Crear el JScrollPane y agregar este panel a él
        scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);
        actualizarJTextArea();
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    void actualizarJTextArea() {
        int i = 0;
        ArrayList<Poblacion> poblaciones = new ArrayList<>(prog.getModelo().getGrafo().getPoblaciones().values());
        StringBuilder poblacionString = new StringBuilder();
        for(Poblacion poblacion : poblaciones) {
            poblacionString.append((i + 1)).append(": ").append(poblacion.getPoblacion()).append("\n");
            i++;
        }
        textArea.setText(poblacionString.toString());
        textArea.setCaretPosition(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
    }


}
