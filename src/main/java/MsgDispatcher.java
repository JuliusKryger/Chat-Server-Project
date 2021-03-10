
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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

    public void messageToAll(Socket client, PrintWriter toClient, ConcurrentHashMap allClients) throws IOException{
        toClient.println("What is your message");
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String msg = fromClient.readLine();
        toClient.println("MESSAGE#" + "*#" + msg);



    }

    public void messageToOneClient(String msg, PrintWriter client, String name) {
        //Command#User#hej
        client.println("MESSAGE#" + name + "#" + msg);
    }

}
