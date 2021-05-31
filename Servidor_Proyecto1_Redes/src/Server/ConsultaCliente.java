package Server;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jdom.Element;
import org.jdom.JDOMException;

import Data.ClienteData;
import Domain.Cliente;
import Domain.Imagen;
import Domain.ParteImagen;
import Domain.Usuario;
import Utility.Conversiones;

public class ConsultaCliente extends Thread {

	private Socket socket;
	private PrintStream send;
	private BufferedReader receive;
	private boolean conectado;
	private String accion;
	private Cliente cliente;

	public ConsultaCliente(Socket socket) throws IOException {
		super("Hilo clientes");
		this.socket = socket;
		this.send = new PrintStream(this.socket.getOutputStream());
		this.receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.conectado = true;
		//this.cliente = Cliente.getInstance();
		//this.cliente.setImagen(new Imagen());
	}// constructor

	public void run() {

		try {
			while (this.conectado) {
				this.escuchando();
			} // do while

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}

	}// run

	public void escuchando() throws IOException, JDOMException {
		System.out.println("Escuchando...");
		this.accion = this.receive.readLine();
		this.protocolo();
	}// escuchando

	public void enviar(String element) {
		this.send.println(element);
	}// enviar

	public void protocolo() throws JDOMException, IOException {
		// System.out.println(this.accion);
		Element accion = Conversiones.stringToXML(this.accion);
		System.out.println("Opcion recibida: " + accion.getChild("accion").getValue());
		switch (accion.getChild("accion").getValue()) {
		case "Cerrar":
			this.send.println(Conversiones.anadirAccion(new Element("accion"), "Cerrar"));
			this.conectado = false;
			this.socket.close();
			break;

		case "iniciar sesion":
			this.cliente = new Cliente("1", "Fabricio", "123");
			break;

		case "nueva imagen":
			this.cliente.getImagen().getPartes().clear();
			break;

		case "parte de imagen":
			this.insertarParteImagen(accion);
			break;

		case "verificar llegada":

			break;

		case "ver imagenes":
			this.verImagenes();
			break;
			
		case "ver imagen":
			this.verImagen(accion);
			
			break;
		default:
			break;
		}// switch
	}// protocolo

	public void insertarParteImagen(Element accion) throws IOException {
		int id = Integer.parseInt(accion.getChild("id").getValue());
		int x = Integer.parseInt(accion.getChild("posX").getValue());
		int y = Integer.parseInt(accion.getChild("posY").getValue());
		Image img = this.cargarImagen(accion.getChild("encodedImage").getValue());

		this.cliente.agregarParteImagen(new ParteImagen(id, x, y, img));
	}// insertarParteImagen

	public void registrarUsuario(Element element) throws JDOMException, IOException {
		/*
		 * Usuario usuario = Conversiones.xmlToUsuario(element); UsuarioBusiness
		 * usuarioBusiness = new UsuarioBusiness(); Element respuesta = new
		 * Element("accion");
		 * 
		 * if (usuarioBusiness.buscarUsuario(usuario.getNombre()) == null) {
		 * System.out.println(usuario.toString());
		 * usuarioBusiness.registrarUsuario(usuario);
		 * 
		 * this.enviar(Conversiones.anadirAccion(respuesta, "Registrado"));
		 * 
		 * } else { System.out.println("No registrado");
		 * this.enviar(Conversiones.anadirAccion(respuesta, "NoRegistrado")); }
		 */
	}// registrarUsuario

	public Image cargarImagen(String encodedImage) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(encodedImage);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
		Image tmp = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(),
				Image.SCALE_SMOOTH);
		return tmp;
	}// cargarImagenPersonaje
	
	public void verImagenes() {
		ArrayList<String> temp = this.cliente.verImagenes();
		Element eImagenes = new Element("imagenes");
		for (int i = 0; i < temp.size(); i++) {
			Element eImagen = new Element("imagen");
			eImagen.addContent(temp.get(i));
			eImagenes.addContent(eImagen);
		}//for i
		
		this.enviar(Conversiones.anadirAccion(eImagenes, "listar imagenes"));
		
	}//verImagenes
	
	public void verImagen(Element element1) throws IOException {
		Element element = new Element("Imagen");
		ClienteData clienteData = new ClienteData();
		
		System.out.println(element1.getChild("nombre").getValue());

        BufferedImage img = clienteData.verImagen(this.cliente.getId(), element1.getChild("nombre").getValue());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        baos.flush();
        String encodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();

        Element eEncodedImage = new Element("encodedImage");
        eEncodedImage.addContent(encodedImage);
        element.addContent(eEncodedImage);
        
        Conversiones.anadirAccion(element, "ver imagen");

        this.send.println(Conversiones.xmlToString(element));
	}//verImagen

	public boolean comprobarUsuarioYaActivo(Usuario usrTemp) {
		/*
		 * Administrador admin = Administrador.getInstance(); for (int i = 0; i <
		 * admin.getUsuariosActivos().size(); i++) { if
		 * (admin.getUsuariosActivos().get(i).getNombre().equals(usrTemp.getNombre())) {
		 * return true; } } // for i
		 */
		return false;
	}// comprobarInicioDeSesion

}// end class
