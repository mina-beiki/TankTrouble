package GameServer;

import Host.logic.User;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles all important elements of an online game . Makes a new game and
 * sends the updated one to clients playing it . Also handles the movements of all
 * clients' tanks and saves it .
 */
public class Game implements Serializable {
    private String name,mode,endingMode;
    private int minPlayers,currentPlayer,tankLive,bulletPower,brkWallsLives;
    private ArrayList<User> users;
    private QueueHandler queueHandler;
    private HashMap<User,Tank> tanks;
    private MapHandler mapHandler;
    private HashMap<User, JsonObject> newMoves;

    /**
     * Generates a new game with the given elements .
     * @param name String , game's name
     * @param mode String , game's mode ( league or death match )
     * @param endingMode String , ending mode
     * @param minPlayers int , min numbers of players
     * @param tankLive int , tank lives
     * @param bulletPower int , power of bullets
     * @param brkWallsLives int , lives of breakable walls
     */
    public Game(String name,String mode,String endingMode,int minPlayers,int tankLive,int bulletPower,int brkWallsLives){
        this.name = name;
        this.mode = mode;
        this.endingMode = endingMode;
        this.minPlayers = minPlayers;
        this.tankLive = tankLive;
        this.bulletPower = bulletPower;
        this.brkWallsLives = brkWallsLives;
        users = new ArrayList<User>();
        currentPlayer = 0;
        queueHandler = new QueueHandler();
        tanks = new HashMap<User, Tank>();
        newMoves = new HashMap<User,JsonObject>();
    }

    /**
     * initializes a game .
     */
    public void init(){
        mapHandler = new MapHandler();
        for(User user:users){
            Tank tank = new Tank(user.getSettings().getTank(),mapHandler);
            tanks.put(user,tank);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("up",false);
            jsonObject.addProperty("down",false);
            jsonObject.addProperty("left",false);
            jsonObject.addProperty("right",false);
            jsonObject.addProperty("space",false);
            newMoves.put(user,jsonObject);
        }

    }

    /**
     * Updates the game and stores the new movements made by clients .
     */
    public void update(){

        for(User user: tanks.keySet()){
            JsonObject jsonObject = newMoves.get(user);
            if (jsonObject.get("up").getAsBoolean()){
                tanks.get(user).moveTank("up");
            }
            if (jsonObject.get("down").getAsBoolean()){
                tanks.get(user).moveTank("down");
            }
            if (jsonObject.get("left").getAsBoolean()){
                tanks.get(user).moveTank("left");
            }
            if (jsonObject.get("right").getAsBoolean()){
                tanks.get(user).moveTank("right");
            }
            if (jsonObject.get("space").getAsBoolean()){
                if (tanks.get(user).checkTwoBullets()){
                    tanks.get(user).newBullet();
                }
            }
        }
    }

    /**
     * updates the new moves made by clients .
     * @param user User , user which has moved
     * @param jsonObject JsonObject , movements
     */
    public synchronized void updateMoves(User user,JsonObject jsonObject) {
        newMoves.replace(user,jsonObject);
    }

    /**
     * adds a new user to game .
     * @param user User , user
     */
    public void addUser(User user){
        users.add(user);
    }

    /**
     * Gets the queue handler of the game .
     * @return QueueHandler
     */
    public QueueHandler getQueueHandler() {
        return queueHandler;
    }

    /**
     * increases the number of players .
     */
    public void updateCurrentPlayer(){
        currentPlayer++;
    }

    /**
     * Gets the name of the game .
     * @return string , name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the mode of the game .
     * @return String , mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Gets ending mode .
     * @return String , ending mode
     */
    public String getEndingMode() {
        return endingMode;
    }

    /**
     * Gets minimum players .
     * @return int , min players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Gets current players' number .
     * @return int , number
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets all tanks involved in the game .
     * @return ArrayList<Tank> , tanks
     */
    public ArrayList<Tank> getTanks() {
        ArrayList<Tank> tankArrayList = new ArrayList<Tank>();
        for(User user:tanks.keySet()){
            tankArrayList.add(tanks.get(user));
        }
        return tankArrayList;
    }

    /**
     * Gets wall walls of map .
     * @return ArrayList<Wall> , walls
     */
    public ArrayList<Wall> getWalls() {
        return mapHandler.getWalls();
    }

    /**
     * gets width of the map.
     * @return int , width
     */
    public int getWidth(){
        return mapHandler.getWidth();
    }

    /**
     * gets height of the map.
     * @return int , height
     */
    public int getHeight(){
        return mapHandler.getHeight();
    }

    /**
     * Gets the tiles of the game .
     * @return ArrayList<Integer> , tiles
     */
    public ArrayList<Integer> getTiles(){
        return mapHandler.getTiles();
    }

}
