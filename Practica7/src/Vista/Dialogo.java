package Vista;

import Main.Main;
import Notification.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;
    private final NotiEnum noti;
    private final String pathFichero;

    public Dialogo(Main prog, String texto, NotiEnum noti, String pathFichero) {
        JLabel label = new JLabel(texto);
        JTextField textField1 = new JTextField(15);

        this.noti = noti;
        this.pathFichero = pathFichero;

        this.interior = new JPanel();
        interior.setLayout(new BoxLayout(interior, BoxLayout.Y_AXIS));
        interior.add(label);
        interior.add(textField1);
        if (noti == NotiEnum.ENCRIPTAR || noti == NotiEnum.DESENCRIPTAR) {
            JLabel label2 = new JLabel("N:");
            JTextField textField2 = new JTextField(15);
            interior.add(label2);
            interior.add(textField2);
        }

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
            String texto = ((JTextField) ((JPanel) this.getContentPane().getComponent(0)).getComponent(1)).getText();
            if(noti == NotiEnum.ENCRIPTAR || noti == NotiEnum.DESENCRIPTAR) {
                String n = ((JTextField) ((JPanel) this.getContentPane().getComponent(0)).getComponent(3)).getText();
                texto = texto + "|" + n;
            }
            this.dispose();
            prog.getModelo().notificar(noti, texto);
            prog.notificar(noti, noti == NotiEnum.FACTORIZAR ? texto : pathFichero);
        }
    }
}
