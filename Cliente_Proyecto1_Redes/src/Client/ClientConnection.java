/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Domain.ParteImagen;
import Utility.Conversiones;
import Utility.Variables;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
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
                    Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDOMException ex) {
                    Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("Opcion: " + opcion);
        switch (opcion) {
            case "imprimir":
                System.out.println("HOLA");
                break;
        }//switch

    }//identificarAccion
    
    public BufferedImage convertirImagen(Image img){
            BufferedImage bufferedImage = new BufferedImage(img.getWidth(null)
                    , img.getHeight(null)
                    , BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D bGr = bufferedImage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();
            
            return bufferedImage;
    }//convertirImagen

    public void enviarImagen(ArrayList<ParteImagen> partes) throws IOException, InterruptedException {

        for (int i = 0; i < partes.size(); i++) {
            Element element = new Element("Image");

            BufferedImage img = this.convertirImagen(partes.get(i).getImagen());            
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
            eIdParte.addContent(i + "");
            element.addContent(eIdParte);
            
            Element ePosX = new Element("posX");
            ePosX.addContent(partes.get(i).getPosX()+"");
            element.addContent(ePosX);
            
            Element ePosY = new Element("posY");
            ePosY.addContent(partes.get(i).getPosY()+"");
            element.addContent(ePosY);
            
            this.send.println(Conversiones.xmlToString(element));
            Thread.sleep(100);
        }//for i

    }//enviarImagen

}//end class
