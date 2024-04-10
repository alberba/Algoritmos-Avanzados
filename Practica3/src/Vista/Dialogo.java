package Vista;

import Modelo.Distribucion;
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

    public Dialogo(Main prog, int bucketsActual) {
        this.interior = new JPanel();
        this.interior.add(new NBucketsPanel(bucketsActual));
        this.interior.add(new TypePanel());
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
            if (interior instanceof NBucketsPanel)
                prog.getModelo().notificar(NotiEnum.SETPARAM, ((NBucketsPanel) interior).getNBuckets());
            if (interior instanceof TypePanel){
                if (((TypePanel) interior).getTipo().equals("Uniforme"))
                    prog.getModelo().notificar(NotiEnum.SETPARAM, Distribucion.UNIFORME);
                else
                    prog.getModelo().notificar(NotiEnum.SETPARAM, Distribucion.GAUSSIANA);
            }
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
