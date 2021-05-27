/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.VentanaPrincipal;
import javax.swing.JFrame;

/**
 *
 * @author Fabricio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setSize(410, 500);
        jframe.add(new VentanaPrincipal());
        jframe.setLocationRelativeTo(null);
        jframe.setLayout(null);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//main

}//end class
