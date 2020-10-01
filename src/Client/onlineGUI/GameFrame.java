package Client.onlineGUI;



import GameServer.Bullet;
import GameServer.Tank;
import GameServer.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The main frame of the offline game which shows the map , tanks , bullets and other
 * components . Handles the rendering of the game and is updated 30 times
 * per second . Reads a random map from maps directory and shows it on the frame .
 *
 */
public class GameFrame extends JFrame {

    private ArrayList<Integer> tiles;
    private int height, width; //for the map array
    private static int GAME_HEIGHT, GAME_WIDTH;
    private ArrayList<Wall> walls, verticalWalls, horizontalWalls, brokenWalls;
    private ArrayList<Tank> tanks;
    //    private ArrayList<Bonus> bonuses ;
    private BufferStrategy bufferStrategy;
    private static GameFrame gameFrame= new GameFrame();

    /**
     * Generates a new game frame for an online game and does the map reading with creating
     * all walls' objects .
     */
    private GameFrame() {
        super("Game Map");

        this.setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        GAME_HEIGHT = 360;
        GAME_WIDTH = 16 * GAME_HEIGHT / 9;
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        tiles = new ArrayList<>();
        walls = new ArrayList<>();
//        verticalWalls = new ArrayList<>();
//        horizontalWalls = new ArrayList<>();
        tanks = new ArrayList<Tank>();
//        bonuses = new ArrayList<>();

    }

    /**
     * Gets instance of GameFrame  .
     * @return GameFrame
     */
    public static GameFrame getInstance(){
        return gameFrame;
    }

    public void printMap(Graphics2D g2d) {
        walls.clear();
        //System.out.println(tiles);

        int x = 3;
        int y = 25;
        int index = 0;

        //map using lines :

        int a = GAME_HEIGHT / (height - 1);
        int b = GAME_WIDTH / (width - 1);

        //horizontal lines :
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                index = (i * width) + j;
                //wall
                if (index != tiles.size() - 1) {
                    if ((!((index + 1) % width == 0)) || index == 0) {
                        if (tiles.get(index) == 1) {
                            if (tiles.get(index + 1) == 1 || tiles.get(index + 1) == 2) {
                                g2d.setPaint(Color.DARK_GRAY);
                                g2d.setStroke(new BasicStroke(7.0f));
//                                Wall wall = new Wall(x, y, x + b, y, "smp", "hor");
//                                walls.add(wall);
                                g2d.drawLine(x, y, x + b, y);
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + 1) == 1 || tiles.get(index + 1) == 2) {
                                g2d.setPaint(new Color(162, 128, 79));
                                g2d.setStroke(new BasicStroke(7.0f));
//                                Wall wall = new Wall(x, y, x + b, y, "brk", "hor");
//                                walls.add(wall);
                                g2d.drawLine(x, y, x + b, y);
                            }
                        }
                    }
                }
                x += b;
            }
            x = 3;
            y += a;
        }


        //vertical lines :
        x = 3;
        y = 25;
        index = 0;
        int ctr = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //wall
                if (index != tiles.size() - 1) {
                    if ((index + width) < tiles.size()) {
                        if (tiles.get(index) == 1) {
                            if (tiles.get(index + width) == 1 || tiles.get(index + width) == 2) {
                                g2d.setPaint(Color.DARK_GRAY);
                                g2d.setStroke(new BasicStroke(7.0f));
//                                Wall wall = new Wall(x, y, x, y + a, "smp", "ver");
//                                walls.add(wall);
                                g2d.drawLine(x, y, x, y + a);
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + width) == 1 || tiles.get(index + width) == 2) {
                                g2d.setPaint(new Color(162, 128, 79));
                                g2d.setStroke(new BasicStroke(7.0f));
//                                Wall wall = new Wall(x, y, x, y + a, "smp", "ver");
//                                walls.add(wall);
                                g2d.drawLine(x, y, x, y + a);
                            }
                        }
                    }
                }
                index += width;
                y += a;
            }
            ctr++;
            index = ctr;
            y = 25;
            x += b;

        }
    }

    /**
     * Shows the window .
     */
    public void showWindow() {
        this.setVisible(true);
    }

    /**
     * Initializes the bufferStrategy .
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Renders the given game state by calling the doRendering method and diong all
     * the drawings for map and other components .
     * @param state GameState , state to be rendered
     */
    public void render() {
        // Get a new graphics context to render the current frame
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        try {
            // Do the rendering
            doRendering(graphics);
        } finally {
            // Dispose the graphics, because it is no more needed
            graphics.dispose();
        }
        // Display the buffer
        bufferStrategy.show();
        // Tell the system to do the drawing NOW;
        // otherwise it can take a few extra ms and will feel jerky!
        Toolkit.getDefaultToolkit().sync();
    }
    /**
     * Draws and prints all the essential components of game frame uch as map , tanks , bullets
     * and user names for players .
     * @param g2d Graphics2D
     * @param state GameState , state to be rendered
     */
    public void doRendering(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 1000, 1000);
        g2d.setColor(Color.BLACK);
        //draw map :
        printMap(g2d);

        //draw tanks :
        try {
            for(Tank tank: tanks) {
                BufferedImage image = ImageIO.read(tank.getImage());
                Double angle = tank.getAngle();
                BufferedImage rotatedImg = rotateImageByDegrees(image, angle);
                g2d.drawImage(rotatedImg, tank.getTankX(), tank.getTankY(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //draw bullets :
        for(Tank tank: tanks) {
            ArrayList<Bullet> bullets = new ArrayList<>();
            bullets.addAll(tank.getBullets());
            Iterator it = bullets.iterator();
            while (it.hasNext()) {
                Bullet bullet = (Bullet) it.next();
                try {
                    BufferedImage image = ImageIO.read(bullet.getBulletImage());
                    Double angle = bullet.getAngle();
                    BufferedImage rotatedImg = rotateImageByDegrees(image, angle);
                    g2d.drawImage(rotatedImg, bullet.getX(), bullet.getY(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //draw bonuses :



        bufferStrategy.show();

    }

    /**
     * Rotates the image with the given angle and prints it using AffineTransformation .
     * @param img BufferedImage , image to be printed
     * @param angle double , angle of the rotation
     * @return BufferedImage , result image
     */
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    /**
     * updates the gameFrame .
     * @param tanks ArrayList<Tank> , tanks
     * @param walls ArrayList<Wall> , walls
     * @param tiles ArrayList<Integer> , tiles
     * @param width int , width
     * @param height int , height
     */
    public void update(ArrayList<Tank> tanks,ArrayList<Wall> walls,ArrayList<Integer> tiles,int width,int height){
        this.tanks = tanks;
        this.walls = walls;
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }


    /**
     * Gets array list of walls .
     * @return ArrayList<Wall> , all walls
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

}