import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    //Call server port with arguments like this: 8080
    public static int port = 9090;
    ConcurrentHashMap<Integer, ClientHandler> allClientHandlers;

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
        MsgDispatcher msgDispatcher = new MsgDispatcher();
        allClientHandlers = new ConcurrentHashMap<>();
        serverSocket = new ServerSocket(port);
        System.out.println("Server is starting ...");
        System.out.println("Now listening on port : " + port);

        while (true) {
            System.out.println("Waiting for a client");
            Socket socket = serverSocket.accept();
            System.out.println("A new client has just connected, client ID is " + ClientHandler.getId());
            ClientHandler clientHandler = new ClientHandler(socket, this, msgDispatcher);
            allClientHandlers.put(clientHandler.getId(), clientHandler);
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

