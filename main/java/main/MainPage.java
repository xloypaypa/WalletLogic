package main;

import model.db.event.Event;
import net.PackageServer;
import net.server.Server;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

/**
 * Created by xlo on 2015/11/3.
 * it's the server page
 */
public class MainPage {
    private JPanel panel;
    private JButton startButton;
    private JTextField serverPort;
    private JLabel serverMessage;
    private JTextField num;

    public void show() {
        JFrame frame = new JFrame("MainPage");
        MainPage mainPage = new MainPage();
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainPage.update();
    }

    private void update() {
        try {
            this.serverMessage.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        this.panel = new JPanel();

        this.startButton = new JButton();
        this.startButton.addActionListener((actionEvent) -> new Thread() {
            @Override
            public void run() {
                startButton.setEnabled(false);
                serverPort.setEnabled(false);
                num.setEnabled(false);
                Server server = Server.getNewServer("server", PackageServer::new);
                Integer num = Integer.valueOf(MainPage.this.num.getText());
                Event.executorService = Executors.newFixedThreadPool(num);
                server.getInstance(Integer.valueOf(serverPort.getText()), num);
                server.accept();
            }
        }.start());
    }
}
