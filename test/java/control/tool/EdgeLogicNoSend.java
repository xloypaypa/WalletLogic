package control.tool;

import control.EdgeLogic;
import control.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/26.
 * it's the edge logic use event not send message
 */
public class EdgeLogicNoSend extends EdgeLogic {

    protected EventCounter eventCounter;

    public EdgeLogicNoSend(Socket socket) {
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
