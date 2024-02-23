package Vista;


import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener {

    private Main prog;
    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel buttons = new JPanel();
        JButton button1 = new JButton("Iniciar");
        button1.addActionListener(this);
        buttons.add(button1);
        JButton button2 = new JButton("Parar");
        button2.addActionListener(this);
        buttons.add(button2);
        this.add(buttons);
        this.add(BorderLayout.NORTH, buttons);
        PanelGrafico panel = new PanelGrafico();
        this.add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
