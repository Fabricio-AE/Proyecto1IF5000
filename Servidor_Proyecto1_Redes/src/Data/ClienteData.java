package Data;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Domain.Imagen;
import Utility.Variables;

public class ClienteData {
	private String sqlConn;

	public ClienteData() {
		this.sqlConn = Variables.SERVIDORSQL
				+ Variables.BDSERVIDORSQL + Variables.USUARIOSERVIDORSQL 
				+ Variables.PASSWORDSERVIDORSQL
				+ "loginTimeout=30;";
	}

	public String registrarUsuario(String nombre, String contrasenia) {
		ResultSet resultSet = null;
		String query = "EXECUTE sp_INSERT_CUENTA ?, ?";

		try {	
			Connection connection = DriverManager.getConnection(this.sqlConn);
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, nombre);
			statement.setString(2, contrasenia);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Error en el servidor";
	}// registrarUsuario

	public String[] iniciarSesion(String nombre, String contrasenia) {
		ResultSet resultSet = null;
		String query = "EXECUTE sp_INICIAR_SESION ?, ?";
		String [] str = new String[2];

		try {
			Connection connection = DriverManager.getConnection(this.sqlConn);
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, nombre);
			statement.setString(2, contrasenia);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if(resultSet.getString(1).equals("SESION INICIADA CON EXITO")) {
					str[0] = resultSet.getString(1);
					str[1] = resultSet.getString(2);
					return str;
				}
				str[0] = resultSet.getString(1);
				
				return str;
			}
			str[0] = "Error en el servidor";
		} catch (SQLException e) {
			e.printStackTrace();
		}//try-catch
		
		return str;
	}// iniciarSesion

	public void guardarImagen(String id, Imagen imagen, int width, int height, int partes) throws IOException {
		File directorio = new File("../archivos/" + id + "/imagenes");
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
				new File("../archivos/" + id + "/imagenes/imagen" + (numero.length + 1) + ".png"));
	}// guardarImagen

	public void guardarImagenDesordenada(String id, Imagen imagen, int width, int height, int partes)
			throws IOException {
		File directorio = new File("../archivos/" + id + "/desordenadas");
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
				new File("../archivos/" + id + "/desordenadas/imagen" + (numero.length + 1) + ".png"));
	}// guardarImagen

	public ArrayList<String> verImagenes(String id) {
		File directorio = new File("../archivos/" + id + "/imagenes");
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		File[] lista = directorio.listFiles();
		ArrayList<String> directorios = new ArrayList<>();

		for (int i = 0; i < lista.length; i++) {
			directorios.add(lista[i].getName());
		} // for i

		return directorios;
	}// verImagenes

	public BufferedImage verImagen(String id, String nombre) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File("../archivos/" + id + "/imagenes/" + nombre));
		return bufferedImage;
	}// verImagen

}// end class
