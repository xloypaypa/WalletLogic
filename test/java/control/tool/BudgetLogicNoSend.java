package control.tool;

import control.BudgetLogic;
import control.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/26.
 * it's the budget logic use event who not send message
 */
public class BudgetLogicNoSend extends BudgetLogic implements NeedWaitEvent {

    protected EventCounter eventCounter;

    public BudgetLogicNoSend(Socket socket) {
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
