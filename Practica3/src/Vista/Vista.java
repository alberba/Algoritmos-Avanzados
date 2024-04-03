package Vista;


import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener, Notificacion {

    private final Main prog;
    private final JButton iniButton, stopButton, paramButton, patronButton;
    private final PanelGrafico panel;

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
        paramButton = new JButton("Parámetros");
        paramButton.addActionListener(this);
        buttons.add(paramButton);
        this.add(buttons);
        this.add(BorderLayout.NORTH, buttons);
        patronButton = new JButton("Patrón");
        patronButton.addActionListener(this);
        buttons.add(patronButton);

        // INSERCIÓN DE PANEL
        panel = new PanelGrafico(p);
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
        } else if (e.getSource() == paramButton) {
            // TODO: Fix this
            Dialogo dialogo = new Dialogo(prog, 0);
            dialogo.setVisible(true);

        } else if (e.getSource() == patronButton) {
            Dialogo dialogo = new Dialogo(prog);
            dialogo.setVisible(true);
        }
    }

    public void progreso() {
        int p = progreso.getValue();
        p++;
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
            case PROGRESO -> progreso();
            // case ESTIMAR -> System.out.println("Set texto");
            default -> System.out.println();
        }
    }
}
