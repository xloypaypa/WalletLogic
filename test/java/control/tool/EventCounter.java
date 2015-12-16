package control.tool;

/**
 * Created by xlo on 2015/11/25.
 * it's the event counter
 */
public class EventCounter {
    protected int num, ok;

    public EventCounter() {
        this.num = 0;
        this.ok = 0;
    }

    public synchronized void addEvent() {
        this.num++;
    }

    public synchronized void removeEvent() {
        this.num--;
    }

    public synchronized void addOk() {
        ok++;
    }

    public synchronized boolean isEnd() {
        return this.num == 0;
    }

    public synchronized int getOk() {
        return ok;
    }
}
