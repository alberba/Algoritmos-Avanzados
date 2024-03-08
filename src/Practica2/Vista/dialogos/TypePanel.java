package Practica2.Vista.dialogos;

import javax.swing.*;
import java.util.Enumeration;

public class TypePanel extends JPanel {

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

    public String getTipo() {
        for (Enumeration<AbstractButton> buttons = grupo.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
