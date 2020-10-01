package GameServer;

/**
 * Main class for game server .
 */
public class GameServerMain {
    public static void main(String[] args) {
        //8080
        GameServer gameServer = new GameServer(1111);
        gameServer.start();
    }
}
