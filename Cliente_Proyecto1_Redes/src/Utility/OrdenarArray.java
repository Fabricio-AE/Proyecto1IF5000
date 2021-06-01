/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Domain.ParteImagen;
import java.util.Comparator;

/**
 *
 * @author Fabricio
 */
public class OrdenarArray implements Comparator<ParteImagen> {

    @Override
    public int compare(ParteImagen o2, ParteImagen o1) {
        if (o1.getId()> o2.getId()) {
            return -1;
        } else if (o1.getId() > o2.getId()) {
            return 0;
        } else {
            return 1;
        }
    }//compare

}// end class
