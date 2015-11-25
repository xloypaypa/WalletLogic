package control.tool;

import control.SendEvent;
import control.SessionLogic;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/25.
 * it's the session logic use event who not send message
 */
public class SessionLogicNoSend extends SessionLogic {

    protected EventCounter eventCounter;

    public SessionLogicNoSend(Socket socket) {
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
