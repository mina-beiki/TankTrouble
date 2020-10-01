package GameServer;

import java.util.ArrayList;

/**
 * Handles the game in server .
 */
public class GameHandler {
    private static GameHandler gameHandler = new GameHandler();

    private GameHandler(){}

    /**
     * Gets an instance of class .
     * @return
     */
    public static GameHandler getInstance(){
        return gameHandler;
    }

    /**
     * initializes the game .
     * @param players ArrayList<SocketHandler> , players
     */
    public void initialGame(ArrayList<SocketHandler> players){


    }
}
