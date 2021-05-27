package Domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class ParteImagen {

    private int id, posX, posY, maxX, maxY, pressedId;
    private Image imagen;

    public ParteImagen(int id, int posX, int posY, Image imagen) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.maxX = posX+imagen.getWidth(null);
        this.maxY = posY+imagen.getHeight(null);
        this.pressedId = 0;
        this.imagen = imagen;
    }//constructor

    public void draw(Graphics g) {
        g.drawImage(this.imagen, this.posX, this.posY, null);
        if (this.pressedId == 1) g.setColor(Color.GREEN);
        else g.setColor(Color.BLACK);
        g.drawRect(this.posX, this.posY,
                this.imagen.getWidth(null)-1, this.imagen.getHeight(null)-1);
    }//draw

    /*setters and getters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        this.maxX = this.posX+imagen.getWidth(null);
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        this.maxY = this.posY+imagen.getHeight(null);
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getPressedId() {
        return pressedId;
    }

    public void setPressedId(int pressedId) {
        this.pressedId = pressedId;
    }    

}//end class
