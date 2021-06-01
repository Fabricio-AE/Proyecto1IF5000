/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.ClientConnection;
import Domain.Cliente;
import Domain.Imagen;
import Domain.ParteImagen;
import Domain.Servidor;
import Utility.Conversiones;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.jdom.Element;

/**
 *
 * @author Fabricio
 */
public class PanelServidor extends JPanel implements ActionListener, MouseListener {

    private Border border;
    private JButton jbtnListarImagenes, jbtnVer, jbtnObtenerImagen;
    private JComboBox<String> jcbImagenes;
    private Servidor servidor;

    public PanelServidor(String titulo) {
        super();
        try {
            this.servidor = Servidor.getInstance();
            this.setBounds(400, 100, 390, 560);
            this.setLayout(null);
            this.border = new TitledBorder(titulo);
            this.setBorder(this.border);
            this.init();
            this.setVisible(true);
            this.addMouseListener(this);
        } catch (IOException ex) {
            Logger.getLogger(PanelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//constructor

    private void init() {
        this.jbtnListarImagenes = new JButton("Listar imagenes");
        this.jbtnListarImagenes.setBounds(this.getWidth() / 2 - 65, 10, 130, 30);
        this.jbtnListarImagenes.addActionListener(this);
        this.add(this.jbtnListarImagenes);

        this.jcbImagenes = new JComboBox<>();
        this.jcbImagenes.setBounds(this.getWidth() / 2 - 60, 50, 120, 30);
        this.add(this.jcbImagenes);

        this.jbtnVer = new JButton("Ver");
        this.jbtnVer.setBounds(this.getWidth() / 2 + 65, 50, 60, 30);
        this.jbtnVer.addActionListener(this);
        this.add(this.jbtnVer);

        this.jbtnObtenerImagen = new JButton("Obtener");
        this.jbtnObtenerImagen.setBounds(this.getWidth() / 2 - 60, 500, 120, 30);
        this.jbtnObtenerImagen.addActionListener(this);
        this.add(this.jbtnObtenerImagen);

    }//init

    private void initJComboBox() {
        this.jcbImagenes.removeAllItems();
        ArrayList<String> imagenes = this.servidor.getImagenes();
        for (int i = 0; i < imagenes.size(); i++) {
            this.jcbImagenes.addItem(imagenes.get(i));
        }//for i
    }//initJComboBox

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.servidor.draw(g);
        this.repaint();
    }//paintComponent

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            Cliente cliente = Cliente.getInstance();
            if (ae.getSource() == this.jbtnListarImagenes
                    && !cliente.getNombre().equals("-1")) {

                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.enviar(Conversiones.anadirAccion(new Element("msg"), "ver imagenes"));
                Thread.sleep(500);
                this.initJComboBox();
            } else if (ae.getSource() == this.jbtnVer
                    && !cliente.getNombre().equals("-1")) {
                ClientConnection clientConnection = ClientConnection.getInstance();
                Element msg = new Element("msg");

                Element nombre = new Element("nombre");
                nombre.addContent((String) this.jcbImagenes.getSelectedItem());

                msg.addContent(nombre);
                clientConnection.enviar(Conversiones.anadirAccion(msg, "ver imagen"));
                Thread.sleep(500);
                this.repaint();
            } else if (ae.getSource() == this.jbtnObtenerImagen
                    && !cliente.getNombre().equals("-1")) {
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.enviarImagen(this.servidor.getImagen().getPartes(), 2);
                clientConnection.enviar(Conversiones.anadirAccion(new Element("msg"), "obtener imagen"));

                Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }//actionPerformed

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.servidor.mousePressed(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}//end class
