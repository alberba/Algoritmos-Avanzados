package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class NPoblacionesPanel extends JPanel {

    private JTextField nPoblaciones;

    public NPoblacionesPanel(int numPoblaciones) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Numero de Poblaciones "));
        nPoblaciones = new JTextField(4);
        nPoblaciones.setText(Integer.toString(numPoblaciones));
        this.add(nPoblaciones);
    }

    public int getNPoblaciones() {
        try {
            return Integer.parseInt(nPoblaciones.getText());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
            return 0;
        }
    }

}
