package GUI;

import java.awt.Graphics;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Domain.Server;
import Server.MyServer;
import Utility.Variables;

public class VentanaPrincipal extends JPanel {

	private MyServer myServer;
	private Server admin;
	private JLabel jlblIpServer;

	public VentanaPrincipal() {
		try {
			this.myServer = new MyServer();
			this.myServer.start();
			this.admin = Server.getInstance();
			this.setSize(390, 460);
			this.setLayout(null);
			this.init();
			this.setVisible(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} // try-catch
	}// constructor

	public void init() {
		
		this.jlblIpServer=new JLabel(this.myServer.getIpServer()+":"+Variables.PORTNUMBER);
		this.jlblIpServer.setBounds(150,150,200,30);
		this.add(this.jlblIpServer);
		
		
	}//init
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.repaint();
	}

}// end class
