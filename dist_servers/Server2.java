import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) throws IOException {
        int port = 5002;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server2 running on port " + port);

        try (Socket connectionToServer1 = new Socket("localhost", 5001);
             Socket connectionToServer3 = new Socket("localhost", 5003)) {
            System.out.println("Connected to Server1 and Server3");
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

                if ("STRT 1000".equals(command)) {
                    
                    Message responseMessage = new Message("STRT", "YEP");
                    out.println(responseMessage);
                    System.out.println("Sent response: " + responseMessage);
                } else if ("CPCTY".equals(command)) {
                 
                    long currentTimestamp = System.currentTimeMillis() / 1000;
                    Capacity capacityResponse = new Capacity("CPCTY", "1000", currentTimestamp);
                    out.println(capacityResponse);
                    System.out.println("Sent capacity response: " + capacityResponse);
                } else {
                    Message responseMessage = new Message(command, "NOP");
                    out.println(responseMessage);
                    System.out.println("Sent response: " + responseMessage);
                }
            }
        }
    }
}
