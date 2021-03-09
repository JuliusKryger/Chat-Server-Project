
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class MsgDispatcher {

    BlockingQueue<PrintWriter> msgToAllClients;
    BlockingQueue<PrintWriter> msgToOneClient;
    BlockingQueue<String> message;

    public MsgDispatcher() {
        msgToAllClients = null;
        msgToOneClient = null;
        message = null;
    }

    public void messageQueue(String msg) {
        message.add(msg);
    }

    public void messageToAll(String msg) {

    }

    public void messageToOneClient(String msg) {

    }

}
