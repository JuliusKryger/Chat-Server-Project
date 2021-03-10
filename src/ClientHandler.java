import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    boolean running = true;
    MsgDispatcher msgDispatcher;
    private static int id = 0;
    Server server;
    String name;


    public PrintWriter getToClient() {
        return toClient;
    }

    public String getName() {
        return name;
    }

    public ClientHandler(Socket client, Server server, MsgDispatcher msgDispatcher) throws IOException {
        this.id++;
        this.server = server;
        this.client = client;
        this.msgDispatcher = msgDispatcher;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(),true);
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
        toClient.println("Send a message to one person, all or see online users?");
        toClient.println("For one person press 1, for all press A, for users press U");
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
            toClient.println("Now what? 1/A/E");
            input = fromClient.readLine();
        }

    }

    public void msgToOne() throws IOException {
        toClient.println("Put in the name of the user you want to talk to: ");
        String clientInput = fromClient.readLine();
        ClientHandler clientHandler = server.getClientNameFromClientHandler(clientInput);
        PrintWriter newToClient = clientHandler.getToClient();
        if (newToClient !=null){
            toClient.println("What is your message:");
            //String msg = "SEND#Lone#Hej fra Kurt ";
            String msg = fromClient.readLine();
            msgDispatcher.messageToOneClient(msg, newToClient, this.name);
            msgDispatcher.messageQueue(msg);
            //tror den tager alle nu??
            //server.allClientHandlers.values().forEach(clientHandler -> {
                //clientHandler.sendMsg(msg)};
        }else{
            toClient.println("The user does not exist or is not online ");
        }

        //der mangler noget mere så man ved hvor man skal sende den hen
        //Evt før hvad den spørger om hvad man vil sende
        //Skal finde en tråd med et bestemt navn og sige hvis der ikke er nogen tråde som hedder det.



    }

    public void msgToAll() throws IOException {
        toClient.println("What is your message");
        String msg = fromClient.readLine();
        msgDispatcher.messageToAll(msg);
        msgDispatcher.messageQueue(msg);
        //Den skal kunne sende ud til alle
    }

    public void clientGreeting() throws IOException {

        toClient.println("What is your name");
        String name = fromClient.readLine();
        Thread.currentThread().setName(name);

        System.out.println("Welcome to our virtual server");
        toClient.println("What is your name");
        //String name = fromClient.readLine();
        this.name = name;
        ///???

        toClient.println("Hello " + name);

    }

    public void seeUsers(){
        System.out.println(server.allClientHandlers.values());

    }

    public void clientGoodBye() throws IOException {
        toClient.println("Your connection is now terminated");
        Thread.currentThread().getName();
        client.close();
        
    }

    public void sendMsg(String msg){
        toClient.println(msg);
    }
}


