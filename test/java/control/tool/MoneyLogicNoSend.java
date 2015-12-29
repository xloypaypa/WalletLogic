package control.tool;

import control.MoneyLogic;
import control.logic.event.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/25.
 * it's the money logic use event who not send message
 */
public class MoneyLogicNoSend extends MoneyLogic implements NeedWaitEvent {

    protected EventCounter eventCounter;

    public MoneyLogicNoSend(Socket socket) {
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
