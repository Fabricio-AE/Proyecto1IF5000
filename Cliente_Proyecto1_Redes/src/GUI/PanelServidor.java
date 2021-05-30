/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Fabricio
 */
public class PanelServidor extends JPanel implements ActionListener{

    private Border border;
    private JButton jbtnBuscarImagen, jbtnObtenerImagen;

    public PanelServidor(String titulo) {
        super();
        this.setBounds(395, 100, 390, 460);
        this.setLayout(null);
        this.border = new TitledBorder(titulo);
        this.setBorder(this.border);
        this.init();
        this.setVisible(true);
        this.init();
    }//constructor

    private void init() {
        this.jbtnBuscarImagen = new JButton("Buscar imagen");
        this.jbtnBuscarImagen.setBounds(this.getWidth()/2-60, 10, 120, 30);
        this.jbtnBuscarImagen.addActionListener(this);
        this.add(this.jbtnBuscarImagen);

        this.jbtnObtenerImagen = new JButton("Obtener");
        this.jbtnObtenerImagen.setBounds(this.getWidth()/2-60, 425, 120, 30);
        this.jbtnObtenerImagen.addActionListener(this);
        this.add(this.jbtnObtenerImagen);

    }//init

    @Override
    public void actionPerformed(ActionEvent ae) {
        
    }

}//end class
