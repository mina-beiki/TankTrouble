package Client.onlineGUI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * State of the user in online game which stores the data of the user such as its
 * tank and handles its keyListener class .
 */
public class UserState {

    private PrintWriter writer;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keySPACE;
    private KeyHandler keyHandler;
    private Socket socket;

    /**
     * Generates a new instance of UserState .
     */
    public UserState(Socket socket){
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        try {
            writer = new PrintWriter((socket.getOutputStream()),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyHandler = new KeyHandler();
    }

    /**
     * The keyboard handler for pressing key .
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_SPACE:
                    keySPACE = true;
                    break;
            }
            writer.println("next move");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("up",keyUP);
            jsonObject.addProperty("down",keyDOWN);
            jsonObject.addProperty("left",keyLEFT);
            jsonObject.addProperty("right",keyRIGHT);
            jsonObject.addProperty("space",keySPACE);
            writer.println(new Gson().toJson(jsonObject));
        }

        /**
         * The keyboard handler for releasing key .
         */
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
                case KeyEvent.VK_SPACE:
                    keySPACE = false;
                    break;
            }
            writer.println("next move");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("up",keyUP);
            jsonObject.addProperty("down",keyDOWN);
            jsonObject.addProperty("left",keyLEFT);
            jsonObject.addProperty("right",keyRIGHT);
            jsonObject.addProperty("space",keySPACE);
            writer.println(new Gson().toJson(jsonObject));
        }


    }

    /**
     * Gets the key listener of user state .
     * @return KeyListener
     */
    public KeyListener getKeyListener() {
        return keyHandler;
    }


}
