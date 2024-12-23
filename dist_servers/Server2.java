import java.io.*;
import java.net.*;

public class Server2 {
    public static void main(String[] args) throws IOException {
        int port = 5002;
        int plotterPort = 6000;

        
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server2 " + port + " portunda çalışıyor.");

       
        Socket plotterSocket = new Socket("localhost", plotterPort);
        PrintWriter plotterOut = new PrintWriter(plotterSocket.getOutputStream(), true);

       
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Bağlantı kabul edildi: " + clientSocket.getRemoteSocketAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String command = in.readLine();
                System.out.println("Gelen komut: " + command);

                if ("STRT 1000".equals(command)) {
                    
                    Message responseMessage = new Message("STRT", "YEP");
                    out.println(responseMessage);
                    System.out.println("Yanıt gönderildi: " + responseMessage);
                } else if ("CPCTY".equals(command)) {
                
                    long currentTimestamp = System.currentTimeMillis() / 1000;
                    Capacity capacityResponse = new Capacity("CPCTY", "1000", currentTimestamp);
                    out.println(capacityResponse);
                    System.out.println("Kapasite yanıtı gönderildi: " + capacityResponse);

                
                    plotterOut.println(capacityResponse);
                    System.out.println("Kapasite verisi plotter'a gönderildi: " + capacityResponse);
                } else {
                    Message responseMessage = new Message(command, "NOP");
                    out.println(responseMessage);
                    System.out.println("Yanıt gönderildi: " + responseMessage);
                }
            }
        }
    }
}
