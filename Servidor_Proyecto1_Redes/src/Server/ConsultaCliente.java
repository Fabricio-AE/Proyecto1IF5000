package Server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jdom.Element;
import org.jdom.JDOMException;

import Domain.Usuario;
import Utility.Conversiones;

public class ConsultaCliente extends Thread {

    private Socket socket;
    private PrintStream send;
    private BufferedReader receive;
    private boolean conectado;
    private String accion;

    public ConsultaCliente(Socket socket) throws IOException {
        super("Hilo clientes");
        this.socket = socket;
        this.send = new PrintStream(this.socket.getOutputStream());
        this.receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.conectado = true;
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
        //System.out.println(this.accion);
        Element accion = Conversiones.stringToXML(this.accion);
        System.out.println("Opcion recibida: " + accion.getChild("accion").getValue());
        switch (accion.getChild("accion").getValue()) {
            case "Cerrar":
                this.send.println(Conversiones.anadirAccion(new Element("accion"), "Cerrar"));
                this.conectado = false;
                this.socket.close();
                break;
                
            case "parte de imagen":
                System.out.println(accion.getChild("id").getValue());
                break;
            default:
                break;
        }// switch
    }// protocolo

    public void registrarUsuario(Element element) throws JDOMException, IOException {
        /*Usuario usuario = Conversiones.xmlToUsuario(element);
        UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
        Element respuesta = new Element("accion");

        if (usuarioBusiness.buscarUsuario(usuario.getNombre()) == null) {
            System.out.println(usuario.toString());
            usuarioBusiness.registrarUsuario(usuario);

            this.enviar(Conversiones.anadirAccion(respuesta, "Registrado"));

        } else {
            System.out.println("No registrado");
            this.enviar(Conversiones.anadirAccion(respuesta, "NoRegistrado"));
        }
*/
    }// registrarUsuario

    public BufferedImage cargarImagenPersonaje(String encodedImage) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(encodedImage);
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }// cargarImagenPersonaje

    public void iniciarSesion(Element eUsuario) throws FileNotFoundException, IOException, JDOMException {
        /*UsuarioBusiness usuarioBusiness = new UsuarioBusiness();

        Usuario usrTemp = usuarioBusiness.buscarUsuario(eUsuario.getAttributeValue("nombre"));

        if (usrTemp != null) {
            if (usrTemp.getContrasenia().equals(eUsuario.getChild("contrasenia").getValue())) {
                if (!this.comprobarUsuarioYaActivo(usrTemp)) {
                    Element element = Conversiones.usuarioToXML(usrTemp);
                    this.enviar(Conversiones.anadirAccion(element, "Logueado"));
                    Element accion = Conversiones.stringToXML(this.receive.readLine());

                    BufferedImage image;
                    if (accion.getChild("accion").getValue().equals("personalizada")) {
                        image = this.cargarImagenPersonaje(accion.getChild("encodedImage").getValue());
                    } else {
                        image = ImageIO.read(getClass().getResourceAsStream(accion.getChild("directorio").getValue()));
                    }
                    Administrador admin = Administrador.getInstance();
                    Usuario uListo = new Usuario(usrTemp.getNombre(), usrTemp.getContrasenia(),
                            usrTemp.getPartidasGanadas(), usrTemp.getMonedas(), new Jugador(0, image), this.socket);
                    this.conectado = false;

                    admin.agregarUsuario(uListo);
                    uListo.start();
                    this.enviar(admin.usuariosActivos());
                } else {
                    Element element = new Element("Respuesta");
                    this.enviar(Conversiones.anadirAccion(element, "ClienteActivo"));
                }
            } else {
                Element element = new Element("Respuesta");
                this.enviar(Conversiones.anadirAccion(element, "contraseniaIncorrecta"));
            }
        } else {
            Element element = new Element("Respuesta");
            this.enviar(Conversiones.anadirAccion(element, "NoExiste"));
        }*/
    }// iniciarSesion

    public boolean comprobarUsuarioYaActivo(Usuario usrTemp) {
        /*Administrador admin = Administrador.getInstance();
        for (int i = 0; i < admin.getUsuariosActivos().size(); i++) {
            if (admin.getUsuariosActivos().get(i).getNombre().equals(usrTemp.getNombre())) {
                return true;
            }
        } // for i
*/
        return false;
    }// comprobarInicioDeSesion

}// end class
