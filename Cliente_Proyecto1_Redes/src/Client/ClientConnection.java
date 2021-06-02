/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Domain.Cliente;
import Domain.Imagen;
import Domain.ParteImagen;
import Domain.Servidor;
import Utility.Conversiones;
import Utility.Variables;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Fabricio
 */
public class ClientConnection extends Thread {

    private static ClientConnection instance = null;
    private int socketPortNumber;
    private PrintStream send;
    private BufferedReader receive;
    private Socket socket;
    private InetAddress address;
    private boolean online;
    private String accion;

    private ClientConnection(String ipServer) throws IOException {
        this.socketPortNumber = Variables.PORTNUMBER;
        //this.address = InetAddress.getLocalHost();
        this.address = InetAddress.getByName(ipServer);
        this.socket = new Socket(this.address, this.socketPortNumber);
        this.send = new PrintStream(this.socket.getOutputStream());
        this.receive = new BufferedReader(
                new InputStreamReader(this.socket.getInputStream())
        );
        this.online = true;
        this.accion = "";
        this.start();
    }//constructor

    public static ClientConnection getInstance() throws IOException {
        if (instance == null) {
            instance = new ClientConnection(Variables.IPSERVER);
        }
        return instance;

    }//getInstance

    public static boolean isNull() {
        return instance == null;
    }//isNull

    public void run() {
        while (this.online) {
            if (this.socket != null) {
                try {
                    this.escuchando();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (JDOMException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }//try-catch

            }//if

        }//while

    }//run

    public void escuchando() throws IOException, JDOMException, InterruptedException {
        this.accion = this.receive.readLine();
        this.identificarAccion();

    }//escuchando

    public void enviar(String accion) {
        this.send.println(accion);

    }//enviar

    public void identificarAccion() throws JDOMException, IOException {
        Element element = Conversiones.stringToXML(this.accion);
        String opcion = element.getChild("accion").getValue();
        switch (opcion) {
            case "Cerrar":
                this.online = false;
                break;

            case "registrarse":
                JOptionPane.showMessageDialog(null, element.getChild("respuesta").getValue());
                break;

            case "iniciar sesion":
                JOptionPane.showMessageDialog(null, element.getChild("respuesta").getValue());
                if (element.getChild("respuesta").getValue().equals("SESION INICIADA CON EXITO")) {
                    Cliente cliente = Cliente.getInstance();
                    cliente.setNombre(element.getChild("nombre").getValue());
                }
                break;

            case "listar imagenes":
                this.listarImagenes(element);
                break;

            case "ver imagen":
                Servidor servidor = Servidor.getInstance();
                servidor.getImagen().getPartes().clear();
                this.verImagen(element);
                break;

            case "verificar llegada":
                this.verificarLlegada();
                break;

            case "respuesta llegada":
                if (element.getChild("respuesta").getValue().equals("llegada incompleta")) {
                    JOptionPane.showMessageDialog(null, "Error al recibir las partes. Realizar de nuevo la accion.");
                }else{
                    JOptionPane.showMessageDialog(null, "Enviada con exito");
                }
                break;

            case "borrar partes":
                Cliente cliente = Cliente.getInstance();
                cliente.getImagen().getPartes().clear();
                break;
            case "parte de imagen":
                this.insertarParteImagen(element);
                break;
        }//switch

    }//identificarAccion

    public void registrarse(String nombre, String contrasenia) {
        Element msg = new Element("msg");
        Conversiones.anadirAccion(msg, "registrar usuario");

        Element eNombre = new Element("nombre");
        eNombre.addContent(nombre);
        msg.addContent(eNombre);

        Element eContrasenia = new Element("contrasenia");
        eContrasenia.addContent(contrasenia);
        msg.addContent(eContrasenia);

        this.enviar(Conversiones.xmlToString(msg));
    }//registrarse

    public void enviarImagen(ArrayList<ParteImagen> partes, int opc) throws IOException, InterruptedException {

        for (int i = 0; i < partes.size(); i++) {
            Element element = new Element("Image");

            Element eOpc = new Element("opc");
            eOpc.addContent(opc + "");

            element.addContent(eOpc);

            BufferedImage img = Conversiones.convertirImagen(partes.get(i).getImagen());
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
            eIdParte.addContent(partes.get(i).getId() + "");
            element.addContent(eIdParte);

            Element ePosX = new Element("posX");
            ePosX.addContent(partes.get(i).getPosX() + "");
            element.addContent(ePosX);

            Element ePosY = new Element("posY");
            ePosY.addContent(partes.get(i).getPosY() + "");
            element.addContent(ePosY);

            this.send.println(Conversiones.xmlToString(element));
            Thread.sleep(100);
        }//for i
        
        this.enviar(Conversiones.anadirAccion(new Element("msg"), "verificar llegada"));
    }//enviarImagen

    public void listarImagenes(Element element) throws IOException {
        ArrayList<String> imagenes = new ArrayList<>();
        for (int i = 0; i < element.getChildren("imagen").size(); i++) {
            Element e = (Element) element.getChildren("imagen").get(i);
            imagenes.add(e.getValue());
        }//for i
        Servidor servidor = Servidor.getInstance();
        servidor.setImagenes(imagenes);
    }//listarImagenes

    public void insertarParteImagen(Element accion) throws IOException {
        int id = Integer.parseInt(accion.getChild("id").getValue());
        int x = Integer.parseInt(accion.getChild("posX").getValue());
        int y = Integer.parseInt(accion.getChild("posY").getValue());
        Image img = this.cargarImagen(accion.getChild("encodedImage").getValue());
        Cliente cliente = Cliente.getInstance();
        cliente.agregarParteImagen(new ParteImagen(id, x, y, img));
    }// insertarParteImagen

    public Image cargarImagen(String encodedImage) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(encodedImage);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
        Image tmp = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(),
                Image.SCALE_SMOOTH);
        return tmp;
    }// cargarImagenPersonaje

    public void verImagen(Element element) throws IOException {
        Servidor servidor = Servidor.getInstance();
        String encodedImage = element.getChild("encodedImage").getValue();
        byte[] bytes = Base64.getDecoder().decode(encodedImage);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
        Image tmp = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(),
                Image.SCALE_SMOOTH);
        servidor.setImagen(new Imagen());
        servidor.getImagen().asignarImagen(tmp);
    }//verImagen

    public void verificarLlegada() throws IOException {
        Element msg = new Element("mgs");
        Element respuesta = new Element("respuesta");
        Cliente cliente = Cliente.getInstance();
        System.out.println("Size: "+ cliente.getImagen().getPartes().size());
        if (cliente.getImagen().getPartes().size() == 25) {
            respuesta.addContent("llegada completa");
        } else {
            respuesta.addContent("llegada incompleta");
        }

        msg.addContent(respuesta);
        this.enviar(Conversiones.anadirAccion(msg, "respuesta llegada"));
    }//verificarLlegada

}//end class
