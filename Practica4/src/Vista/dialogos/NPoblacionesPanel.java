package Vista.dialogos;

import javax.swing.*;
import java.awt.*;

public class NPoblacionesPanel extends JPanel {

    private JTextField nPoblaciones;
    private JTextField nCarreteras;

    public NPoblacionesPanel(int numPoblaciones, int numCarreteras) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Numero de Poblaciones: "));
        nPoblaciones = new JTextField(4);
        nPoblaciones.setText(Integer.toString(numPoblaciones));
        this.add(nPoblaciones);
        //nPoblaciones.setEnabled(false);
        this.add(new JLabel("Numero mínimo de carreteras: "));
        nCarreteras = new JTextField(4);
        nCarreteras.setText(Integer.toString(numCarreteras));
        this.add(nCarreteras);
    }

    public int getNPoblaciones() {
        try {
            if (Integer.parseInt(nPoblaciones.getText()) < 2) {
                JOptionPane.showMessageDialog(this, "El valor debe ser mayor o igual a 2.", "Valor incorrecto", JOptionPane.ERROR_MESSAGE);
                return 20;
            }
            return Integer.parseInt(nPoblaciones.getText());
        } catch (Exception e) {
            // Notificar mediante pop-up el error
            JOptionPane.showMessageDialog(this, "El valor debe ser numérico entero.", "Valor incorrecto", JOptionPane.ERROR_MESSAGE);
            return 20;
        }
    }

    public int getNCarreteras() {
        try {
            if (Integer.parseInt(nCarreteras.getText()) < 2) {
                JOptionPane.showMessageDialog(this, "El valor debe ser mayor o igual a 2.", "Valor incorrecto", JOptionPane.ERROR_MESSAGE);
                return 5;
            }
            return Integer.parseInt(nCarreteras.getText());
        } catch (Exception e) {
            // Notificar mediante pop-up el error
            JOptionPane.showMessageDialog(this, "El valor debe ser numérico entero.", "Valor incorrecto", JOptionPane.ERROR_MESSAGE);
            return 5;
        }
    }

}
