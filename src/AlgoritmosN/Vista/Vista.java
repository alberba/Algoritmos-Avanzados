package AlgoritmosN.Vista;


import AlgoritmosN.Main.Main;
import AlgoritmosN.NotiEnum;
import AlgoritmosN.Notificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener, Notificacion {

    private final Main prog;
    private final JButton iniButton, stopButton;
    private final JProgressBar progreso;
    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        iniButton = new JButton("Iniciar");
        iniButton.addActionListener(this);
        buttons.add(iniButton);
        stopButton = new JButton("Parar");
        stopButton.addActionListener(this);
        buttons.add(stopButton);
        this.add(buttons);
        this.add(BorderLayout.NORTH, buttons);

        // INSERCIÓN DE PANEL
        PanelGrafico panel = new PanelGrafico(p);
        this.add(BorderLayout.CENTER, panel);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniButton) {
            prog.notificar(NotiEnum.INICIAR);
        } else if (e.getSource() == stopButton) {
            prog.notificar(NotiEnum.PARAR);
        }
    }

    public void progreso() {
        int p = progreso.getValue();
        p++;
        if (p > 100) {
            p = 0;
        }
        progreso.setValue(p);
        progreso.setString(p + "%");
    }

    public void setValueProgreso(int p) {
        progreso.setValue(p);
        progreso.setString(p + "%");
    }

    @Override
    public void notificar(NotiEnum s) {
        if (s == NotiEnum.DIBUJAR) {
            this.repaint();
        } else if (s == NotiEnum.PROGRESO) {
            progreso();
        }
    }
}
