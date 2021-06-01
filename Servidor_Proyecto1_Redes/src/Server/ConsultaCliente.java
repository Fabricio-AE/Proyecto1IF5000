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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// run

	public void escuchando() throws IOException, JDOMException, InterruptedException {
		System.out.println("Escuchando...");
		this.accion = this.receive.readLine();
		this.protocolo();
	}// escuchando

	public void enviar(String element) {
		this.send.println(element);
	}// enviar

	public void protocolo() throws JDOMException, IOException, InterruptedException {
		// System.out.println(this.accion);
		Element accion = Conversiones.stringToXML(this.accion);
		System.out.println("Opcion recibida: " + accion.getChild("accion").getValue());
		switch (accion.getChild("accion").getValue()) {
		case "Cerrar":
			this.send.println(Conversiones.anadirAccion(new Element("accion"), "Cerrar"));
			this.conectado = false;
			this.socket.close();
			break;
			
		case "registrar usuario":
			this.registrarUsuario(accion);
			break;
			
		case "iniciar sesion":
			this.iniciarSesion(accion);
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
			this.cliente.getImagen().getPartes().clear();
			this.verImagen(accion);
			this.cliente.getImagen().getPartes().clear();
			
			break;
			
		case "obtener imagen":
			this.enviarImagen();
			break;
		default:
			break;
		}// switch
	}// protocolo
	
	public void registrarUsuario(Element element) {
		ClienteData clienteData = new ClienteData();
		String resultado = clienteData.registrarUsuario(element.getChild("nombre").getValue()
				, element.getChild("contrasenia").getValue());
		Element msg = new Element("msg");
		
		Element respuesta = new Element("respuesta");
		respuesta.addContent(resultado);
		
		msg.addContent(respuesta);
		
		this.enviar(Conversiones.anadirAccion(msg, "registrarse"));
		
	}//registrarUsuario
	
	public void iniciarSesion(Element element) throws IOException {
		ClienteData clienteData = new ClienteData();
		String [] resultado = clienteData.iniciarSesion(element.getChild("nombre").getValue()
				, element.getChild("contrasenia").getValue());
		System.out.println(element.getChild("nombre").getValue());
		
		if (resultado[0].equals("SESION INICIADA CON EXITO")) {
			this.cliente = new Cliente(resultado[1]
					, element.getChild("nombre").getValue()
					, element.getChild("contrasenia").getValue());
		}
		
		Element msg = new Element("msg");
		
		Element respuesta = new Element("respuesta");
		respuesta.addContent(resultado[0]);
		
		Element nombre = new Element("nombre");
		nombre.addContent(element.getChild("nombre").getValue()+resultado[1]);
		
		msg.addContent(respuesta);
		msg.addContent(nombre);
		
		this.enviar(Conversiones.anadirAccion(msg, "iniciar sesion"));
	}

	public void insertarParteImagen(Element element) throws IOException {
		int id = Integer.parseInt(element.getChild("id").getValue());
		int x = Integer.parseInt(element.getChild("posX").getValue());
		int y = Integer.parseInt(element.getChild("posY").getValue());
		int opc = Integer.parseInt(element.getChild("opc").getValue());
		Image img = this.cargarImagen(element.getChild("encodedImage").getValue());
		
		this.cliente.agregarParteImagen(new ParteImagen(id, x, y, img), opc);
		System.out.println("opcccccccccccccc: "+this.cliente.getImagen().getPartes().size());
	}// insertarParteImagen
	
	public void obtenerImagen(Element element) throws IOException {
		int id = Integer.parseInt(element.getChild("id").getValue());
		int x = Integer.parseInt(element.getChild("posX").getValue());
		int y = Integer.parseInt(element.getChild("posY").getValue());
		Image img = this.cargarImagen(element.getChild("encodedImage").getValue());

		this.cliente.agregarParteImagen(new ParteImagen(id, x, y, img), 2);
	}//obtenerImagen

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

        BufferedImage img = clienteData.verImagen(this.cliente.getId(), element1.getChild("nombre").getValue());
        this.cliente.setImagen(new Imagen());
        this.cliente.getImagen().asignarImagen(Conversiones.convertirImagen(img));
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

	public void enviarImagen() throws IOException, InterruptedException {
		
		this.send.println(Conversiones.anadirAccion(new Element("msg"), "borrar partes"));

        for (int i = 0; i < this.cliente.getImagen().getPartes().size(); i++) {
            Element element = new Element("Image");

            BufferedImage img = Conversiones.convertirImagen(this.cliente.getImagen().getPartes().get(i).getImagen());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            baos.flush();
            String encodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
            baos.close();
            Conversiones.anadirAccion(element, "parte de imagen");

            Element eEncodedImage = new Element("encodedImage");
            eEncodedImage.addContent(encodedImage);
            element.addContent(eEncodedImage);

            Element eIdParte = new Element("id");
            eIdParte.addContent(this.cliente.getImagen().getPartes().get(i).getId() + "");
            element.addContent(eIdParte);
            

            Element ePosX = new Element("posX");
            ePosX.addContent(this.cliente.getImagen().getPartes().get(i).getPosX() + "");
            element.addContent(ePosX);

            Element ePosY = new Element("posY");
            ePosY.addContent(this.cliente.getImagen().getPartes().get(i).getPosY() + "");
            element.addContent(ePosY);

            this.send.println(Conversiones.xmlToString(element));
            Thread.sleep(100);
        }//for i

    }//enviarImagen

}// end class
