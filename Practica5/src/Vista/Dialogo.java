package Vista;

import Vista.dialogos.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private TypePanel typePanel;
    private OrigenDestinoPanel origenDestinoPanel;


    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setLocation(400, 400);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
        }
    }

    public void notificar() {
    }
}
