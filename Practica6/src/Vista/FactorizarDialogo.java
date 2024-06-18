package Vista;

import Main.Main;
import Notification.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactorizarDialogo extends JDialog implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final JPanel interior;

    public FactorizarDialogo(Main prog, String num) {
        JLabel label = new JLabel("Número a factorizar:");
        JTextField textField = new JTextField();
        textField.setText(num);

        this.interior = new JPanel();
        interior.add(label);
        interior.add(textField);

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
            String num = ((JTextField) ((JPanel) this.getContentPane().getComponent(0)).getComponent(1)).getText();
            prog.notificar(NotiEnum.FACTORIZAR, num);
            this.dispose();
        }
    }
}
