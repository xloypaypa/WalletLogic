package control;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/3.
 * it's the logic manager
 */
public abstract class LogicManager {
    protected SocketChannel socketChannel;

    public LogicManager(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
