
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    boolean running = true;

    public ClientHandler (Socket client) throws IOException {
        this.client = client;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(),true);

    }


    @Override
    public void run() {

        try {
            while (running) {
                String requestFromClient = fromClient.readLine();
                if (requestFromClient.contains("1")) {
                    toClient.println("1");
                } else {
                    toClient.println(" type 1 to get 1" );
                }
            }
        } catch (IOException e){
            System.out.println("IO exception in clientHandler class ");
            e.printStackTrace();
        } finally {
            System.out.println("Connection closed");
            toClient.close();
            try {
                fromClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
