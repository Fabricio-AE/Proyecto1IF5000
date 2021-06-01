/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.ClientConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Fabricio
 */
public class VentanaRegistrar extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel jlblUsuario, jlblContrasenia;
    private JTextField jtfUsuario, jtfContrasenia;
    private JButton jbtnRegistrar;

    public VentanaRegistrar() throws HeadlessException {
        this.setSize(new Dimension(300, 300));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.init();
        this.setVisible(true);
    }

    public void init() {
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setSize(300, 300);
        this.panel.setVisible(true);

        this.jlblUsuario = new JLabel("Nombre");
        this.jlblUsuario.setBounds(this.panel.getWidth() / 2 - 30,
                30, 60, 30);
        this.panel.add(this.jlblUsuario);

        this.jtfUsuario = new JTextField();
        this.jtfUsuario.setBounds(this.panel.getWidth() / 2 - 60,
                60, 120, 30);
        this.panel.add(this.jtfUsuario);

        this.jlblContrasenia = new JLabel("Contraseña");
        this.jlblContrasenia.setBounds(this.panel.getWidth() / 2 - 40,
                130, 80, 30);
        this.panel.add(this.jlblContrasenia);

        this.jtfContrasenia = new JTextField();
        this.jtfContrasenia.setBounds(this.panel.getWidth() / 2 - 60,
                160, 120, 30);
        this.panel.add(this.jtfContrasenia);

        this.jbtnRegistrar = new JButton("Registrar");
        this.jbtnRegistrar.setBounds(this.panel.getWidth() / 2 - 50,
                230, 100, 30);
        this.jbtnRegistrar.addActionListener(this);
        this.panel.add(this.jbtnRegistrar);

        this.add(this.panel);

    }//init

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == this.jbtnRegistrar
                    && !this.jtfUsuario.getText().isEmpty()
                    && !this.jtfContrasenia.getText().isEmpty()) {

                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.registrarse(this.jtfUsuario.getText(),
                        this.jtfContrasenia.getText());
            }
        } catch (IOException ex) {
            Logger.getLogger(VentanaRegistrar.class.getName()).log(Level.SEVERE, null, ex);
        }//try-catch
    }//actionPerformed

}//end class
