package Vista;

import Main.Main;
import Modelo.Modelo;
import Notification.NotiEnum;
import Notification.Notificacion;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Vista extends JFrame implements ActionListener, Notificacion {

    private final Main prog;
    private final JButton factorizarButton, claveRSAButton, encriptarButton, desencriptarButton, comprimirFicheroButton;
    private final PanelGrafico panel;
//    private final PanelGraficoDibujo panelDibujo;

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
        comprimirFicheroButton = new JButton("Comprimir fichero");
        comprimirFicheroButton.addActionListener(this);
        buttons.add(comprimirFicheroButton);

        this.add(BorderLayout.NORTH, buttons);


        // INSERCIÓN DE PANEL
        panel = new PanelGrafico(p);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);

        // PANEL GRAFICO DIBUJO
//        panelDibujo = new PanelGraficoDibujo(p);
//        this.add(panelDibujo, BorderLayout.CENTER);

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
            Dialogo dialogo = new Dialogo(prog, "Número a factorizar:", NotiEnum.FACTORIZAR, null);
            panel.setText("");
        } else if (e.getSource() == claveRSAButton) {
            prog.notificar(NotiEnum.RSA, null);
        } else if (e.getSource() == encriptarButton) {
            JFileChooser fileChooser = new JFileChooser();
            // El directorio inicial por defecto será la carpeta del proyecto
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Se ha escogido un fichero
                Dialogo dialogo = new Dialogo(prog, "Introduce la clave pública:", NotiEnum.ENCRIPTAR, fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == desencriptarButton) {
            JFileChooser fileChooser = new JFileChooser();
            // El directorio inicial por defecto será la carpeta del proyecto
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Se ha escogido un fichero
                Dialogo dialogo = new Dialogo(prog, "Introduce la clave privada:", NotiEnum.DESENCRIPTAR, fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == comprimirFicheroButton) {
            JFileChooser fileChooser = new JFileChooser();
            // El directorio inicial por defecto será la carpeta del proyecto
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Se ha escogido un fichero
                prog.notificar(NotiEnum.COMPRIMIR, fileChooser.getSelectedFile().getAbsolutePath());
            }

        }
    }

    public PanelGrafico getPanel() {
        return panel;
    }

    public Main getProg() {
        return prog;
    }

    @Override
    public void notificar(NotiEnum s, Object message) {
        if (s == NotiEnum.ADDOUTPUT) {
            panel.añadirTexto((String) message);
        } else if (s == NotiEnum.PINTARGRAFICO) {
            Modal m = new Modal(this, "Tiempos de Factorización");
            m.setVisible(true);
        }
    }
}
