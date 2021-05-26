/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Utility.Variables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
    }//constructor

    public static ClientConnection getInstance(String ipServer) throws IOException {
        if (instance == null) {
            instance = new ClientConnection(ipServer);
        }
        return instance;

    }//getInstance

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
    
    public void identificarAccion(){
        
        
    }//identificarAccion

}//end class
