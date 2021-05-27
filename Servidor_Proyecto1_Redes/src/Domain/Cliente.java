package Domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Utility.OrdenarArray;

public class Cliente {

	private static Cliente instance = null;
    private Imagen imagen;
    private String id, nombre, contrasenia;
    private ArrayList<String> archivos;

    private Cliente() {
    }//constructor

    public static Cliente getInstance() {
        if (instance == null) {
            instance = new Cliente();
            return instance;
        } else {
            return instance;
        }//if-else
    }//getInstance
    
    public void draw(Graphics g) {
        g.drawRect(19, 59, 351, 351);
        if (this.imagen != null) {
            if (!this.imagen.getPartes().isEmpty()) {
                this.imagen.draw(g);
            }//if
        }//if
    }//draw
    
    public void agregarParteImagen(ParteImagen img) {
    	this.imagen.getPartes().add(img);
    	if(this.imagen.getPartes().size() == 25) {
    		Collections.sort(this.imagen.getPartes(), new OrdenarArray());
    		int width = 350, height = 350;
    		int partes = 5;
    		int cont=0;
    		for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					int posX = j * (width / partes), posY = i * (height / partes);
					this.imagen.getPartes().get(cont).setPosX(posX+20);
					this.imagen.getPartes().get(cont).setPosY(posY+60);
					cont++;
				}
			}
    	}//if
    }//agregarParteImagen
    
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
