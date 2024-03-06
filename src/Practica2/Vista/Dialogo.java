package Practica2.Vista;

import Practica2.Main.Main;
import Practica2.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private JButton okButton = new JButton("OK");
    private Main prog;
    private JTextField numero;
    private JPanel interior = new JPanel();

    public Dialogo(Main prog, int profActual) {
        this.prog = prog;
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Profundidad: "));
        numero = new JTextField(4);
        numero.setText(Integer.toString(profActual));
        okButton.addActionListener(this);
        interior.add(numero);
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
