package GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server for the game in online games .
 */
public class GameServer extends Thread{
    private ServerSocket serverSocket;
    private ArrayList<Game> games;
    private int port;

    /**
     * initializes the server .
     * @param port int , port number to be connected
     */
    public GameServer(int port){
        games = new ArrayList<Game>();
        this.port = port;
    }

    /**
     * runs the server .
     */
    public void run(){
        try {
            serverSocket = new ServerSocket(port);
            while (true)
            {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket,games)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
