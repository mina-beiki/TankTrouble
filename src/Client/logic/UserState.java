package Client.logic;

import Client.Client;
import Host.logic.Settings;

import java.awt.event.*;

/**
 * State of the user in offline game which stores the data of the user such as its
 * tank and handles its keyListener class .
 */
public class UserState {

    private Tank tank;
    private String userName ;
    private boolean gameOver, flag;
    private boolean laser, extraLive, bulletPower, protection; //bonuses on the road
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keySPACE;
    private KeyHandler keyHandler;

    /**
     * Generates a new instance of UserState .
     */
    public UserState() {
        gameOver = false;
        flag = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        laser = false;
        extraLive = false;
        bulletPower = false;
        protection = false;
        //
        keyHandler = new KeyHandler();
        //
        tank = new Tank();
        //
        userName = Client.getInstance().getUser().getUsername();
    }

    /**
     * The method which updates the user state.
     */
    public void update() {
        boolean flag = false;
        if (keyUP){
            tank.moveTank("up");
        }
        if (keyDOWN){
            tank.moveTank("down");
        }
        if (keyLEFT){
            tank.moveTank("left");
        }
        if (keyRIGHT){
            tank.moveTank("right");
        }

        if (keySPACE) {
           if (tank.checkTwoBullets()){
               tank.newBullet();
           }
        }


    }


    public KeyListener getKeyListener() {
        return keyHandler;
    }


    /**
     * The keyboard handler.
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
        }

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
        }

    }

    /**
     * Gets tank of the user .
     * @return Tank , user's tank
     */
    public Tank getTank() {
        return tank;
    }

    /**
     * Gets username of user .
     * @return String , user name
     */
    public String getUserName() {
        return userName;
    }
}


