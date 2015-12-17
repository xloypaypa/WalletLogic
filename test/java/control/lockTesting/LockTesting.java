package control.lockTesting;

import control.LogicTesting;
import control.tool.EventCounter;
import control.tool.NoSendEvent;
import model.db.MoneyCollection;
import model.db.UserCollection;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by xlo on 15/12/16.
 * it's the lock testing
 */
public class LockTesting extends LogicTesting {

    @Test
    public void test() throws InterruptedException {

        EventCounter eventCounter = new EventCounter();

        for (int i = 0; i < 20; i++) {
            eventCounter.addEvent();
            if (i % 2 == 0) {
                new Thread() {
                    @Override
                    public void run() {
                        NoSendEvent noSendEvent = new NoSendEvent(socket, eventCounter);
                        noSendEvent.setEventAction(() -> {
                            new MoneyCollection().lockCollection();
                            new UserCollection().lockCollection();
                            return true;
                        });
                        noSendEvent.call();
                    }
                }.start();
            } else if (i % 2 == 1) {
                new Thread() {
                    @Override
                    public void run() {
                        NoSendEvent noSendEvent = new NoSendEvent(socket, eventCounter);
                        noSendEvent.setEventAction(() -> {
                            new UserCollection().lockCollection();
                            new MoneyCollection().lockCollection();
                            return true;
                        });
                        noSendEvent.call();
                    }
                }.start();
            }
        }

        while (!eventCounter.isEnd()) {
            Thread.sleep(500);
        }

        assertEquals(20, eventCounter.getOk());
    }

}
