
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MsgDispatcher {

    BlockingQueue<PrintWriter> msgToAllClients;
    BlockingQueue<PrintWriter> msgToOneClient;
    BlockingQueue<String> message;
    Server server;

    public MsgDispatcher() {
        msgToAllClients = null;
        msgToOneClient = null;
        message = new ArrayBlockingQueue<>(200);
    }

    public void messageQueue(String msg) {
        message.add(msg);
    }

    public void messageToAll(String msg, PrintWriter toClient, BufferedReader fromClient) throws IOException {
        toClient.println("What is your message");
        msg = fromClient.readLine();
        for (ClientHandler clientHandler : server.allClientHandlers.values()) {
            clientHandler.msgToAll();
        }
        messageQueue(msg);

    }

    public void messageToOneClient(String msg, PrintWriter client, String name) {
        //Command#User#hej
        client.println("MESSAGE#" + name + "#" + msg);
    }

}
