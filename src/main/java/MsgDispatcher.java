import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MsgDispatcher extends Thread {

    BlockingQueue<PrintWriter> msgToAllClients;
    BlockingQueue<PrintWriter> msgToOneClient;
    BlockingQueue<String> message;
    ConcurrentHashMap<Integer, ClientHandler> clientHandlers;

    public MsgDispatcher(BlockingQueue<String> allMsg, ConcurrentHashMap<Integer, ClientHandler> clientHandlers) {
        msgToAllClients = null;
        msgToOneClient = null;
        this.message = allMsg;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        String msg = " ";
        try {
            msg = message.take();
            messageToAll(msg);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void messageQueue(String msg) {
        message.add(msg);
    }

    public void messageToAll(String msg) throws IOException {
        Set<Integer> set = clientHandlers.keySet();
        for (Integer i : set) {
            ClientHandler cl = clientHandlers.get(i);
            cl.getToClient().println(msg);
        }
    }

    public void messageToOneClient(String msg, PrintWriter client, String name) {
        //Command#User#hej
        client.println("MESSAGE#" + name + "#" + msg);
    }
}