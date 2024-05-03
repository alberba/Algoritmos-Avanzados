package Vista;

import Vista.dialogos.NDatosPanel;
import Vista.dialogos.TypePanel;
import Main.Main;
import Notification.NotiEnum;
import Vista.dialogos.NBucketsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;

    public Dialogo(Main prog, int bucketsActual, int nDatosActual) {
        this.interior = new JPanel();
        this.interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        this.interior.add(new NBucketsPanel(bucketsActual));
        this.interior.add(new NDatosPanel(nDatosActual));
        this.prog = prog;
        this.setLayout(new BorderLayout());
        okButton.addActionListener(this);
        this.add(interior, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);
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
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
