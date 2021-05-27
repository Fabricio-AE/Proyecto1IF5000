package Utility;

import java.util.Comparator;

import Domain.ParteImagen;

public class OrdenarArray implements Comparator<ParteImagen> {

	@Override
    public int compare(ParteImagen o2, ParteImagen o1) {
        if (o1.getId() > o2.getId()) {
            return -1;
        } else if (o1.getId() > o2.getId()) {
            return 0;
        } else {
            return 1;
        }
    }//compare

}// end class