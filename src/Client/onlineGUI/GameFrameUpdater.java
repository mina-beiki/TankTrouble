package Client.onlineGUI;

import GameServer.Game;
import GameServer.Information;
import GameServer.Tank;
import GameServer.Wall;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles updating of game frame for online game .
 */
public class GameFrameUpdater implements Runnable {
    private Socket socket;
    private BufferedReader reader;


    public GameFrameUpdater(Socket socket){
        this.socket = socket;
    }


    /**
     * Runs the GameFrame .
     */
    @Override
    public void run() {
//        out = new ObjectOutputStream((socket.getOutputStream()));
//        in = new ObjectInputStream((socket.getInputStream()));
//        writer = new PrintWriter((socket.getOutputStream()),true);
        GameFrame.getInstance().initBufferStrategy();
        try {
            while (true){
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = reader.readLine();
                System.out.println(str+"\n\n\n\n");
                Information info = new Gson().fromJson(str,Information.class);
//                String str ;
//                String control = reader.readLine();
//                str = reader.readLine();
//                ArrayList<Tank> tanks = new Gson().fromJson(str, new TypeToken<ArrayList<Tank>>() {}.getType());
//                System.out.println(tanks);
//                str = reader.readLine();
//                ArrayList<Wall> walls = new Gson().fromJson(str,new TypeToken<ArrayList<Wall>>(){}.getType());
//                System.out.println(walls);
//                str = reader.readLine();
//                ArrayList<Integer> tiles = new Gson().fromJson(str,new TypeToken<ArrayList<Integer>>(){}.getType());
//                System.out.println(tiles);
//                str = reader.readLine();
//                int width = new Gson().fromJson(str,Integer.class );
//                System.out.println(width);
//                str = reader.readLine();
//                int height = new Gson().fromJson(str,Integer.class );
//                System.out.println(height);
                GameFrame.getInstance().update(info.getTankss(),info.getWalls(),info.getTiles(),info.getWidth(),info.getHeight());
                GameFrame.getInstance().render();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
