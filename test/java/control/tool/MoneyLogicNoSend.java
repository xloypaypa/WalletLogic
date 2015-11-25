package control.tool;

import control.MoneyLogic;
import control.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/25.
 * it's the money logic use event who not send message
 */
public class MoneyLogicNoSend extends MoneyLogic {

    protected EventCounter eventCounter;

    public MoneyLogicNoSend(Socket socket) {
        super(socket);
        this.eventCounter = new EventCounter();
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
        return new NoSendEvent(socket, eventCounter);
    }
}
