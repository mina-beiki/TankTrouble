package GameServer;

import Client.logic.ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class QueueHandler {

    private Queue<SocketHandler> queueList;
    private ArrayList<Socket> notifySockets;
    private ArrayList<SocketHandler> players;

    public QueueHandler() {
        queueList = new LinkedList<>();
        notifySockets = new ArrayList<>(0);
        players = new ArrayList<>();
    }

    public Queue<SocketHandler> getQueueList() {
        return queueList;
    }

    public SocketHandler removeFromQueue() {
        return queueList.remove();
    }

    public void addToQueue(SocketHandler socketHandler) {
        queueList.add(socketHandler);

    }

    public boolean checkQueue(Game game) {
        int min = game.getMinPlayers();
        if (queueList.size() > min-1) {
            for (int i = 0; i < min ; i++) {
                SocketHandler player = queueList.remove();
                players.add(player);
            }

            // start sending game information to client and recieving client info
            GameLoop gameLoop = new GameLoop(game,notifySockets);
            notifyUsers();
            gameLoop.init();
//            ThreadPool.execute(gameLoop);
            Thread thread = new Thread(gameLoop);
            thread.start();
            return true;
        } else {
            return false;
        }
    }

    public void addToNotify(Socket socket) {
        notifySockets.add(socket);
    }

    public void notifyUsers() {
        try {
            for (Socket socket : notifySockets) {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("game started");

            }
            notifySockets.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
