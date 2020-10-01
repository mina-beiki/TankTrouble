package Client.logic;

import java.io.File;
import java.util.Random;

/**
 * Handles computer player state by having control of its tank
 * movement (randomly) and updating it through the game .
 */
public class CompState {

    private Tank tank,otherTank;
    private boolean gameOver, flag;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keySPACE;
    private int ctr;


    /**
     * Generates a new comp state with the given tank object .
     * @param otherTank Tank , tank of the computer player .
     */
    public CompState(Tank otherTank) {
        gameOver = false;
        flag = false;
        ctr = 0;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        keySPACE = false;
        //
        tank = new Tank();
        tank.setTankImage(new File("tanks/comp.png"));
        this.otherTank = otherTank;
    }

    /**
     * The method which updates the computer state.
     */
    public void update() {

        boolean flag = nextMove();
        if(flag) {
            if (ctr % 20 == 0) {
                if (tank.checkTwoBullets()) {
                    //tank.newBullet();
                }
            }
        }


    }

    /**
     * Does the next move by calculating the distance for between computer player
     * and user .
     * @return boolean
     */
    private boolean nextMove(){
        int tankX=tank.getTankX(),tankY= tank.getTankY(),newX,newY,otherX = otherTank.getTankX(),otherY = otherTank.getTankY();
        double tankXSpeed = tank.getTankXSpeed(),tankYSpeed = tank.getTankYSpeed();
        double distance1 , distance2;
        boolean forward = false, backward = false;

        if (GameFrame.getInstance().checkWalls((tankX+tankXSpeed + 10),(tankY+tankYSpeed +10))) {
            forward = true;
        }
        if (GameFrame.getInstance().checkWalls((tankX-tankXSpeed + 10),(tankY-tankYSpeed + 10 ))) {
            backward = true;
        }
        distance1 = calcDistance(tankX+tankXSpeed,otherX,tankY+tankYSpeed,otherY);
        distance2 = calcDistance(tankX-tankXSpeed,otherX,tankY-tankYSpeed,otherY);
        if(forward && backward){
            if(distance1 < distance2){
                tank.moveTank("up");
                tank.moveTank("left");
            }else {
                tank.moveTank("down");
            }
        }else if(forward){
            tank.moveTank("up");
            tank.moveTank("left");
        }else if(backward){
            tank.moveTank("down");
            tank.moveTank("left");
        }else {
            tank.moveTank("left");
            tank.moveTank("up");
        }
        if((distance1>30 && distance1<100) || (distance2>30 && distance2<100)){
            return true;
        }
        return false;
    }

    /**
     * Calculates the distance between user and comp player .
     * @param x1 int , starting point x
     * @param x2 int , ending point x
     * @param y1 int , starting point y
     * @param y2 int , ending point x
     * @return double , distance
     */
    private double calcDistance(double x1,double x2,double y1,double y2){
        double xd = Math.pow(x1-x2,2),yd = Math.pow(y1-y2,2);
        double dist = Math.sqrt(xd+yd);
        return dist;
    }


    /**
     * Gets computer player tank
     * @return Tank , tank
     */
    public Tank getTank() {
        return tank;
    }
}
