package Host.network;

import Host.logic.Settings;
import Host.logic.User;

import java.io.*;
import java.net.Socket;

/**
 * This Handler implements Runnable and is responsible for
 * handling login, sign up and changing user settings
 */
public class  ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private User user;

    /**
     * Construct a ClientHandler with given socket
     * @param socket server socket
     * @throws IOException
     */
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter dos = new PrintWriter(socket.getOutputStream(), true);
        dos.flush();
        in = new ObjectInputStream((socket.getInputStream()));
        out = new ObjectOutputStream((socket.getOutputStream()));
    }

    /**
     * This run method handle login, sign up and changing user settings
     */
    @Override
    public void run() {
            try {
                while (true) {
                    String str = reader.readLine();

                    boolean flag = false;

                    if (str.equals("login")) {
                        String username = reader.readLine();
                        String password = reader.readLine();
                        for (User user : DataBase.getSavedUsers()) {
                            if (user.getPassword().equals(password) && user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                                flag = true;
                                out.writeObject(user);
                                this.user = user;
                            }
                        }
                        if (!flag) {
                            out.writeObject(null);
                        }
                    } else if (str.equals("signup")) {
                        String username = reader.readLine();
                        String password = reader.readLine();
                        for (User user : DataBase.getSavedUsers()) {
                            if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                                out.writeObject(null);
                                flag = true;
                            }
                        }
                        if (!flag) {
                            DataBase.addUser(username, password);
                            for (User user : DataBase.getSavedUsers()) {
                                if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                                    out.writeObject(user);
                                    this.user = user;
                                }
                            }
                        }
                    } else if (str.equals("Change settings")) {
                        try {
                            System.out.println(DataBase.getArrayList());
                            DataBase.removeUserFromArray(user);
                            Settings settings = (Settings) in.readObject();
                            user.setSettings(settings);
                            DataBase.addUserToArray(user);
                            out.writeObject(settings);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

                } catch(IOException e){
                    e.printStackTrace();
                }

    }
}
