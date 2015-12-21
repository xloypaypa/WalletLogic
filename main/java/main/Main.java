package main;

import model.db.event.Event;
import net.PackageServer;
import net.server.Server;

import java.util.concurrent.Executors;

/**
 * Created by xlo on 15-11-1.
 * it's the main class
 */
public class Main {
    public static void main(String[] args) {

        if (args.length == 2) {
            Server server = Server.getNewServer("server", PackageServer::new);
            Integer num = Integer.valueOf(args[1]);
            Event.executorService = Executors.newFixedThreadPool(num);
            server.getInstance(Integer.valueOf(args[0]), num);
            server.accept();
        } else {
            MainPage mainPage = new MainPage();
            mainPage.show();
        }
    }
}
