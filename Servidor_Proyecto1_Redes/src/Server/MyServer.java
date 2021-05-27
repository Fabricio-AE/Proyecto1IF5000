package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.jdom.JDOMException;

import Utility.Variables;

public class MyServer extends Thread {

    private int socketPortNumber;
    private String ipServer;

    public MyServer() throws UnknownHostException {
        super("Hilo Servidor");
        this.socketPortNumber = Variables.PORTNUMBER;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            int i = 0;
            while (i == 0) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                InetAddress addr = addresses.nextElement();

                this.ipServer = addr.getHostAddress();
                i++;
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    } // constructor

    public int getSocketPortNumber() {
        return socketPortNumber;
    }

    public void setSocketPortNumber(int socketPortNumber) {
        this.socketPortNumber = socketPortNumber;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.socketPortNumber);

            do {

                Socket socket = serverSocket.accept();

                ConsultaCliente consultaCliente = new ConsultaCliente(socket);
                consultaCliente.start();

            } while (true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    } // run
}//end class

