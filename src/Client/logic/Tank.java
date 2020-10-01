package Client.logic;

import Client.Client;
import Client.gui.Wall;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Simulation of tank in the game consisting of all of its components such as
 * location , bullets , moveThread and speed .
 * Handles tank moving and bullet shooting .
 */
public class Tank {
    private Thread thread, moveThread;
    private int tankX , tankY , lives  , speed;
    private double angle,tankXSpeed , tankYSpeed;
    private String color ;
    private File tankImage;
    private volatile ArrayList<Bullet> bullets;


    /**
     * Generates a new tank instance and sets random location for its initial
     * location .
     */
    public Tank() {

        while (true){
            Random random = new Random();
            tankX=random.nextInt(GameFrame.getWIDTH()-10);
            tankY=random.nextInt(GameFrame.getHEIGHT()-10);
            if(initTank()){
                break;
            }
        }
        angle = 0;
        speed = 3;
        tankYSpeed = Math.sin(Math.toRadians(angle))*speed;
        tankXSpeed = Math.cos(Math.toRadians(angle))*speed;
        color = Client.getInstance().getUser().getSettings().getTank();
        if(color.equals("Blue")){
            tankImage = new File("tanks/blue.png");
        }
        if(color.equals("White")){
            tankImage = new File("tanks/white.png");

        }if(color.equals("Black")){
            tankImage = new File("tanks/black.png");

        }if(color.equals("Red")){
            tankImage = new File("tanks/red.png");

        }if(color.equals("Green")){
            tankImage = new File("tanks/green.png");
        }
        bullets = new ArrayList<Bullet>();
        thread = new Thread(new BulletThread());
        thread.start();
        moveThread = new Thread(new BulletMoverThread());
        moveThread.start();

    }

    /**
     * Shoots a new bullet .
     */
    public void newBullet(){
        Bullet bullet = new Bullet(angle,tankX,tankY);
        bullet.moveBullet();
        bullets.add(bullet);
    }

    /**
     * Handles all the bullets of a tank which is implemented using synchronized threads .
     */
    private class BulletThread implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (bullets) {
                    Iterator it = bullets.iterator();
                    while (it.hasNext()) {
                        Bullet bullet = (Bullet) it.next();
                        if (((double) (System.nanoTime() - bullet.getShootTime()) / 1_000_000_000.0) > 4) {
                            it.remove();
                        }
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Handles the movement of bullets .
     */
    private class BulletMoverThread implements Runnable{
        @Override
        public void run() {
            while (true){
                ArrayList<Bullet> copy = new ArrayList<>();
                copy.addAll(bullets);
                synchronized (copy) {
                    Iterator it = copy.iterator();
                    while (it.hasNext()) {
                        Bullet bullet = (Bullet) it.next();
                        bullet.moveBullet();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Checks the rule of 2 seconds for bullets. (No more than 2 bullets in 2 seconds . )
     * @return boolean
     */
    public boolean checkTwoBullets(){
        int num = 0;
        ArrayList<Bullet> copy = new ArrayList<>();
        copy.addAll(bullets);
        for(Bullet bullet: copy){
            if(((double)(System.nanoTime() - bullet.getShootTime())/1_000_000_000.0) < 1){
                num++;
            }
        }
        return num<2;
    }

    /**
     * Gets image file of tank .
     * @return File , image
     */
    public File getImage() {
        return tankImage;
    }


    /**
     * Moves the tank by using the keys the user has pressed .
     * @param move String , keyBoard key chosen (Direction)
     */
    public void moveTank(String move){
        if(move.equals("up")){
            moveForward();
        }else if(move.equals("down")){
            moveBackward();
        }else if(move.equals("left")){
            decreaseAngle();
        }else {
            increaseAngle();
        }
        int halfTank = 25; //TODO : this should be set according to the tank
        setTankX(Math.max(tankX, 3));
        setTankX(Math.min(tankX, GameFrame.getWIDTH() - halfTank + 3));
        setTankY(Math.max(tankY, 25));
        setTankY(Math.min(tankY, GameFrame.getHEIGHT() - halfTank + 25));

    }

    /**
     * Moves the tank in forward direction in an specific angle .
     */
    private void moveForward(){
        tankYSpeed = Math.sin(Math.toRadians(angle))*speed;
        tankXSpeed = Math.cos(Math.toRadians(angle))*speed;
        if (GameFrame.getInstance().checkWalls((tankX+tankXSpeed + 10),(tankY+tankYSpeed +10))) {
            tankX += tankXSpeed;
            tankY += tankYSpeed;
        }
    }

    /**
     * Moves the tank in backward direction in an specific angle .
     */
    private void moveBackward(){
        tankYSpeed = Math.sin(Math.toRadians(angle))*speed;
        tankXSpeed = Math.cos(Math.toRadians(angle))*speed;
        if (GameFrame.getInstance().checkWalls((tankX-tankXSpeed + 10),(tankY-tankYSpeed + 10 ))) {
            tankX -= tankXSpeed;
            tankY -= tankYSpeed;
        }
    }

    /**
     * Initializes the random location of tank when the game starts .
     * @return boolean , true if it is a correct location based on the walls
     */
    private boolean initTank (){
        int centerX = tankX + 10 ;
        int centerY = tankY + 10 ;
        for(Wall wall : GameFrame.getInstance().getWalls()){
            if(wall.getLineType().equals("hor")){
                if(centerX>=wall.getX1() && centerX<=wall.getX2() ) {
                    if(Math.abs(centerY-wall.getY1())<10) {
                        return false;
                    }
                }
            }
            if(wall.getLineType().equals("ver")){
                if (centerY >= wall.getY1() && centerY <= wall.getY2()) {
                    if(Math.abs(centerX-wall.getX1())<10) {
                        return false;
                    }
                }
            }
        }
        if(centerX < GameFrame.getWIDTH() && centerY <GameFrame.getHEIGHT()) {
            return true;
        }
        return false ;
    }

    /**
     * increases the angle of tank .
     */
    private void increaseAngle(){
        angle+=3;

    }


    /**
     * decreases the angle of tank .
     */
    private void decreaseAngle(){
        angle-=3;
    }

    /**
     * gets tank x .
     * @return int , location x
     */
    public int getTankX() {
        return tankX;
    }

    /**
     * gets tank x .
     * @return int , location x
     */
    public int getTankY() {
        return tankY;
    }

    /**
     * gets tank speed in x direction  .
     * @return double , speed x
     */
    public double getTankXSpeed() {
        return tankXSpeed;
    }

    /**
     * gets tank speed in y direction .
     * @return double , speed y
     */
    public double getTankYSpeed() {
        return tankYSpeed;
    }

    /**
     * sets tank image .
     * @param tankImage File , image file
     */
    public void setTankImage(File tankImage) {
        this.tankImage = tankImage;
    }

    /**
     * gets the angle of tank .
     * @return double , angle
     */
    public double getAngle(){
        return angle;
    }

    /**
     * sets tank x .
     * @param tankX int , tank x
     */
    public void setTankX(int tankX) {
        this.tankX = tankX;
    }

    /**
     * sets tank y .
     * @param tankY int , tank y
     */
    public void setTankY(int tankY) {
        this.tankY = tankY;
    }

    /**
     * gets bullets of a tank  .
     * @return ArrayList<Bullet> , bullets
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
