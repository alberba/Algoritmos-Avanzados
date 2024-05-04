package Vista;


import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener, Notificacion {

    private final Main prog;
    private final JButton iniButton, stopButton, paramButton;
    private final PanelGrafico panel;
    private final PanelIndice panelIndice;
    private JScrollPane sp;

    private final JProgressBar progreso;
    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000, 800));

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        iniButton = new JButton("Iniciar");
        iniButton.addActionListener(this);
        buttons.add(iniButton);
        stopButton = new JButton("Parar");
        stopButton.addActionListener(this);
        buttons.add(stopButton);
        paramButton = new JButton("Parámetros");
        paramButton.addActionListener(this);
        buttons.add(paramButton);
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

        panelIndice = new PanelIndice(prog);
        this.add(panelIndice, BorderLayout.EAST);
        // INSERCIÓN DE BARRA DE PROGRESO
        progreso = new JProgressBar();
        progreso.setValue(0);
        // Se muestra el porcentaje de la barra, inicialmente 0%
        progreso.setString("0%");
        progreso.setStringPainted(true);
        this.add(progreso, BorderLayout.SOUTH);
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400, 0);
        this.setVisible(true);
    }

    public void resetPanel() {
        panel.clear();
        setValueProgreso(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniButton) {
            prog.notificar(NotiEnum.INICIAR, null);
        } else if (e.getSource() == stopButton) {
            prog.notificar(NotiEnum.PARAR, null);
        }
    }

    public void progreso() {
        int p = progreso.getValue();
        p = p == 100 ? 0 : p + 1;
        setValueProgreso(p);
    }

    public void setValueProgreso(int p) {
        progreso.setValue(p);
        progreso.setString(p + "%");
    }

    @Override
    public void notificar(NotiEnum s, Object o) {
        switch (s) {
            case DIBUJAR -> this.repaint();
            case PINTAR -> System.out.println("");
            case PROGRESO -> progreso();
            // case ESTIMAR -> System.out.println("Set texto");
            default -> System.out.println();
        }
    }
}
