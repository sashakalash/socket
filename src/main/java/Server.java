import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    String name;

    public static void main(String[] args) {
        Server server = new Server();
        server.startConnection(8080);
    }

    public void startConnection(int port) {
        try {
            serverSocket= new ServerSocket(port);
            clientSocket = serverSocket.accept();
            System.out.println("Connection accepted");

            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());

            out.writeUTF("What's your name?");
            name = in.readUTF();

            while (!clientSocket.isClosed()) {
                out.writeUTF("Are you child? (yes/no)");
                String entry = in.readUTF();
                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client has left the chat");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    out.flush();
                    break;
                } else if (entry.equalsIgnoreCase("yes")) {
                    out.writeUTF("Welcome to the kids area, " + name + "! Let's play!");
                } else if (entry.equalsIgnoreCase("no")) {
                    out.writeUTF("Welcome to the adult zone, " + name + "! Have a good rest, or a good working day!");
                } else {
                    out.writeUTF("I didn't get you, " + name + ". Try again!");
                }
                out.flush();
            }
            stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stopConnection() throws IOException {
        System.out.println("Closing connections & channels");
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}