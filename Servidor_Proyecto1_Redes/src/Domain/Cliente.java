package Domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Utility.OrdenarArray;

public class Cliente {

	private Imagen imagen;
	private String id, nombre, contrasenia;
	private ArrayList<String> archivos;

	public Cliente(String id, String nombre, String contrasenia) throws IOException {
		this.id = id;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.imagen = new Imagen(null);
	}// constructor

	public void draw(Graphics g) {
		g.drawRect(19, 59, 351, 351);
		if (this.imagen != null) {
			if (!this.imagen.getPartes().isEmpty()) {
				this.imagen.draw(g);
			} // if
		} // if
	}// draw

	public void agregarParteImagen(ParteImagen img) throws IOException {
		this.imagen.getPartes().add(img);
		if (this.imagen.getPartes().size() == 25) {// cambiar el 25 por una variable en Variables
			Collections.sort(this.imagen.getPartes(), new OrdenarArray());
			int width = 350, height = 350;
			int partes = 5;//recibir por parametro a la hora de enviar las partes
			int cont = 0;
			for (int i = 0; i < partes; i++) {
				for (int j = 0; j < partes; j++) {
					int posX = j * (width / partes), posY = i * (height / partes);
					this.imagen.getPartes().get(cont).setPosX(posX + 20);
					this.imagen.getPartes().get(cont).setPosY(posY + 60);
					cont++;
				} // for j
			} // for i
			this.guardarImagen(width, height, 5);
		} // if
	}// agregarParteImagen

	public void guardarImagen(int width, int height, int partes) throws IOException {
		File directorio = new File("../archivos/" + this.id + "/armadas");
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		BufferedImage biResultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = biResultado.getGraphics();
		int cont = 0;
		for (int i = 0; i < partes; i++) {
			for (int j = 0; j < partes; j++) {
				int posX = j * (width / partes), posY = i * (height / partes);
				g.drawImage(this.imagen.getPartes().get(cont++).getImagen(), posX, posY, null);
			}//for j
		}//for i
		String[] numero = directorio.list();
		ImageIO.write(biResultado, "PNG", new File("../archivos/" + this.id + "/armadas/"+(numero.length+1)+".png"));
	}// guardarImagen

	public void mousePressed(MouseEvent ev) {
		if (this.imagen != null) {
			this.imagen.mousePressed(ev);
		} // if
	}// mouseDragged

	/* setters and getters */
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

}// end class
