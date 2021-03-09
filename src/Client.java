import java.io.*;
import java.net.Socket;

public class Client extends Thread {

    public static void main(String[] args) throws IOException {
        //adress = IP-adressen, localhost for local connection. - and port is the port you connect through.
        new Client().connect("localhost", 9090);
    }

    public void connect(String address, int port) throws IOException {
        //grants access to the server
        Socket accessSocket = new Socket(address, port);

        //To read and write to the server
        BufferedReader input = new BufferedReader(new InputStreamReader(accessSocket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter toServer = new PrintWriter(accessSocket.getOutputStream(), true);
        boolean running = true;

<<<<<<< HEAD

        while (running) {

            System.out.println("Welcome to our virtual server");
            System.out.println("type 'stop' to end connection");
            System.out.println("----------");
=======
        while (running){
>>>>>>> main

            String readFromKeyboard = keyboard.readLine();
            toServer.println(readFromKeyboard);
            String serverResponse = input.readLine();
            System.out.println("FROM SERVER :" + serverResponse);


            accessSocket.close();
            System.exit(0);
        }

    }
}




