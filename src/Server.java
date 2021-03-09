import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static int port = 9090;
    ConcurrentHashMap<Integer, ClientHandler> allClientHandlers;

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid port number, using default port :" + port);
            }
        }
        new Server().startServer(port);
    }

    private void startServer(int port) throws IOException {
        ServerSocket serverSocket;
        allClientHandlers = new ConcurrentHashMap<>();
        serverSocket = new ServerSocket(port);
        System.out.println("Server is starting ...");
        System.out.println("Now listening on port : " + port);

        while (true) {
            System.out.println("Waiting for a client");
            Socket socket = serverSocket.accept();
            System.out.println("A new client has just connected, client ID is " + ClientHandler.getId());
            ClientHandler clientHandler = new ClientHandler(socket, this);
            allClientHandlers.put(clientHandler.getId(), clientHandler);
            new Thread(clientHandler).start();
        }
    }
}















/*
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
 */

