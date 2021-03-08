import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread implements Runnable  {
    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    boolean running = true;
    Scanner scanner = new Scanner(System.in);
    MsgDispatcher msgDispatcher;

    public ClientHandler (Socket client) throws IOException {
        this.client = client;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(),true);

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
        toClient.println("Send a message to one person or all?");
        toClient.println("For one person press 1, for all press A");
        toClient.println("Press E to exit");
        String input = fromClient.readLine();
        while (!input.equals("E")){
            switch (input){
                case "1":
                    msgToOne();
                    break;
                case "A":
                    msgToAll();
                    break;
                case "E":
                    clientGoodBye();
                    System.exit(1);
            }
            toClient.println("Now what? 1/A/E");
            input = fromClient.readLine();
        }
        
    }

    public void msgToOne() throws IOException {
        toClient.println("What is your message");
        String msg = fromClient.readLine();
        msgDispatcher.messageToOneClient(msg);
        msgDispatcher.messageQueue(msg);
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

    public void clientGreeting(){
        toClient.println("What is your name");
        String name = scanner.nextLine();
        Thread.currentThread().setName(name);
        toClient.println("Hello " + name);

    }

    public void clientGoodBye(){
        toClient.println("Goodbye ");
        Thread.currentThread().getName();
    }
}
