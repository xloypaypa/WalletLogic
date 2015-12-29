package control.tool;

import control.EdgeLogic;
import control.logic.event.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/26.
 * it's the edge logic use event not send message
 */
public class EdgeLogicNoSend extends EdgeLogic implements NeedWaitEvent {

    protected EventCounter eventCounter;

    public EdgeLogicNoSend(Socket socket) {
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
        return new NoSendNeedLoginEvent(socket, eventCounter, this.getClass());
    }

}
