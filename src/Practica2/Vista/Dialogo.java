package Practica2.Vista;

import Practica2.Main.Main;
import Practica2.NotiEnum;
import Practica2.Vista.dialogos.ProfPanel;
import Practica2.Vista.dialogos.TypePanel;
import Practica2.Vista.dialogos.TypePolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private JButton okButton = new JButton("OK");
    private Main prog;
    private JTextField numero;
    private JPanel interior;

    public Dialogo(Main prog, int profActual) {
        this.interior = new ProfPanel(profActual);
        this.prog = prog;
        this.setLayout(new FlowLayout());
        okButton.addActionListener(this);
        interior.add(okButton);
        this.add(interior);
        mostrar();
    }

    public Dialogo(Main prog) {
        this.interior = new TypePanel();
        this.prog = prog;
        this.setLayout(new FlowLayout());
        okButton.addActionListener(this);
        interior.add(okButton);
        this.add(interior);
        mostrar();
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setLocation(400, 400);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            notificar();
            this.dispose();
        }
    }

    public void notificar() {
        try {
            prog.getModelo().notificar(NotiEnum.SETPARAM, Integer.parseInt(numero.getText()));
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
