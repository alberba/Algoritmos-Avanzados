package Vista;

import Main.Main;
import Notification.NotiEnum;
import Notification.Notificacion;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame implements ActionListener, Notificacion {

    private final Main prog;
    private final JButton factorizarButton, claveRSAButton, encriptarButton, desencriptarButton;
    private final PanelGrafico panel;

    public Vista(String title, Main p) {
        super(title);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 800));

        // INSERCIÓN DE BOTONES
        JPanel buttons = new JPanel();
        factorizarButton = new JButton("Factorizar");
        factorizarButton.addActionListener(this);
        buttons.add(factorizarButton);
        claveRSAButton = new JButton("Generador clave RSA");
        claveRSAButton.addActionListener(this);
        buttons.add(claveRSAButton);
        encriptarButton = new JButton("Encriptar fichero");
        encriptarButton.addActionListener(this);
        buttons.add(encriptarButton);
        desencriptarButton = new JButton("Desencriptar fichero");
        desencriptarButton.addActionListener(this);
        buttons.add(desencriptarButton);
        this.add(BorderLayout.NORTH, buttons);


        // INSERCIÓN DE PANEL
        panel = new PanelGrafico(p);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);

        panel.repaint();
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400, 0);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == factorizarButton) {
            FactorizarDialogo factorizarDialogo = new FactorizarDialogo(prog, "2305843009213693951");
            panel.setText("");
        } else if (e.getSource() == claveRSAButton) {

        } else if (e.getSource() == encriptarButton) {

        }
    }

    public PanelGrafico getPanel() {
        return panel;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        if(s == NotiEnum.ADDOUTPUT){
            panel.añadirTexto((String) message);
        }
    }
}
