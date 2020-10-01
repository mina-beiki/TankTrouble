package Host.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main server of Game which is responsible for sign up and log in
 * and saving users information in a database
 */
public class MainServer extends Thread {
    private ServerSocket serverSocket;

    /**
     * Main server constructor
     * making new server socket with port 5050
     */
    public MainServer(){
        try {
            serverSocket = new ServerSocket(5050);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * accept a socket from client
     * start a client handler thread for every accepted socket
     */
    public void run(){
        try {
            while (true)
            {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

