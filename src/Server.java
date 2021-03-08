import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int port = 9090;
    private static boolean running = true;

    private static List<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {
        //client connection
        ServerSocket clientSocket= new ServerSocket(port);

        while (running) {
            //client connection
            System.out.println("Waiting for client to connect");
            Socket client = clientSocket.accept();
            System.out.println("Server connected to client");
            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);
            threadPool.execute(clientThread);
        }


    }
}

