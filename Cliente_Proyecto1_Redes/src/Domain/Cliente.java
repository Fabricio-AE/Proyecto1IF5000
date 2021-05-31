package Domain;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Cliente {

    private static Cliente instance = null;
    private Imagen imagen;

    private Cliente() {
    }//constructor

    public static Cliente getInstance() {
        if (instance == null) 
            instance = new Cliente();
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
    
    public void mousePressed(MouseEvent ev){
        if (this.imagen!=null) {
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

}//end class
