package Vista;

import Main.Main;
import Notification.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CorreccionPanel extends JDialog implements ActionListener {
    private final JButton okButton, corregirButton;
    private final Main prog;
    ArrayList<String> palabrasIncorrectasOrdenadas = new ArrayList<>();
    private JLabel label;
    private JComboBox<String> comboBox;

    public CorreccionPanel(Main prog, Vista vista) {
        this.setLayout(new BorderLayout());
        this.prog = prog;
        JPanel buttons = new JPanel();

        this.corregirButton = new JButton("Corregir");
        this.corregirButton.addActionListener(this);
        buttons.add(corregirButton);

        this.okButton = new JButton("OK");
        this.okButton.addActionListener(this);
        buttons.add(okButton);
        this.add(buttons, BorderLayout.SOUTH);

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.corregirButton) {
            this.notificar();
        } else if (e.getSource() == this.okButton) {
            this.dispose();
        }
    }

    public void notificar() {

    }

    public boolean hayPalabrasIncorrectas() {
        return !palabrasIncorrectasOrdenadas.isEmpty();
    }
}