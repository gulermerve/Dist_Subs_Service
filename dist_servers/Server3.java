import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
    public static void main(String[] args) throws IOException {
        int port = 5003;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server3 running on port " + port);

        try (Socket connectionToServer1 = new Socket("localhost", 5001);
             Socket connectionToServer2 = new Socket("localhost", 5002)) {
            System.out.println("Connected to Server1 and Server2");
        } catch (IOException e) {
            System.err.println("Error connecting to servers: " + e.getMessage());
        }

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from: " + clientSocket.getRemoteSocketAddress());

        
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String command = in.readLine();
                System.out.println("Received command: " + command);

                
                Message responseMessage;
                if ("STRT 1000".equals(command)) { 
                    responseMessage = new Message("STRT", "YEP");
                } else {
                    responseMessage = new Message("STRT", "NOP");
                }

                out.println(responseMessage);
                System.out.println("Sent response: " + responseMessage);
            }
        }
    }
}
