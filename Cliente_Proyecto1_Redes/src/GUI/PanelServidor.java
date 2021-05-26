/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Fabricio
 */
public class PanelServidor extends JPanel{
    private Border border;
    
    public PanelServidor(String titulo) {
    	super();
    	this.setBounds(395, 100, 390, 460);
        this.setLayout(null);
        this.border = new TitledBorder(titulo);
        this.setBorder(this.border);
        this.init();
        this.setVisible(true);
        
    }//constructor
    
    private void init(){
        
    }//init
    
}//end class
