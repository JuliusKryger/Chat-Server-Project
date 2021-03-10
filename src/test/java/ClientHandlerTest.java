import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

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