package Domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Imagen {

    private Image imagen;
    private boolean isSelected;
    private ArrayList<ParteImagen> partes;

    public Imagen(Image image) throws IOException {
        this.imagen = image;
        this.isSelected = false;
        this.partes = new ArrayList<>();
    }//constructor

    public void dispersarPartes() {
        for (int i = 0; i < this.partes.size(); i++) {
            int j = (int) (Math.random() * 23);
            int posXSelected = this.partes.get(j).getPosX();
            int posYSelected = this.partes.get(j).getPosY();

            this.partes.get(j).setPosX(this.partes.get(i).getPosX());
            this.partes.get(j).setPosY(this.partes.get(i).getPosY());

            this.partes.get(i).setPosX(posXSelected);
            this.partes.get(i).setPosY(posYSelected);
        }//for i

    }//dispersarPartes

    public void draw(Graphics g) {
        for (int i = 0; i < this.partes.size(); i++) {
            this.partes.get(i).draw(g);
        }//for i
    }//draw

    public void mousePressed(MouseEvent ev) {
        for (int i = 0; i < this.partes.size(); i++) {
            if ((this.partes.get(i).getPosX() <= ev.getX()
                    && this.partes.get(i).getMaxX() >= ev.getX())
                    && (this.partes.get(i).getPosY() <= ev.getY()
                    && this.partes.get(i).getMaxY() >= ev.getY())) {
                if (!this.isSelected) {
                    this.partes.get(i).setPressedId(1);
                    this.isSelected = true;
                } else if (this.isActive() != -1) {
                    int j = this.isActive();

                    int posXSelected = this.partes.get(j).getPosX();
                    int posYSelected = this.partes.get(j).getPosY();

                    this.partes.get(j).setPosX(this.partes.get(i).getPosX());
                    this.partes.get(j).setPosY(this.partes.get(i).getPosY());

                    this.partes.get(i).setPosX(posXSelected);
                    this.partes.get(i).setPosY(posYSelected);

                    this.partes.get(j).setPressedId(0);
                    this.isSelected = false;
                }//else if
            }//if caja de colisiones
        }//for i
    }//mouseDragged

    public int isActive() {
        for (int i = 0; i < this.partes.size(); i++) {
            if (this.partes.get(i).getPressedId() == 1) {
                return i;
            }
        }

        return -1;
    }//isActive

    /*setters and getters*/
    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public ArrayList<ParteImagen> getPartes() {
        return partes;
    }

    public void setPartes(ArrayList<ParteImagen> partes) {
        this.partes = partes;
    }

}//end class
