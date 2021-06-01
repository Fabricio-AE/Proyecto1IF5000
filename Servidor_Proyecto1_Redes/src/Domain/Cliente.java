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

import Data.ClienteData;
import Utility.OrdenarArray;

public class Cliente {

	private Imagen imagen;
	private String id, nombre, contrasenia;
	private ArrayList<String> archivos;

	public Cliente(String id, String nombre, String contrasenia) throws IOException {
		this.id = id;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.imagen = new Imagen();
	}// constructor

	public void agregarParteImagen(ParteImagen img, int opc) throws IOException {
		this.imagen.getPartes().add(img);

		if (this.imagen.getPartes().size() == 25) {// cambiar el 25 por una variable en Variables
			int width = 350, height = 350;
			int partes = 5;// recibir por parametro a la hora de enviar las partes
			int cont = 0;

			ClienteData clienteData = new ClienteData();
			if (opc == 1) {
				clienteData.guardarImagenDesordenada(this.id, this.imagen, width, height, partes);
				Collections.sort(this.imagen.getPartes(), new OrdenarArray());
			}

			for (int i = 0; i < partes; i++) {
				for (int j = 0; j < partes; j++) {
					int posX = j * (width / partes), posY = i * (height / partes);
					this.imagen.getPartes().get(cont).setPosX(posX + 20);
					this.imagen.getPartes().get(cont).setPosY(posY + 90);
					cont++;
				} // for j
			} // for i
			
			if (opc == 1)
				clienteData.guardarImagen(this.id, this.imagen, width, height, partes);
		} // if
	}// agregarParteImagen

	public ArrayList<String> verImagenes() {
		ClienteData clienteData = new ClienteData();
		return clienteData.verImagenes(this.id);
	}// verImagenes

	/* setters and getters */
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public ArrayList<String> getArchivos() {
		return archivos;
	}

	public void setArchivos(ArrayList<String> archivos) {
		this.archivos = archivos;
	}

}// end class
