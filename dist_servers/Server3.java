import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server3 {
    private static final Map<Integer, Subscriber> subscribers = new ConcurrentHashMap<>();
    private static final int PORT = 5003;
    private static final int PLOTTER_PORT = 6000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server3 " + PORT + " portunda çalışıyor.");

        Socket plotterSocket = new Socket("localhost", PLOTTER_PORT);
        PrintWriter plotterOut = new PrintWriter(plotterSocket.getOutputStream(), true);

        List<Socket> otherServers = new ArrayList<>();
        try {
            otherServers.add(new Socket("localhost", 5001));
            otherServers.add(new Socket("localhost", 5002));
        } catch (IOException e) {
            System.err.println("Diğer sunuculara bağlanırken hata oluştu: " + e.getMessage());
        }

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Bağlantı kabul edildi: " + clientSocket.getRemoteSocketAddress());

            new Thread(() -> handleClient(clientSocket, plotterOut)).start();
        }
    }

    private static void handleClient(Socket clientSocket, PrintWriter plotterOut) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String command = in.readLine();
            System.out.println("Gelen komut: " + command);

            if (command.startsWith("STRT")) {
                out.println("YEP");
                System.out.println("Başlatma komutuna yanıt verildi.");
            } else if (command.startsWith("SUBS")) {
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
            } else if (command.startsWith("DEL")) {
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
            } else if (command.startsWith("CPCTY")) {
                int capacity = subscribers.size();
                plotterOut.println("CPCTY:" + capacity + ":3");
                out.println("Capacity sent to plotter.");
                System.out.println("Kapasite bilgisi gönderildi: " + capacity);
            } else {
                out.println("Unknown command");
                System.out.println("Bilinmeyen komut alındı.");
            }
        } catch (IOException e) {
            System.err.println("İstemciyle iletişim sırasında hata: " + e.getMessage());
        }
    }
}
