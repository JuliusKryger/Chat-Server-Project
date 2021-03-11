import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    //Call server port with arguments like this: 8080
    public static int port = 5555;
    ConcurrentHashMap<Integer, ClientHandler> allClientHandlers;
    BlockingQueue<String> message = new ArrayBlockingQueue<>(200);

    public static void main(String[] args) throws IOException {
        try {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid port number, using default port :" + port);
        }
        new Server().startServer(port);
    }

    private void startServer(int port) throws IOException {
        ServerSocket serverSocket;
        allClientHandlers = new ConcurrentHashMap<>();
        MsgDispatcher msgDispatcher = new MsgDispatcher(message, allClientHandlers);
        msgDispatcher.start();
        serverSocket = new ServerSocket(port);
        System.out.println("Server is starting ...");
        System.out.println("Now listening on port : " + port);
        while (true) {
            System.out.println("Waiting for a client");
            Socket socket = serverSocket.accept();
            System.out.println("A new client has just connected, client ID is " + ClientHandler.getId());
            ClientHandler clientHandler = new ClientHandler(socket, this, message);
            int id = clientHandler.getClientId();
            allClientHandlers.put(id, clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public Object getClientNameFromClientHandler(String name) {
        for (ClientHandler clientHandler : allClientHandlers.values()) {
            if (clientHandler.getName().contains(name)) {
                return clientHandler;
            }
        }
        return allClientHandlers;
    }
}