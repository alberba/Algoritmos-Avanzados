package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;

import java.awt.*;

public class PanelGrafico extends JTextArea {

    private Modelo modelo;
    private static final int anchura = 1200;
    private static final int altura = 1000;

    public PanelGrafico(Main p) {
        modelo = p.getModelo();
        this.setPreferredSize(new Dimension(anchura, altura));
        this.setEditable(false);
        this.setLineWrap(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(anchura, altura);
    }

    public void añadirTexto(String texto) {
        this.append(texto);
    }

    @Override
    public void repaint() {
        super.repaint();
        this.revalidate();
    }

}