/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Fabricio
 */
public class Servidor {

    private static Servidor instance = null;
    private ArrayList<String> imagenes;
    private Imagen imagen;

    private Servidor() {
    }//constructor

    public static Servidor getInstance() {
        if (instance == null) {
            instance = new Servidor();
        }
        return instance;
    }//getInstance

    public void draw(Graphics g) {
        g.drawRect(19, 89, 351, 351);
        if (this.imagen != null) {
            if (!this.imagen.getPartes().isEmpty()) {
                this.imagen.draw(g);
            }//if
        }//if
    }//draw

    public void mousePressed(MouseEvent ev) {
        if (this.imagen != null) {
            this.imagen.mousePressed(ev);
        }//if        
    }//mouseDragged

    /*setters and getters*/
    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }
    
    

}//end class
