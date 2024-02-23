package AlgoritmosN.Vista;


import AlgoritmosN.Main.Main;
import AlgoritmosN.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener {

    private final Main prog;
    private final JButton iniButton, stopButton;
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

}
