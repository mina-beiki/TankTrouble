package Client.logic;

import Client.gui.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The main frame of the offline game which shows the map , tanks , bullets and other
 * components . Handles the rendering of the game and is updated 30 times
 * per second . Reads a random map from maps directory and shows it on the frame .
 *
 */
public class GameFrame extends JFrame {


    private File map, mapsDirectory;
    private ArrayList<Integer> tiles;
    private int height, width; //for the map array
    private static int GAME_HEIGHT, GAME_WIDTH;
    private ArrayList<Wall> walls, verticalWalls, horizontalWalls;
    private static GameFrame gameFrame = new GameFrame();
    public static GameFrame getInstance() {
        return gameFrame;
    }
    private BufferStrategy bufferStrategy;


    /**
     * Generates a new game frame for a game and does the map reading with creating
     * all walls' objects .
     */
    public GameFrame() {
        super("Game Map");
        mapsDirectory = new File("maps");
        this.setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        GAME_HEIGHT = 360;
        GAME_WIDTH = 16 * GAME_HEIGHT / 9;
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        tiles = new ArrayList<>();
        walls = new ArrayList<>();
        verticalWalls = new ArrayList<>();
        horizontalWalls = new ArrayList<>();
        readMap();
        createWalls();
    }

    /**
     * Reads a random chosen map from maps directory and saves it into an arrayList of
     * integers ( tiles ) .
     */
    private void readMap() {
        //read map from a random file in maps directory :
        int size = mapsDirectory.list().length;
        int fileIndex = getRandomNumber(0, size);
        System.out.println("map index:" + fileIndex);

        File[] files = mapsDirectory.listFiles();
        int ctr = -1;
        for (File file : files) {
            ctr++;
            if (ctr == fileIndex) {
                map = file;
            }
        }
        //read map file :

        String content = "";
        try {
            FileReader fr = new FileReader(map);
            int i;
            while ((i = fr.read()) != -1)
                content = content + (char) i;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);

        width = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '\n') {
                break;
            }
            width++;
        }
        //convert string to array list of integers :
        content = content.replaceAll("\n", "");
        System.out.println(content);
        height = content.length() / width;
        System.out.println("width:" + width + " height:" + height);

        //make an array list of integers for rooms :
        for (int i = 0; i < content.length(); i++) {
            tiles.add(Integer.parseInt(String.valueOf(content.charAt(i))));
        }
    }

    /**
     * Creates all walls' objects of the game and saves it into walls ArrayList .
     */
    public void createWalls() {
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
                                Wall wall = new Wall(x, y, x + b, y, "smp", "hor");
                                horizontalWalls.add(wall);
                                walls.add(wall);
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + 1) == 1 || tiles.get(index + 1) == 2) {
                                Wall wall = new Wall(x, y, x + b, y, "brk", "hor");
                                horizontalWalls.add(wall);
                                walls.add(wall);
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
                                Wall wall = new Wall(x, y, x, y + a, "smp", "ver");

                                verticalWalls.add(wall);
                                walls.add(wall);
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + width) == 1 || tiles.get(index + width) == 2) {
                                Wall wall = new Wall(x, y, x, y + a, "brk", "ver");

                                verticalWalls.add(wall);
                                walls.add(wall);
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
     * Prints the map of the game by using graphics 2d and drawLine method . Each wall
     * is considered as a line and breakable walls and simple walls are distinguished by their
     * colors .
     * @param g2d
     */
    public void printMap(Graphics2D g2d) {
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
                                Wall wall = new Wall(x, y, x + b, y, "smp", "hor");
                                if (walls.contains(wall)) {
                                    g2d.drawLine(x, y, x + b, y);
                                }
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + 1) == 1 || tiles.get(index + 1) == 2) {
                                g2d.setPaint(new Color(162, 128, 79));
                                g2d.setStroke(new BasicStroke(7.0f));
                                Wall wall = new Wall(x, y, x + b, y, "brk", "hor");
                                if (walls.contains(wall)) {
                                    g2d.drawLine(x, y, x + b, y);
                                }
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
                                Wall wall = new Wall(x, y, x, y + a, "smp", "ver");
                                if (walls.contains(wall)) {
                                    g2d.drawLine(x, y, x, y + a);
                                }
                            }
                        } else if (tiles.get(index) == 2) {
                            if (tiles.get(index + width) == 1 || tiles.get(index + width) == 2) {
                                g2d.setPaint(new Color(162, 128, 79));
                                g2d.setStroke(new BasicStroke(7.0f));
                                Wall wall = new Wall(x, y, x, y + a, "brk", "ver");
                                if (walls.contains(wall)) {
                                    g2d.drawLine(x, y, x, y + a);
                                }
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
     * Generates a random number within the given range .
     * @param min int , min of the range
     * @param max int , max of the range
     * @return int , random number
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * shows the window
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
    public void render(GameState state) {
        // Get a new graphics context to render the current frame
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        try {
            // Do the rendering
            doRendering(graphics, state);
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
    public void doRendering(Graphics2D g2d, GameState state) {

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 1000, 1000);
        g2d.setColor(Color.BLACK);
        //draw map :
        printMap(g2d);
        //draw tanks :
        //user tank :
        if(state.isGameOverUser()){
            try {
                BufferedImage image = ImageIO.read(new File("tanks/explosion.png"));
                g2d.drawImage(image, state.getUserState().getTank().getTankX(), state.getUserState().getTank().getTankY(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            try {

                BufferedImage image = ImageIO.read(state.getUserState().getTank().getImage());
                Double angle = state.getUserState().getTank().getAngle();
                BufferedImage rotatedImg = rotateImageByDegrees(image, angle);
                g2d.drawImage(rotatedImg, state.getUserState().getTank().getTankX(), state.getUserState().getTank().getTankY(), null);
                // print user name :
                g2d.setFont(new Font("Helvetica", Font.BOLD, 12));
                g2d.drawString(state.getUserState().getUserName(),state.getUserState().getTank().getTankX() ,state.getUserState().getTank().getTankY()-10 );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO: PC player
        if(state.isGameOverComp()){
            try {
                BufferedImage image = ImageIO.read(new File("tanks/explosion.png"));
                g2d.drawImage(image, state.getCompState().getTank().getTankX(), state.getCompState().getTank().getTankY(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                BufferedImage image = ImageIO.read(state.getCompState().getTank().getImage());
                Double angle = state.getCompState().getTank().getAngle();
                BufferedImage rotatedImg = rotateImageByDegrees(image, angle);
                g2d.drawImage(rotatedImg, state.getCompState().getTank().getTankX(), state.getCompState().getTank().getTankY(), null);
                // print user name :
                g2d.setFont(new Font("Helvetica", Font.BOLD, 12));
                g2d.drawString("Computer",state.getCompState().getTank().getTankX() ,state.getCompState().getTank().getTankY()-10 );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //draw bullets :

        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.addAll(state.getUserState().getTank().getBullets());
        bullets.addAll(state.getCompState().getTank().getBullets());
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
     * Gets the height of the game frame .
     * @return int , Height
     */
    public static int getHEIGHT() {
        return GAME_HEIGHT;
    }

    /**
     * Gets the width of the game frame .
     * @return int , Width
     */
    public static int getWIDTH() {
        return GAME_WIDTH;
    }

    /**
     * Handles the tank not getting into the walls and not crossing them .
     * @param x int , location x of the tank
     * @param y int , location y of the tank
     * @return boolean
     */
    public boolean checkWalls(double x, double y) {
        for (Wall wall : walls) {
            if (wall.getLineType().equals("hor")) {
                if (x >= wall.getX1() && x <= wall.getX2()) {
                    if (Math.abs(y - wall.getY1()) < 10) {
                        return false;
                    }
                }
            }
            if (wall.getLineType().equals("ver")) {
                if (y >= wall.getY1() && y <= wall.getY2()) {
                    if (Math.abs(x - wall.getX1()) < 10) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Is called when hitting a breakable wall and removes the wall when the
     * bullet has hit it .
     * @param wallToBreak Wall , wall to be broken
     */
    public void hitBrkWall(Wall wallToBreak) {
        walls.remove(wallToBreak);
        if (wallToBreak.getLineType().equals("hor")) {
            horizontalWalls.remove(wallToBreak);
        }
        if (wallToBreak.getLineType().equals("ver")) {
            verticalWalls.remove(wallToBreak);
        }
    }

    /**
     * Gets array list of walls .
     * @return ArrayList<Wall> , all walls
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * Gets array list of vertical walls .
     * @return ArrayList<Wall> , vertical walls
     */
    public ArrayList<Wall> getVerticalWalls() {
        return verticalWalls;
    }

    /**
     * Gets array list of horizontal  walls .
     * @return ArrayList<Wall> , horizontal walls
     */
    public ArrayList<Wall> getHorizontalWalls() {
        return horizontalWalls;
    }
}

