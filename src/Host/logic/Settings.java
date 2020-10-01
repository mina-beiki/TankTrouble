package Host.logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Settings implements Serializable {
    private int tankLives , bulletsPower , brkWallsLives;
    private ArrayList<String> urls;
    private String tank;

    public Settings(){
        tankLives = 2;
        bulletsPower = 1;
        brkWallsLives = 2;
        urls = new ArrayList<>();
        tank = "White";
    }

    public Settings(int tankLives, int bulletsPower, int brkWallsLives, ArrayList<String> urls, String tank){
         this.tankLives = tankLives;
         this.brkWallsLives = brkWallsLives;
         this.bulletsPower = bulletsPower;
         this.urls = urls;
         this.tank = tank;
    }

    public int getTankLives() {
        return tankLives;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public void setTankLives(int tankLives) {
        this.tankLives = tankLives;
    }

    public int getBulletsPower() {
        return bulletsPower;
    }

    public void setBulletsPower(int bulletsPower) {
        this.bulletsPower = bulletsPower;
    }

    public int getBrkWallsLives() {
        return brkWallsLives;
    }

    public void setBrkWallsLives(int brkWallsLives) {
        this.brkWallsLives = brkWallsLives;
    }

    public String getTank() {
        return tank;
    }
}
