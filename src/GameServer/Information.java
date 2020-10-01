package GameServer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Consists of all information that the server sends .
 */
public class Information implements Serializable {
    private ArrayList<Tank> tanks;
    private ArrayList<Wall> walls;
    private ArrayList<Integer> tiles;
    private int width;
    private int height;

    /**
     * Makes a new instance of information .
     * @param tanks
     * @param walls
     * @param tiles
     * @param width
     * @param height
     */
    public Information(ArrayList<Tank> tanks, ArrayList<Wall> walls, ArrayList<Integer> tiles, int width, int height) {
        this.tanks = tanks;
        this.walls = walls;
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }

    public ArrayList<Tank> getTankss() {
        return tanks;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Integer> getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
