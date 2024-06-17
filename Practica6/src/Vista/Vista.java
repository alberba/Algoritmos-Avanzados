package Vista;

import Main.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener {

    private final Main prog;
    private final JButton iniciaButton, ficheroButton, correccionButton;
    private final PanelGrafico panel;

    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 800));

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        iniciaButton = new JButton("Comprobar texto");
        iniciaButton.addActionListener(this);
        buttons.add(iniciaButton);
        ficheroButton = new JButton("Abrir fichero");
        ficheroButton.addActionListener(this);
        buttons.add(ficheroButton);
        correccionButton = new JButton("Corregir");
        correccionButton.addActionListener(this);
        buttons.add(correccionButton);
        this.add(BorderLayout.NORTH, buttons);


        // INSERCIÓN DE PANEL
        panel = new PanelGrafico(p);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);

        panel.repaint();
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400, 0);
        this.setVisible(true);
    }

    public void resetTextPane() {
        panel.setModelo(prog.getModelo());
        panel.repaint();
        panel.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciaButton) {

        } else if (e.getSource() == ficheroButton) {

        } else if (e.getSource() == correccionButton) {

        }
    }

    public PanelGrafico getPanel() {
        return panel;
    }
}
