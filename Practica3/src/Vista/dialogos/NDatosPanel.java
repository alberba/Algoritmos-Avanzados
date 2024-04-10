package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class NDatosPanel extends JPanel {

    private JTextField nDatos;

    public NDatosPanel(int nDatosActual) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Medida de los datos: "));
        nDatos = new JTextField(4);
        nDatos.setText(Integer.toString(nDatosActual));
        this.add(nDatos);
    }

    public int getNDatos() {
        try {
            return Integer.parseInt(nDatos.getText());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
            return 0;
        }
    }

}
