package Client.logic;

import Client.Client;
import Client.gui.Wall;
import java.io.File;

/**
 * Presents one bullet in the offline game with all its features such as location , speed , time
 * handling  and image file . Handles movement of a bullet in the map and reflecting it
 * when hitting walls and also breaking the breakable walls when they have no other lives left .
 */
public class Bullet {
    private int x, y, bulletSpeed, bulletPower;
    private long shootTime;
    private double speedX, speedY, angle, lifeSpan;
    private File bulletImage;

    /**
     * Makes a new bullet object .
     * @param angle Angle of the bullet
     * @param xTank location x
     * @param yTank location y
     */
    public Bullet(double angle, int xTank, int yTank) {
        shootTime = System.nanoTime();
        lifeSpan = 0;
        x = xTank;
        y = yTank;
        bulletSpeed = 8;
        this.angle = angle;
        bulletImage = new File("tanks/bullet.png");
        speedY = Math.sin(Math.toRadians(angle)) * bulletSpeed;
        speedX = Math.cos(Math.toRadians(angle)) * bulletSpeed;
        bulletPower = Client.getInstance().getUser().getSettings().getBulletsPower();
    }

    /**
     * Makes the bullet move according to the other walls and reflects it when
     * hitting walls .
     */
    public void moveBullet() {

        if (hitHorizontal(x + speedX, y + speedY)) {
            speedY *= -1;
        }
        if (hitVertical(x + speedX, y + speedY)) {
            speedX *= -1;
        }

        x += speedX;
        y += speedY;

        int halfBullet = 10;
        x = Math.max(x, 0);
        x = Math.min(x, GameFrame.getWIDTH() - halfBullet);
        y = Math.max(y, 0);
        y = Math.min(y, GameFrame.getWIDTH() - halfBullet);

    }

    /**
     * Gets the time which the bullet is visible in the map .
     * @return long , time of bullet
     */
    public long getShootTime() {
        return shootTime;
    }

    /**
     * Returns image file of bullet .
     * @return File , bullet image
     */
    public File getBulletImage() {
        return bulletImage;
    }

    /**
     * Gets bullet's location x .
     * @return int , x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets bullet's location y .
     * @return int , y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the angle of the bullet .
     * @return double , angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Handles a bullet hitting horizontal walls . When hitting simple walls it should be reflected
     * and when hitting breakable walls it should break them unless they have lives left .
     * If they have lives left , bullets will decrease them .
     * @param x double , location x of bullet
     * @param y double , location y of bullet
     * @return boolean , true if it should be reflected and false if not .
     */
    private boolean hitHorizontal(double x, double y) {
        for (Wall wall : GameFrame.getInstance().getHorizontalWalls()) {
            if (x >= wall.getX1() && x <= wall.getX2()) {
                if (Math.abs(y - wall.getY1()) < 4) {
                    if (wall.getType().equals("brk")) {
                        if (wall.getLives()==0) {
                            //break the wall :
                            GameFrame.getInstance().hitBrkWall(wall);
                            return false;
                        } else if (wall.getLives() > 0) {
                            System.out.println(wall.getLives());
                            wall.decreaseLives();
                            return true ;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles a bullet hitting vertical walls . When hitting simple walls it should be reflected
     * and when hitting breakable walls it should break them unless they have lives left .
     * If they have lives left , bullets will decrease them .
     * @param x double , location x of bullet
     * @param y double , location y of bullet
     * @return boolean , true if it should be reflected and false if not .
     */
    private boolean hitVertical(double x, double y) {
        for (Wall wall : GameFrame.getInstance().getVerticalWalls()) {
            if (y >= wall.getY1() && y <= wall.getY2()) {
                if (Math.abs(x - wall.getX1()) < 4) {
                    if (wall.getType().equals("brk")) {
                        if (wall.getLives()==0) {
                            //break the wall :
                            GameFrame.getInstance().hitBrkWall(wall);
                            return false;
                        } else if (wall.getLives() > 0) {
                            wall.decreaseLives();
                            return true ;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}


