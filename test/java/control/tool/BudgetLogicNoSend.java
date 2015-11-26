package control.tool;

import control.BudgetLogic;
import control.SendEvent;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/26.
 * it's the budget logic use event who not send message
 */
public class BudgetLogicNoSend extends BudgetLogic {

    protected EventCounter eventCounter;

    public BudgetLogicNoSend(Socket socket) {
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
