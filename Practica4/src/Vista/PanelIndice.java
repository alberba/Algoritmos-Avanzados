package Vista;
import Main.Main;
import Modelo.Poblacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelIndice extends JPanel {

    private final Main prog;

    public PanelIndice(Main prog) {
        this.prog = prog;
        this.setPreferredSize(new Dimension(100, 100));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
        int i = 0;
        ArrayList<Poblacion> poblaciones = new ArrayList<>(prog.getModelo().getGrafo().getPoblaciones().values());
        for(Poblacion poblacion : poblaciones) {
            g.setColor(Color.BLACK);
            g.drawString((i + 1) + ": " + poblacion.getPoblacion(), 0, (i * 15) + 5);
            i++;
        }
    }


}
