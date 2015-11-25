package control.tool;

import control.SendEvent;
import control.UserLogic;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/25.
 * it's the user logic use event who not send message
 */
public class UserLogicNoSend extends UserLogic {

    protected EventCounter eventCounter;

    public UserLogicNoSend(Socket socket) {
        super(socket);
    }

    public NoSendEvent getEvent() {
        return (NoSendEvent) this.event;
    }

    public void waitEventEnd() {
        while (!this.eventCounter.isEnd()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected SendEvent buildSendEvent(Socket socket) {
        this.eventCounter = new EventCounter();
        return new NoSendEvent(socket, eventCounter);
    }
}
