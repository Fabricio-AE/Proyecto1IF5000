/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.ClientConnection;
import Domain.Cliente;
import Utility.Conversiones;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jdom.Element;

/**
 *
 * @author Fabricio
 */
public class VentanaPrincipal extends JFrame implements WindowListener {

    private JPanel panel, panelCliente, panelServidor;

    public VentanaPrincipal() throws HeadlessException {
        super("TFTP");
        try {
            Cliente cliente = Cliente.getInstance();
            this.setSize(new Dimension(800, 700));
            this.setLayout(null);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.setResizable(false);
            this.addWindowListener(this);
            this.init();
            this.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//constructor

    private void init() {

        try {
            this.panel = new PanelPrincipal("Sesión");
            this.add(this.panel);

            this.panelCliente = new PanelCliente("Cliente");
            this.add(this.panelCliente);

            this.panelServidor = new PanelServidor("Servidor");
            this.add(this.panelServidor);
        } //init
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void windowOpened(WindowEvent we) {

    }

    @Override
    public void windowClosing(WindowEvent we) {
        String[] btns = {"Aceptar", "Cancelar"};
        int opc = JOptionPane.showOptionDialog(this, "Desea salir?", "Mensaje", 0, 0, null, btns, this);
        if (opc == JOptionPane.YES_OPTION) {
            if (!ClientConnection.isNull()) {
                try {
                    ClientConnection cliConn = ClientConnection.getInstance();
                    cliConn.enviar(Conversiones.anadirAccion(new Element("msg"), "Cerrar"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }//try-catch
            }
            System.exit(0);
        }//if
    }

    @Override
    public void windowClosed(WindowEvent we) {

    }

    @Override
    public void windowIconified(WindowEvent we) {

    }

    @Override
    public void windowDeiconified(WindowEvent we) {

    }

    @Override
    public void windowActivated(WindowEvent we) {

    }

    @Override
    public void windowDeactivated(WindowEvent we) {

    }

}//end class
