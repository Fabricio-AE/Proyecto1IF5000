package Domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Graphics2D;

public class Imagen{

    private boolean isSelected;
    private Image imagen;
    private ArrayList<ParteImagen> partes;

    public Imagen(Image image) throws IOException {
        this.imagen = image;
        this.isSelected = false;
        this.partes = new ArrayList<>();
    }//constructor
    
    public Imagen() throws IOException {
        /*this.imagen = null;*/
        this.isSelected = false;
        this.partes = new ArrayList<>();
    }//constructor
    
    public BufferedImage convertirImagen(Image img) {
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bufferedImage;
    }//convertirImagen
    
    public void asignarImagen(Image img) throws IOException {
        int width = 350, height = 350;
        int partes = 5;
        Component comp = new Component() {
        };
        
        BufferedImage bi = convertirImagen(img);
        
        Image tmp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.imagen = tmp;

        int idImagen = 0;
        for (int i = 0; i < partes; i++) {
            for (int j = 0; j < partes; j++) {
                int posX = j * (width / partes), posY = i * (height / partes);

                Image imagePart = comp.createImage(new FilteredImageSource(tmp.getSource(),
                        new CropImageFilter(posX, posY, width / partes, height / partes)));

                this.partes.add(
                        new ParteImagen(idImagen++, posX+20, posY+90, imagePart));
            }//for j
        }//for i

    }//asignarImagen

    public void dispersarPartes() {
        for (int i = 0; i < this.partes.size(); i++) {
            int j = (int) (Math.random() * this.partes.size());
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
            }//if
        }//for

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
