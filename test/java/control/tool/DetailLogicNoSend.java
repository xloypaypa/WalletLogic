package control.tool;

import control.DetailLogic;
import control.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/18.
 * it's the detail logic no send
 */
public class DetailLogicNoSend extends DetailLogic implements NeedWaitEvent {

    protected EventCounter eventCounter;

    public DetailLogicNoSend(Socket socket) {
        super(socket);
    }

    public NoSendEvent getEvent() {
        return (NoSendEvent) this.event;
    }

    public void waitEventEnd() {
        while (!this.eventCounter.isEnd()) {
            waitEvent();
        }
    }

    @Override
    protected SendEvent buildSendEvent(Socket socket) {
        this.eventCounter = new EventCounter();
        return new NoSendEvent(socket, eventCounter);
    }

}
