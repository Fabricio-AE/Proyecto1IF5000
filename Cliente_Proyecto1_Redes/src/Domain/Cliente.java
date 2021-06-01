package Domain;

import Data.ClienteData;
import Utility.OrdenarArray;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;

public class Cliente {

    private static Cliente instance = null;
    private String nombre;
    private Imagen imagen;

    private Cliente() throws IOException {
        this.imagen = new Imagen();
        this.nombre = "-1";
    }//constructor

    public static Cliente getInstance() throws IOException {
        if (instance == null) {
            instance = new Cliente();
        }
        return instance;
    }//getInstance
    
    public static boolean isNull() {
        return instance == null;
    }//isNull

    public void agregarParteImagen(ParteImagen img) throws IOException {
        this.imagen.getPartes().add(img);

        if (this.imagen.getPartes().size() == 25) {// cambiar el 25 por una variable en Variables			
            int width = 350, height = 350;
            int partes = 5;// recibir por parametro a la hora de enviar las partes
            int cont = 0;
            ClienteData clienteData = new ClienteData();
            clienteData.guardarImagenDesordenada(this.nombre, this.imagen, width, height, partes);

            Collections.sort(this.imagen.getPartes(), new OrdenarArray());

            for (int i = 0; i < partes; i++) {
                for (int j = 0; j < partes; j++) {
                    int posX = j * (width / partes), posY = i * (height / partes);
                    this.imagen.getPartes().get(cont).setPosX(posX + 20);
                    this.imagen.getPartes().get(cont).setPosY(posY + 90);
                    cont++;
                } // for j
            } // for i
            clienteData.guardarImagen(this.nombre, this.imagen, width, height, partes);

        } // if
    }// agregarParteImagen

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}//end class
