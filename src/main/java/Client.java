import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private Scanner scanner;

    public void startConnection(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            scanner = new Scanner(System.in);

            System.out.println("Client connected to socket");

            while (!clientSocket.isOutputShutdown()) {
                String input = in.readUTF();
                System.out.println("Server: " + input);
                System.out.println("You: ");
                String inputString = sendMessage();

                if (inputString != null) {
                    out.writeUTF(inputString);
                    out.flush();

                    if (inputString.equalsIgnoreCase("quit")) {
                        System.out.println("You've just left the chat");
                        break;
                    }
                }
            }
            stopConnection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String sendMessage() throws IOException {
        return scanner.nextLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Closing connections & channels on clentSide - DONE.");
    }

    public static void main(String[] args) {
        String host = "netology.homework";
        int port = 8080;
        Client clientServer = new Client();
        clientServer.startConnection(host, port);
    }
}