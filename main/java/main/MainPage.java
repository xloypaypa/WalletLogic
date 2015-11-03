package main;

import net.PackageServer;
import net.server.Server;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainPage");
        MainPage mainPage = new MainPage();
        frame.setContentPane(mainPage.panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
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
        this.startButton = new JButton();
        this.startButton.addActionListener((actionEvent) -> {
            Server server = Server.getNewServer("server", PackageServer::new);
            server.getInstance(Integer.valueOf(this.serverPort.getText()), Integer.valueOf(this.num.getText()));
            new Thread() {
                @Override
                public void run() {
                    startButton.setEnabled(false);
                    serverPort.setEnabled(false);
                    num.setEnabled(false);
                    server.accept();
                }
            }.start();
        });
    }
}
