package Domain;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.ArrayList;

import Utility.Conversiones;

public class Imagen {

	private boolean isSelected;
	private ArrayList<ParteImagen> partes;

	public Imagen() throws IOException {
		this.isSelected = false;
		this.partes = new ArrayList<>();
	}// constructor

	public void asignarImagen(Image img) throws IOException {
		int width = 350, height = 350;
		int partes = 5;
		Component comp = new Component() {
		};

		BufferedImage bi = Conversiones.convertirImagen(img);

		Image tmp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		int idImagen = 0;
		for (int i = 0; i < partes; i++) {
			for (int j = 0; j < partes; j++) {
				int posX = j * (width / partes), posY = i * (height / partes);

				Image imagePart = comp.createImage(new FilteredImageSource(tmp.getSource(),
						new CropImageFilter(posX, posY, width / partes, height / partes)));

				this.partes.add(new ParteImagen(idImagen++, posX + 20, posY + 90, imagePart));
			} // for j
		} // for i

	}// asignarImagen

	/* setters and getters */
	public ArrayList<ParteImagen> getPartes() {
		return partes;
	}

	public void setPartes(ArrayList<ParteImagen> partes) {
		this.partes = partes;
	}

}// end class