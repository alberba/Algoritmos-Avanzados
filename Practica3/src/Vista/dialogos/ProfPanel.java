package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class ProfPanel extends JPanel {

    private JTextField numero;

    public ProfPanel(int profActual) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Profundidad: "));
        numero = new JTextField(4);
        numero.setText(Integer.toString(profActual));
        this.add(numero);
    }

    public int getProfundidad() {
        try {
            return Integer.parseInt(numero.getText());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
            return 0;
        }
    }
}
