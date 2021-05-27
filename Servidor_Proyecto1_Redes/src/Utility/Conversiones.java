package Utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import Domain.Usuario;

public class Conversiones {

	public static String xmlToString(Element element) {
		XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat().getCompactFormat());
		String xmlStringElement = outputter.outputString(element);
		xmlStringElement = xmlStringElement.replace("\n", "");
		return xmlStringElement;
	}

	public static Element stringToXML(String stringElement) throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		StringReader stringReader = new StringReader(stringElement);
		Document doc = saxBuilder.build(stringReader);
		return doc.getRootElement();
	} // stringToXML

	public static Element usuarioToXML(Usuario usuario) {
		/*Element eUsuario = new Element("Usuario");
		eUsuario.setAttribute("nombre", usuario.getNombre());

		Element eContrasenia = new Element("contrasenia");
		eContrasenia.addContent(usuario.getContrasenia());

		Element ePartidasGanadas = new Element("partidasganadas");
		ePartidasGanadas.addContent(String.valueOf(usuario.getPartidasGanadas()));

		Element eMonedas = new Element("monedas");
		eMonedas.addContent(String.valueOf(usuario.getMonedas()));

		eUsuario.addContent(eContrasenia);
		eUsuario.addContent(ePartidasGanadas);
		eUsuario.addContent(eMonedas);
		
		*/return /*eUsuario*/null;
	}// usuarioToXML
	
	

	public static Usuario xmlToUsuario(Element element) throws NumberFormatException, IOException {
		/*Usuario usuario = new Usuario(element.getAttributeValue("nombre"), element.getChild("contrasenia").getValue(),
				Integer.parseInt(element.getChild("partidasganadas").getValue()),
				Integer.parseInt(element.getChild("monedas").getValue()));
		*/return /*usuario*/ null;
	}// XMLToUsuario

	public static Usuario stringToUsuario(String stringUsuario) throws JDOMException, IOException {
		return xmlToUsuario(stringToXML(stringUsuario));
	}// StringToUsuario

	public static String usuarioToString(Usuario usuario) {
		return xmlToString(usuarioToXML(usuario));
	}// stringUsuario

	public static String anadirAccion(Element element, String accion) {
		Element eAccion = new Element("accion");
		eAccion.addContent(accion);

		element.addContent(eAccion);

		return xmlToString(element);
	}// anadirAccion

	public static String enviarPersonaje(Usuario usuario) throws IOException {
		/*BufferedImage img = usuario.getJugador().getImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		baos.flush();
		String encodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
		baos.close(); // should be inside a finally block
		*/
		return /*encodedImage*/ "";
	}// enviarPersonaje

} // fin clase
