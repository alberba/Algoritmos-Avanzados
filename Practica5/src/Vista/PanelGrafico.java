package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PanelGrafico extends JTextPane {

    private final Modelo modelo;
    private static final int anchura = 1200;
    private static final int altura = 1000;

    public PanelGrafico(Main p) {
        modelo = p.getModelo();
        this.setPreferredSize(new Dimension(anchura, altura));
        this.setEditable(true);
        this.setText(p.getModelo().getTexto().getTextoOriginal());
        this.setFont(new Font("Roboto", Font.PLAIN, 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        // Definimos el subrayado
        SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
        StyleConstants.setUnderline(highlightStyle, true);
        StyleConstants.setForeground(highlightStyle, Color.RED);

        TreeMap<String, ArrayList<Candidato>> correcciones = modelo.getCorrecciones();
        // Comprueba si ya hay correcciones asignadas
        if (correcciones != null) {
            StyledDocument doc = this.getStyledDocument();
            String[] palabras = this.getText().split(" ");
            int pos = 0;
            for (String palabra : palabras) {
                if (correcciones.containsKey(palabra)) {
                    int wordLength = palabra.length();
                    // Subrayamos la palabra
                    doc.setCharacterAttributes(pos, wordLength, highlightStyle, false);
                }
                // +1 para saltar el espacio
                pos += palabra.length() + 1;
            }
        }
    }

    public void setTexto(String texto) {
        this.setText(texto);
        this.repaint();
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