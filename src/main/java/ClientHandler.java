import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    BlockingQueue<String> messageQueue;
    private static int id = 0;
    Server server;
    String name;
    MsgDispatcher msgDispatcher;
    private int clientId;

    public ClientHandler(Socket client, Server server, BlockingQueue<String> messageQueue) throws IOException {
        id++;
        clientId = id;
        this.server = server;
        this.client = client;
        this.messageQueue = messageQueue;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(), true);
    }

    //This will be used later in our serverClass, as an unique identifier.
    public static int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            clientGreeting();
            protocol();
        } catch (IOException e) {
            System.err.println("There is a problem in run method");
            e.printStackTrace();
        }
    }

    public void protocol() throws IOException {
        toClient.println("What would you like to do?");
        toClient.println("Send a message to one person, everyone or see all online users?");
        toClient.println("For one person press 1: For everyone press A: To see all online users press U: ");
        toClient.println("Press e to exit");
        String msg = " ";
        String input = fromClient.readLine();
        while (!input.equals("E")) {
            switch (input) {
                case "1":
                    msgToOne();
                    break;
                case "A":
                    msgToAll();
                    break;
                case "U":
                    seeUsers();
                    break;
                case "e":
                    clientGoodBye();
            }
            toClient.println("Now what? 1/A/U/e");
            input = fromClient.readLine();
        }
    }

    public void msgToOne() throws IOException {
        toClient.println("Put in the name of the user you want to talk to: ");
        String clientInput = fromClient.readLine();
        ClientHandler clientHandler = (ClientHandler) server.getClientNameFromClientHandler(clientInput);
        PrintWriter newToClient = clientHandler.getToClient();
        if (newToClient != null) {
            toClient.println("What is your message:");
            //String msg = "SEND#Lone#Hej fra Kurt ";
            String msg = fromClient.readLine();
            msgDispatcher.messageToOneClient(msg, newToClient, this.name);
            msgDispatcher.messageQueue(msg);
        } else {
            toClient.println("The user does not exist or is not online ");
        }

        //der mangler noget mere så man ved hvor man skal sende den hen
        //Evt før hvad den spørger om hvad man vil sende
        //Skal finde en tråd med et bestemt navn og sige hvis der ikke er nogen tråde som hedder det.
    }

    public void msgToAll() throws IOException {
        toClient.println("What is your message");
        String msg = fromClient.readLine();
        messageQueue.add(msg);

        //msgDispatcher.messageToAll(client, toClient, server.allClientHandlers);
    }

    public void clientGreeting() throws IOException {
        toClient.println("What is your name");
        String name = fromClient.readLine();
        Thread.currentThread().setName(name);
        toClient.println("\nHello " + name);
        this.name = name;
        toClient.println("  ");
        toClient.println("Welcome to our virtual server");
        toClient.println("  ");
    }

    @Override
    public String toString() {
        //toClient.println("Following Users are online: ");
        return name;
    }

    public void seeUsers() {
        toClient.println("Following Users are online");
        toClient.println(server.allClientHandlers.values().toString());
    }

    public void clientGoodBye() throws IOException {
        toClient.println("Your connection is now terminated");
        Thread.currentThread().getName();
        client.close();
    }

    public PrintWriter getToClient() {
        return toClient;
    }

    public String getName() {
        return name;
    }

    public int getClientId() {
        return clientId;
    }
}

