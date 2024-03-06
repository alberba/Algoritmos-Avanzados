package Practica2.Vista.dialogos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypePanel extends JPanel implements ActionListener {

    private ButtonGroup grupo;

    public TypePanel() {
        grupo = new ButtonGroup();
        JRadioButton r1 = new JRadioButton("Cuadrado");
        JRadioButton r2 = new JRadioButton("Triangulo");
        grupo.add(r1);
        grupo.add(r2);
        this.add(r1);
        this.add(r2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
