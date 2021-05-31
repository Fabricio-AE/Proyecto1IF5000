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
import Utility.Conversiones;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdom.Element;

/**
 *
 * @author Fabricio
 */
public class PanelCliente extends JPanel implements ActionListener, MouseListener {

    private Border border;
    private JButton jbtnBuscarImagen, jbtnEnviarImagen, jbtnImagenesRecibidas;
    private JFileChooser jfcChooser;
    private Cliente cliente;

    public PanelCliente(String titulo) throws IOException {
        super();
        this.cliente = Cliente.getInstance();
        this.setBounds(0, 100, 390, 560);
        this.setLayout(null);
        this.border = new TitledBorder(titulo);
        this.setBorder(this.border);
        this.init();
        this.setVisible(true);
        this.addMouseListener(this);
    }//constructor

    private void init() {
        this.jbtnBuscarImagen = new JButton("Buscar imagen");
        this.jbtnBuscarImagen.setBounds(this.getWidth()/2-60, 10, 120, 30);
        this.jbtnBuscarImagen.addActionListener(this);
        this.add(this.jbtnBuscarImagen);
        
        this.jbtnEnviarImagen = new JButton("Enviar");
        this.jbtnEnviarImagen.setBounds(this.getWidth()/2-125, 500, 120, 30);
        this.jbtnEnviarImagen.addActionListener(this);
        this.add(this.jbtnEnviarImagen);
        
        this.jbtnImagenesRecibidas = new JButton("Ver recibidas");
        this.jbtnImagenesRecibidas.setBounds(this.getWidth()/2+5, 500, 120, 30);
        this.jbtnImagenesRecibidas.addActionListener(this);
        this.add(this.jbtnImagenesRecibidas);
        
    }//init

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.cliente.draw(g);
        this.repaint();
    }//paintComponent

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == this.jbtnBuscarImagen) {
                this.jfcChooser = new JFileChooser();
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagen", "png", "jpg", "bmp");
                this.jfcChooser.setFileFilter(filtro);
                int opcion = this.jfcChooser.showOpenDialog(this);
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    String directorioImagen = this.jfcChooser.getSelectedFile().getAbsolutePath();
                    Imagen img = new Imagen();
                    img.asignarImagen(ImageIO.read(new File(directorioImagen)));
                    this.cliente.setImagen(img);
                    this.repaint();
                    this.cliente.getImagen().dispersarPartes();
                } //if
                
            }else if(ae.getSource() == this.jbtnEnviarImagen 
                    && !ClientConnection.isNull()){
                System.out.println("Estoy enviando...");
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.enviar(Conversiones.anadirAccion(new Element("msg"), "nueva imagen"));
                clientConnection.enviarImagen(this.cliente.getImagen().getPartes());
                
            }else if(ae.getSource() == this.jbtnImagenesRecibidas){
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.enviar(Conversiones.anadirAccion(new Element("msg"), "ver imagenes"));
            }
                
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelCliente.class.getName()).log(Level.SEVERE, null, ex);
        }//try-catch   
    }//actionPerformed

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.cliente.mousePressed(me);
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
