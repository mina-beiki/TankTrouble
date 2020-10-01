package GameServer;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private Game game;
    private HashMap<Socket,PrintWriter> usersSocket;
    private int round;

    public GameLoop(Game game, ArrayList<Socket> sockets) {
        round = 0;
        usersSocket = new HashMap<>();
        for(Socket socket:sockets){

            PrintWriter writer = null;
            try {
                writer = new PrintWriter((socket.getOutputStream()), true);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            usersSocket.put(socket,writer);
//            ObjectOutputStream out = null;
//            try {
//                out = new ObjectOutputStream((socket.getOutputStream()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            usersSocket.put(socket,out);

        }
        this.game = game;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        // Perform all initializations ...
        game.init();

    }

    /**
     * Runs the loop for game .
     */
    @Override
    public void run() {
        boolean gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                game.update();
                for(Socket socket: usersSocket.keySet()){
                    Information info = new Information(game.getTanks(),game.getWalls(),game.getTiles(),game.getWidth(),game.getHeight());
                    String gson = new Gson().toJson(info);
//                    System.out.println(gson);
                    usersSocket.get(socket).println(gson);

                }

                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
    }

}
