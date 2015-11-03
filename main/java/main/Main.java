package main;

import net.PackageServer;
import net.server.Server;

/**
 * Created by xlo on 15-11-1.
 * it's the main class
 */
public class Main {
    public static void main(String[] args) {
        Server server = Server.getNewServer("server", PackageServer::new);
        server.getInstance(8080, 1);
        server.accept();
    }
}
