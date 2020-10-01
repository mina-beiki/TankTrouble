package Client;

import Client.gui.LoginWindow;
import javax.swing.*;

/**
 * Client main, that handle client GUI and client sockets.
 */
public class ClientMain {

    public static void main(String[] args) {
        //set laf :
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            e.printStackTrace();
        }

        //Start game with signing up or logging in
        LoginWindow.getInstance().setup();
        LoginWindow.getInstance().showWindow();

    }
}