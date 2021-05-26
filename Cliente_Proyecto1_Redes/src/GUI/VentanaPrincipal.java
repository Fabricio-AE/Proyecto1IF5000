/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Fabricio
 */
public class VentanaPrincipal extends JFrame {
    private JPanel panel, panelCliente, panelServidor;
    
    public VentanaPrincipal() throws HeadlessException {
        super("TFTP");
        this.setSize(new Dimension(800, 600));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.init();        
        this.setVisible(true);
    }//constructor
    
    private void init(){
        
        this.panel = new PanelPrincipal("Sesión");
        this.add(this.panel);
        
        
        this.panelCliente = new PanelCliente("Cliente");
        this.add(this.panelCliente);
        
        this.panelServidor = new PanelServidor("Servidor");
        this.add(this.panelServidor);
        
        
    }//init
    
    
}//end class
