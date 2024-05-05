package Vista;
import Main.Main;
import Modelo.Poblacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelIndice extends JPanel {

    private final Main prog;
    private final JScrollPane scrollPane;

    public PanelIndice(Main prog) {
        this.prog = prog;
        this.setPreferredSize(new Dimension(200, this.getHeight()));

        // Crear el JScrollPane y agregar este panel a él
        scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
        int i = 0;
        ArrayList<Poblacion> poblaciones = new ArrayList<>(prog.getModelo().getGrafo().getPoblaciones().values());
        for(Poblacion poblacion : poblaciones) {
            g.setColor(Color.BLACK);
            g.drawString((i + 1) + ": " + poblacion.getPoblacion(), 0, (i * 15) + 15);
            i++;
        }
    }


}
