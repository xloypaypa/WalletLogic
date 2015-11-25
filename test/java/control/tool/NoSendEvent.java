package control.tool;

import control.SendEvent;
import javafx.util.Pair;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlo on 2015/11/25.
 * it's the event no send message
 */
public class NoSendEvent extends SendEvent {
    protected List<Pair<String, byte[]>> message;
    protected EventCounter eventCounter;

    public NoSendEvent(Socket socket, EventCounter eventCounter) {
        super(socket);
        this.message = new ArrayList<>();
        this.eventCounter = eventCounter;
    }

    public List<Pair<String, byte[]>> getMessage() {
        return message;
    }

    @Override
    protected void sendMessage(List<Pair<String, byte[]>> message) {
        this.message.addAll(message);
    }

    @Override
    protected void whenCommit() {
        super.whenCommit();
        this.eventCounter.removeEvent();
    }

    @Override
    public void submit() {
        this.eventCounter.addEvent();
        super.submit();
    }
}
