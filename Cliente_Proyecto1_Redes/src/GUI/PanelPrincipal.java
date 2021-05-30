/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.ClientConnection;
import Utility.Conversiones;
import Utility.Variables;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.jdom.Element;

/**
 *
 * @author Fabricio
 */
public class PanelPrincipal extends JPanel implements ActionListener{
    private JLabel jlblServidor ,jlblUsuario, jlblContrasenia, jlblPuerto;
    private JTextField jtfServidor ,jtfUsuario, jtfContrasenia, jtfPuerto;
    private JButton jbtnConectar, jbtnRegistrarse;
    private Container containerSesion;
    private Border border;
    
    public PanelPrincipal(String titulo) {
        super();
        this.setSize(790, 100);
        this.setLayout(null);
        this.border = new TitledBorder(titulo);
        this.setBorder(this.border);
        this.init();
        this.setVisible(true);
        
    }//constructor
    
    private void init(){
    	/*Contenedor sesion*/
    	
    	this.containerSesion = new Container();
    	this.containerSesion.setBounds(0, 0, 790, 300);
    	this.containerSesion.setBackground(new Color(1,0,0));
    	
        this.jlblServidor = new JLabel("Servidor");
        this.jlblServidor.setBounds(Variables.ESPACIO, 10, 110, 50);
        this.containerSesion.add(this.jlblServidor);
        
        this.jtfServidor = new JTextField();
        this.jtfServidor.setBounds(Variables.ESPACIO, 50, 110, 20);
        this.containerSesion.add(this.jtfServidor);
        
        this.jlblUsuario = new JLabel("Nombre de usuario");
        this.jlblUsuario.setBounds(Variables.ESPACIO*15, 10, 110, 50);
        this.containerSesion.add(this.jlblUsuario);
        
        this.jtfUsuario = new JTextField();
        this.jtfUsuario.setBounds(Variables.ESPACIO*15, 50, 110, 20);
        this.containerSesion.add(this.jtfUsuario);
        
        this.jlblContrasenia = new JLabel("Contraseña");
        this.jlblContrasenia.setBounds(Variables.ESPACIO*29, 10, 110, 50);
        this.containerSesion.add(this.jlblContrasenia);
        
        this.jtfContrasenia = new JTextField();
        this.jtfContrasenia.setBounds(Variables.ESPACIO*29, 50, 110, 20);
        this.containerSesion.add(this.jtfContrasenia);
        
        this.jlblPuerto = new JLabel("Puerto");
        this.jlblPuerto.setBounds(Variables.ESPACIO*44, 10, 110, 50);
        this.containerSesion.add(this.jlblPuerto);
        
        this.jtfPuerto = new JTextField();
        this.jtfPuerto.setBounds(Variables.ESPACIO*44, 50, 110, 20);
        this.containerSesion.add(this.jtfPuerto);
        
        this.jbtnConectar = new JButton("Conectar");
        this.jbtnConectar.setBounds(Variables.ESPACIO*56, 40, 105, 35);
        this.jbtnConectar.addActionListener(this);
        this.containerSesion.add(this.jbtnConectar);
        
        this.jbtnRegistrarse = new JButton("Registrarse");
        this.jbtnRegistrarse.setBounds(Variables.ESPACIO*68-3, 40, 105, 35);
        this.jbtnRegistrarse.addActionListener(this);
        this.containerSesion.add(this.jbtnRegistrarse);
        
        this.add(this.containerSesion);
        
        /*Contenedor*/
        
    }//init

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.jbtnConectar 
                && !this.jtfServidor.getText().isEmpty()
                /*&& !this.jtfPuerto.getText().isEmpty()*/){
            try {
                Variables.IPSERVER = this.jtfServidor.getText();
                System.out.println(Variables.IPSERVER);
                ClientConnection cli = ClientConnection.getInstance();
                cli.enviar(Conversiones.anadirAccion(new Element("coneccion"), "iniciar sesion"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }//try-catch
        }//if
    }//actionPerformed
    
}//end class
