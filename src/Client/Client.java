package Client;


import Client.gui.GamePanel;
import Client.gui.LoginWindow;
import Client.gui.MainWindow;
import Client.onlineGUI.GameFrame;
import Client.onlineGUI.UserState;
import Host.logic.Settings;
import Host.logic.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Connects to server and receives data such as login and sign up for users
 * and making games for server games .
 */
public class Client {
    private Socket socket;
    private BufferedReader reader;
    private ObjectInputStream in;
    private PrintWriter out;
    private User user;
    private static Client client = new Client();
    private ObjectOutputStream writer;

    private Client(){}

    /**
     * Connects client to the server .
     * @param username String , user name
     * @param pass String , password
     * @param method String , method
     */
    public void connectServer(String username,String pass,String method){
        try {
            //5050
            socket = new Socket("localhost",5050);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new ObjectOutputStream((socket.getOutputStream()));
            in = new ObjectInputStream((socket.getInputStream()));
            out = new PrintWriter((socket.getOutputStream()),true);

            out.println(method+"\n"+username+"\n"+pass);
            user = (User) in.readObject();

            JFrame alert = new JFrame();

            if(user == null){
                if(method.equals("signup")){
                    JOptionPane.showMessageDialog(alert,"This username already exists.\nPlease choose another username.","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(alert,"Username or password is incorrect.\nPlease try again.","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
                }
            }else {
                LoginWindow.getInstance().hideWindow();
                MainWindow.getInstance().showWindow();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Changes and Updates a user settings by sending the new ones .
     * @param settings Settings , new Settings to be sent to server
     */
    public void changeUserSettings(Settings settings){
        out.println("Change settings");
        try {
            writer.writeObject(settings);
            Settings settings1 = (Settings) in.readObject();
            user.setSettings(settings1);
            System.out.println(user.getSettings().getUrls());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Connects the client to game server with the given ip and port number .
     * @param ip String , ip
     * @param port int , port
     * @return  ArrayList<JsonObject> , games
     */
    public ArrayList<JsonObject> ConnectToGameServer(String ip, int port){
        ArrayList<JsonObject> games = new ArrayList<JsonObject>();
        try {
            socket = new Socket(ip,port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new ObjectOutputStream((socket.getOutputStream()));
            in = new ObjectInputStream((socket.getInputStream()));
            out = new PrintWriter((socket.getOutputStream()),true);

            out.println("connect");
            out.println(new Gson().toJson(user));
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                JsonObject jsonObject1 = new Gson().fromJson(reader.readLine(),JsonObject.class);
                games.add(jsonObject1);
            }

        } catch (IOException  e) {
            e.printStackTrace();
        }
        return games;
    }

    /**
     * Makes a new game in server by using users given data .
     * @param jsonObject JsonObject
     * @return ArrayList<JsonObject> , games
     * @throws IOException
     */
    public ArrayList<JsonObject> makeGame(JsonObject jsonObject) throws IOException {
        ArrayList<JsonObject> games = new ArrayList<JsonObject>();
        out.println("Make new game");
        out.println(new Gson().toJson(jsonObject));
        int n = 0;
        n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) {
            JsonObject jsonObject1 = new Gson().fromJson(reader.readLine(),JsonObject.class);
            games.add(jsonObject1);
        }
        return games;
    }

    /**
     * Joins a prepared game in server .
     * @param name String , name of the game
     * @param frame JFrame , GameFrame of the game
     */
    public void joinGame(String name,JFrame frame){
        out.println("im in");
        out.println(new Gson().toJson(user));
        out.println(name);
        try {
            while (true) {
                String str = reader.readLine();
                if (str.equals("game started")) {
                    frame.dispose();
                    GameFrame.getInstance().setVisible(true);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserState userState = new UserState(socket);
        GameFrame.getInstance().addKeyListener(userState.getKeyListener());

    }

    /**
     * Gets the socket .
     * @return Socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Used for singleton implementation , gets an instance of Client .
     * @return Client
     */
    public static Client getInstance() {
        return client;
    }

    /**
     * Gets user .
     * @return User
     */
    public User getUser() {
        return user;
    }


}

