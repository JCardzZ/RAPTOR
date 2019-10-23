/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import Vista.Acceso;
import Vista.Menu;

/**
 *
 * @author J Cardoza
 */
public class POS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//      Menu m = new Menu();
//      m.setLocationRelativeTo(null);
//      m.setVisible(true);
//      m.setTitle("POS");
        Acceso login = new Acceso();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
    }

}
