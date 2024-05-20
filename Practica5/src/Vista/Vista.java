package Vista;


import Main.Main;
import Notification.NotiEnum;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener {

    private final Main prog;
    private final JButton iniButton, resetButton, algoritmoButton, poblacionesButton, xmlButton;
    private final PanelGrafico panel;

    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 800));

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        iniButton = new JButton("Iniciar");
        iniButton.addActionListener(this);
        buttons.add(iniButton);
        resetButton = new JButton("Reiniciar");
        resetButton.addActionListener(this);
        buttons.add(resetButton);
        algoritmoButton = new JButton("Algoritmo");
        algoritmoButton.addActionListener(this);
        buttons.add(algoritmoButton);
        poblacionesButton = new JButton("Poblaciones");
        poblacionesButton.addActionListener(this);
        buttons.add(poblacionesButton);
        xmlButton = new JButton("Cambiar XML");
        xmlButton.addActionListener(this);
        buttons.add(xmlButton);
        this.add(buttons);
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

    public void resetPanel() {
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniButton) {
            prog.notificar(NotiEnum.INICIAR, null);
        } else if (e.getSource() == resetButton) {
        }
    }
}
