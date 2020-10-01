package Host;

import Host.network.MainServer;

/**
 * Host Main class that handles main server.
 */
public class HostMain {
    public static void main(String[] args) {
        MainServer server = new MainServer();
        server.start();
    }
}
