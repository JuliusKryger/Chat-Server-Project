import java.io.*;
import java.net.Socket;

public class Client  {

    private static String serverIP = "127.0.0.1";
    private static int port = 9090;
    static boolean running = true;

    public static void main(String[] args) throws IOException {
        //grants access to the server
        Socket accessSocket = new Socket(serverIP, port);

        //To read and write to the server
        BufferedReader input = new BufferedReader(new InputStreamReader(accessSocket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter toServer = new PrintWriter(accessSocket.getOutputStream(), true);

        while (running){
            System.out.println("Welcome to our virtual server");
            System.out.println("type 'stop' to end connection");
            System.out.println("----------");
            String readFromKeyboard = keyboard.readLine();

            if (readFromKeyboard.equals("stop")) break;

            toServer.println(readFromKeyboard);

            String serverResponse = input.readLine();
            System.out.println("FROM SERVER :" + serverResponse);

        }


        accessSocket.close();
        System.exit(0);

    }
}
