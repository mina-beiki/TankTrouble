package GameServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapHandler {
    private File map, mapsDirectory;
    private int height, width;
    private ArrayList<Integer> tiles;
    private static int GAME_HEIGHT, GAME_WIDTH;
    private ArrayList<Wall> walls, verticalWalls, horizontalWalls, brokenWalls;

    public MapHandler(){
        mapsDirectory = new File("maps");
        GAME_HEIGHT = 360;
        GAME_WIDTH = 16 * GAME_HEIGHT / 9;
        tiles = new ArrayList<>();
        walls = new ArrayList<>();
        verticalWalls = new ArrayList<>();
        horizontalWalls = new ArrayList<>();
        brokenWalls = new ArrayList<>();
        readMap();
        createWalls();
    }

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
        content = content.replaceAll("\r\n", "");
        System.out.println(content);
        height = content.length() / width;
        System.out.println("width:" + width + " height:" + height);

        //make an array list of integers for rooms :
        for (int i = 0; i < content.length(); i++) {
            tiles.add(Integer.parseInt(String.valueOf(content.charAt(i))));
        }
    }

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
                                Wall wall = new Wall(x, y, x, y + a, "smp", "ver");
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
    public int getGameWidth(){
        return GAME_WIDTH;
    }

    public int getGameHeight(){
        return GAME_HEIGHT;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public ArrayList<Integer> getTiles() {
        return tiles;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }
//    public boolean checkWalls(double x, double y) {
//        for (Wall wall : walls) {
//            if (wall.getLineType().equals("hor")) {
//                if (x >= wall.getX1() && x <= wall.getX2()) {
//                    if (Math.abs(y - wall.getY1()) < 10) {
//                        return false;
//                    }
//                }
//            }
//            if (wall.getLineType().equals("ver")) {
//                if (y >= wall.getY1() && y <= wall.getY2()) {
//                    if (Math.abs(x - wall.getX1()) < 10) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }

    public void hitBrkWall(Wall wallToBreak) {
    walls.remove(wallToBreak);
    if (wallToBreak.getLineType().equals("hor")) {
        horizontalWalls.remove(wallToBreak);
    }
    if (wallToBreak.getLineType().equals("ver")) {
        verticalWalls.remove(wallToBreak);
    }
}

    public ArrayList<Wall> getVerticalWalls() {
        return verticalWalls;
    }

    public ArrayList<Wall> getHorizontalWalls() {
        return horizontalWalls;
    }
}
