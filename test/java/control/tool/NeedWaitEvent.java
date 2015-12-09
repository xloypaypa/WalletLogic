package control.tool;

/**
 * Created by xlo on 2015/12/9.
 * it's the wait able
 */
public interface NeedWaitEvent {

    default void waitEvent() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
