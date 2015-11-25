package control.tool;

/**
 * Created by xlo on 2015/11/25.
 * it's the event counter
 */
public class EventCounter {
    protected int num;

    public EventCounter() {
        this.num = 0;
    }

    public synchronized void addEvent() {
        this.num++;
    }

    public synchronized void removeEvent() {
        this.num--;
    }

    public synchronized boolean isEnd() {
        return this.num == 0;
    }
}
