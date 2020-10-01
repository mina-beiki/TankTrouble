package GameServer;

import Host.logic.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandler implements Runnable{
    private PrintWriter writer;
    private Socket socket;
    private BufferedReader reader;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<Game> games;
    private Game game;
    private User user;

    public SocketHandler(Socket socket, ArrayList<Game> games) throws IOException {
        this.socket = socket;
        this.games = games;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter((socket.getOutputStream()),true);
        PrintWriter dos = new PrintWriter(socket.getOutputStream(), true);
        dos.flush();
        in = new ObjectInputStream((socket.getInputStream()));
        out = new ObjectOutputStream((socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = reader.readLine();
                if (str.equals("connect")) {
//                        System.out.println("connected");
                    user = new Gson().fromJson(reader.readLine(),User.class);
                    writer.println(games.size());
                    for (Game game : games) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("name",game.getName());
                        jsonObject.addProperty("mode",game.getMode());
                        jsonObject.addProperty("endMode",game.getEndingMode());
                        jsonObject.addProperty("minPlayers",game.getMinPlayers());
                        jsonObject.addProperty("currentPlayer",game.getCurrentPlayer());
                        writer.println(new Gson().toJson(jsonObject));
                    }
                }else if(str.equals("Make new game")){
                    JsonObject jsonObject = new Gson().fromJson(reader.readLine(),JsonObject.class);
                    Game game = new Game(jsonObject.get("name").getAsString(),jsonObject.get("mode").getAsString(),jsonObject.get("endMode").getAsString(),
                            jsonObject.get("minPlayers").getAsInt(),user.getSettings().getTankLives(),user.getSettings().getBulletsPower()
                            ,user.getSettings().getBrkWallsLives());
                    games.add(game);
                    writer.println(games.size());
                    for (Game game1 : games) {
                        System.out.println(game1);
                        JsonObject jsonObject2 = new JsonObject();
                        jsonObject2.addProperty("name",game1.getName());
                        jsonObject2.addProperty("mode",game1.getMode());
                        jsonObject2.addProperty("endMode",game1.getEndingMode());
                        jsonObject2.addProperty("minPlayers",game1.getMinPlayers());
                        jsonObject2.addProperty("currentPlayer",game1.getCurrentPlayer());
                        writer.println(new Gson().toJson(jsonObject2));
                    }
                }else if(str.equals("im in")){
                    user = new Gson().fromJson(reader.readLine(),User.class);
                    String name = reader.readLine();
                    for(Game game:games){
                        if(game.getName().equals(name)){
                            this.game = game;
                            game.getQueueHandler().addToQueue(this);
                            game.addUser(user);
                            game.updateCurrentPlayer();
                            game.getQueueHandler().addToNotify(socket);
                            game.getQueueHandler().checkQueue(game);
                        }
                    }
                }else if(str.equals("next move")){
                    JsonObject jsonObject = new Gson().fromJson(reader.readLine(),JsonObject.class);
                    game.updateMoves(user,jsonObject);
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }
}
