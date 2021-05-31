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
		File directorio = new File("../archivos/" + id + "/imagenes");
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		BufferedImage biResultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = biResultado.getGraphics();
		int cont = 0;
		for (int i = 0; i < partes; i++) {
			for (int j = 0; j < partes; j++) {
				int posX = j * (width / partes), posY = i * (height / partes);
				g.drawImage(imagen.getPartes().get(cont++).getImagen(), posX, posY, null);
			}//for j
		}//for i
		String[] numero = directorio.list();
		ImageIO.write(biResultado, "PNG", new File("../archivos/" + id + "/imagenes/imagen"+(numero.length+1)+".png"));
	}// guardarImagen
	
	public ArrayList<String> verImagenes(String id) {
		File directorio = new File("../archivos/" + id + "/imagenes");
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		File [] lista = directorio.listFiles();
		ArrayList<String> directorios = new ArrayList<>();
		
		for (int i = 0; i < lista.length; i++) {
			directorios.add(lista[i].getName());
		}//for i
		
		return directorios;
	}//verImagenes
	
	public BufferedImage verImagen(String id, String nombre) throws IOException {		
		BufferedImage bufferedImage = ImageIO.read(new File("../archivos/" + id + "/imagenes/"+nombre));
		return bufferedImage;
	}//verImagen
	
}//end class
