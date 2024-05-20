package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;

import java.awt.*;

public class PanelGrafico extends JTextPane {

    private final Modelo modelo;
    private static final int anchura = 1200;
    private static final int altura = 1000;

    public PanelGrafico(Main p) {
        modelo = p.getModelo();
        this.setPreferredSize(new Dimension(anchura, altura));
        this.setEditable(true);
        this.setText(p.getModelo().getTexto().getTextoOriginal());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(anchura, altura);
    }

    @Override
    public void repaint() {
        super.repaint();
    }


}