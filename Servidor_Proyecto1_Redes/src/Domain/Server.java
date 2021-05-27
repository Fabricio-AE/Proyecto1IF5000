/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;

/**
 *
 * @author Fabricio
 */
public class Server {
    
    private static Server instance = null;
    
    private Server(){
        
    }//constructor
    
    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }//if
        return instance;
    }//getInstance
    
}//end class
