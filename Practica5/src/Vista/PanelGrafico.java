package Vista;

import Main.Main;
import Modelo.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PanelGrafico extends JTextPane {

    private Modelo modelo;
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

        // Definimos el subrayado
        SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
        StyleConstants.setUnderline(highlightStyle, true);
        StyleConstants.setForeground(highlightStyle, Color.RED);

        TreeMap<String, ArrayList<Candidato>> correcciones = modelo.getCorrecciones();
        // Comprueba si ya hay correcciones asignadas
        if (correcciones != null) {
            StyledDocument doc = this.getStyledDocument();
            String[] palabras = this.getText().toLowerCase().split("(\\s|\\.|,|\n)+");
            int pos = 0;
            for (String palabra : palabras) {
                if (correcciones.containsKey(palabra)) {
                    int wordLength = palabra.length();
                    // Subrayamos la palabra
                    doc.setCharacterAttributes(pos, wordLength, highlightStyle, false);
                }
                // +1 para saltar el espacio
                if((pos + palabra.length() < this.getText().length()) && (this.getText().charAt(pos + palabra.length()) == ' '))
                    pos += palabra.length() + 1;
                else
                    pos += palabra.length() + 2;
            }
        }
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setTexto(String texto) {
        this.setText(texto);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(anchura, altura);
    }

    public void resetStyle() {
        // Crear un nuevo SimpleAttributeSet sin ningún estilo
        SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
        StyleConstants.setUnderline(defaultStyle, false);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        // Obtener el StyledDocument
        StyledDocument doc = this.getStyledDocument();

        // Aplicar el estilo predeterminado a todo el texto
        doc.setCharacterAttributes(0, this.getText().length(), defaultStyle, true);
    }

    @Override
    public void repaint() {
        super.repaint();
        this.revalidate();
    }

}