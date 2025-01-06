import com.example.protobuf.Subscription;
import java.io.*;
import java.net.*;

public class Server2 {
    private static final int SERVER_PORT = 5002;
    private static final int MAX_ERRORS = 2;  // Hata toleransı 2

    public static void main(String[] args) {
        int errorCount = 0;  // Hata sayacı
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server2 başlatıldı...");
            
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    try (InputStream in = socket.getInputStream();
                         OutputStream out = socket.getOutputStream()) {

                        // Protobuf mesajını okuma
                        Subscription subscription = Subscription.parseFrom(in);
                        System.out.println("Server2 - Alınan abone verisi: " + subscription);

                        // Yanıt gönderme
                        String response = "Server2 - Abone verisi alındı!";
                        out.write(response.getBytes());
                    }
                } catch (IOException | InvalidProtocolBufferException e) {
                    errorCount++;
                    System.err.println("Hata oluştu: " + e.getMessage());
                    if (errorCount >= MAX_ERRORS) {
                        System.err.println("Maksimum hata sayısına ulaşıldı. Server2 kapatılıyor.");
                        break;  // Hata toleransı aşıldığında sunucu kapanır
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server2 hatası: " + e.getMessage());
        }
    }
}
