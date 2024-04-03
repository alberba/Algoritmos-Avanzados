package Vista;

import Vista.dialogos.TypePanel;
import Main.Main;
import Notification.NotiEnum;
import Vista.dialogos.EnumPolygon;
import Vista.dialogos.ProfPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;

    public Dialogo(Main prog, int profActual) {
        this.interior = new ProfPanel(profActual);
        this.prog = prog;
        this.setLayout(new FlowLayout());
        okButton.addActionListener(this);
        interior.add(okButton);
        this.add(interior);
        mostrar();
    }

    public Dialogo(Main prog) {
        this.interior = new TypePanel(null == EnumPolygon.CUADRADO);
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
            if (interior instanceof ProfPanel)
                prog.getModelo().notificar(NotiEnum.SETPARAM, ((ProfPanel) interior).getProfundidad());
            if (interior instanceof TypePanel){
                if (((TypePanel) interior).getTipo().equals("Cuadrado"))
                    prog.getModelo().notificar(NotiEnum.SETPARAM, EnumPolygon.CUADRADO);
                else
                    prog.getModelo().notificar(NotiEnum.SETPARAM, EnumPolygon.TRIANGULO);
            }
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos");
        }
    }
}
