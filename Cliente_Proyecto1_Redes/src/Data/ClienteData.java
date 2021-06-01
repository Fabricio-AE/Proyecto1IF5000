package Data;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Domain.Imagen;

public class ClienteData {

    public ClienteData() {
    }

    public void guardarImagen(String id, Imagen imagen, int width, int height, int partes) throws IOException {
        File directorio = new File("../cliente/" + id + "/imagenes");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        BufferedImage biResultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = biResultado.getGraphics();
        for (int i = 0; i < imagen.getPartes().size(); i++) {
            int posX = imagen.getPartes().get(i).getPosX() - 20;
            int posY = imagen.getPartes().get(i).getPosY() - 90;
            g.drawImage(imagen.getPartes().get(i).getImagen(), posX, posY, null);
        } // for i
        String[] numero = directorio.list();
        ImageIO.write(biResultado, "PNG",
                new File("../cliente/" + id + "/imagenes/imagen" + (numero.length + 1) + ".png"));
    }// guardarImagen

    public void guardarImagenDesordenada(String id, Imagen imagen, int width, int height, int partes)
            throws IOException {
        File directorio = new File("../cliente/" + id + "/desordenadas");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        BufferedImage biResultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = biResultado.getGraphics();
        for (int i = 0; i < imagen.getPartes().size(); i++) {
            int posX = imagen.getPartes().get(i).getPosX() - 20;
            int posY = imagen.getPartes().get(i).getPosY() - 90;
            g.drawImage(imagen.getPartes().get(i).getImagen(), posX, posY, null);
        } // for i
        String[] numero = directorio.list();
        ImageIO.write(biResultado, "PNG",
                new File("../cliente/" + id + "/desordenadas/imagen" + (numero.length + 1) + ".png"));
    }// guardarImagen

}//end class
