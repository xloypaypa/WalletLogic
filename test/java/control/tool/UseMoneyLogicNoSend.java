package control.tool;

import control.SendEvent;
import control.UseMoneyLogic;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/11.
 * it's the use money logic use event not send message
 */
public class UseMoneyLogicNoSend extends UseMoneyLogic implements NeedWaitEvent {

    protected EventCounter eventCounter;

    public UseMoneyLogicNoSend(Socket socket) {
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
