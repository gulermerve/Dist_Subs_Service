import java.io.*;
import java.net.*;
import java.util.*;

public class Server1 {
    private static Map<Integer, Subscriber> subscribers = new HashMap<>();  

    public static void main(String[] args) throws IOException {
        int port = 5001;
        int plotterPort = 6000;

       
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server1 " + port + " portunda çalışıyor.");

        Socket plotterSocket = new Socket("localhost", plotterPort);
        PrintWriter plotterOut = new PrintWriter(plotterSocket.getOutputStream(), true);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Bağlantı kabul edildi: " + clientSocket.getRemoteSocketAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String command = in.readLine();
                System.out.println("Gelen komut: " + command);

               
                if (command.startsWith("SUBS")) {
                    String[] parts = command.split(",");
                    int id = Integer.parseInt(parts[1].split(":")[1].trim());
                    String nameSurname = parts[2].split(":")[1].trim().replace("\"", "");
                    long startDate = Long.parseLong(parts[3].split(":")[1].trim());
                    long lastAccessed = Long.parseLong(parts[4].split(":")[1].trim());
                    List<String> interests = Arrays.asList(parts[5].split(":")[1].trim().replace("[", "").replace("]", "").split(","));
                    boolean isOnline = Boolean.parseBoolean(parts[6].split(":")[1].trim());

                    Subscriber newSubscriber = new Subscriber(id, nameSurname, startDate, lastAccessed, interests, isOnline);
                    subscribers.put(id, newSubscriber);
                    out.println("Subscriber added: " + newSubscriber);
                    System.out.println("Yeni abone eklendi: " + newSubscriber);
                }
                
                else if (command.startsWith("DEL")) {
                    String[] parts = command.split(",");
                    int id = Integer.parseInt(parts[1].split(":")[1].trim());

                    if (subscribers.containsKey(id)) {
                        Subscriber removedSubscriber = subscribers.remove(id);
                        out.println("Subscriber deleted: " + removedSubscriber);
                        System.out.println("Abone silindi: " + removedSubscriber);
                    } else {
                        out.println("No subscriber found with ID: " + id);
                        System.out.println("ID ile abone bulunamadı: " + id);
                    }
                }
               
                else {
                    out.println("Unknown command");
                    System.out.println("Bilinmeyen komut alındı.");
                }
            }
        }
    }
}
