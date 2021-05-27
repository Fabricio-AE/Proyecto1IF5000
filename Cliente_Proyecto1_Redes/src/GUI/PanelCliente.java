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
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Fabricio
 */
public class PanelCliente extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private Border border;
    private JButton jbtnBuscarImagen, jbtnEnviarImagen, jbtnImagenesRecibidas;
    private JFileChooser jfcChooser;
    private Cliente cliente;
    private ClientConnection clientConnection;

    public PanelCliente(String titulo) {
        super();
        this.cliente = Cliente.getInstance();
        this.setBounds(0, 100, 390, 460);
        this.setLayout(null);
        this.border = new TitledBorder(titulo);
        this.setBorder(this.border);
        this.init();
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }//constructor

    private void init() {
        this.jbtnBuscarImagen = new JButton("Buscar imagen");
        this.jbtnBuscarImagen.setBounds(135, 10, 120, 30);
        this.jbtnBuscarImagen.addActionListener(this);
        this.add(this.jbtnBuscarImagen);
        
        this.jbtnEnviarImagen = new JButton("Enviar");
        this.jbtnEnviarImagen.setBounds(70, 425, 120, 30);
        this.jbtnEnviarImagen.addActionListener(this);
        this.add(this.jbtnEnviarImagen);
        
        this.jbtnImagenesRecibidas = new JButton("Ver recibidas");
        this.jbtnImagenesRecibidas.setBounds(120+75, 425, 120, 30);
        this.jbtnImagenesRecibidas.addActionListener(this);
        this.add(this.jbtnImagenesRecibidas);
        
    }//init

    public void asignarImagen(BufferedImage img) throws IOException {
        int width = 350, height = 350;
        int partes = 5;
        
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.cliente.setImagen(new Imagen(tmp));

        int idImagen = 0;
        for (int i = 0; i < partes; i++) {
            for (int j = 0; j < partes; j++) {
                int posX = j * (width / partes), posY = i * (height / partes);

                Image imagePart = createImage(new FilteredImageSource(tmp.getSource(),
                        new CropImageFilter(posX, posY, width / partes, height / partes)));

                this.cliente.getImagen().getPartes().add(
                        new ParteImagen(idImagen++, posX+20, posY+60, imagePart));
            }//for j
        }//for i

    }//asignarImagen

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
                    this.asignarImagen(ImageIO.read(new File(directorioImagen)));
                    this.repaint();
                    this.cliente.getImagen().dispersarPartes();
                } //if
                
            }else if(ae.getSource() == this.jbtnEnviarImagen 
                    && !ClientConnection.isNull()){
                System.out.println("Estoy enviando...");
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.enviarImagen(this.cliente.getImagen().getPartes());
            }else if(ae.getSource() == this.jbtnImagenesRecibidas){
                
            }//else-if
                
        } catch (IOException ex) {
            ex.printStackTrace();
        }//try-catch   
    }//actionPerformed

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

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
