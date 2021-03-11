import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ClientHandlerTest {

    //Please ignore this test is actually useless.

    Server server;
    ClientHandler clientHandler;
    BlockingQueue<String> allMessages;
    ServerSocket serverSocket;
    Thread testThread;
    int port = 5555;

    @BeforeEach
    void setUp() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        allMessages = new ArrayBlockingQueue<>(200);
        String SimulatedUserInput = "Lorem Ipsum dolor sit amet, consectetur adipiscing elit.";
        allMessages.add(SimulatedUserInput);
        //PrintWriter pw = new PrintWriter(System.out);
        //BufferedReader br = new BufferedReader(new StringReader(SimulatedUserInput));
        clientHandler = new ClientHandler(socket, server, allMessages);
        testThread = new Thread(clientHandler);
    }

    @Test
    void testClientSendMsg() throws InterruptedException {
    testThread.start();
    testThread.join();
        System.out.println(allMessages);
    }
}