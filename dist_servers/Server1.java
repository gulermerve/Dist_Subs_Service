import com.example.protobuf.Subscription;
import java.io.*;
import java.net.*;

public class Server1 {
    private static final int SERVER_PORT = 5001;
    private static final int MAX_ERRORS = 1;  // Hata toleransı 1

    public static void main(String[] args) {
        int errorCount = 0;  // Hata sayacı
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server1 başlatıldı...");
            
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    try (InputStream in = socket.getInputStream();
                         OutputStream out = socket.getOutputStream()) {

                        // Protobuf mesajını okuma
                        Subscription subscription = Subscription.parseFrom(in);
                        System.out.println("Server1 - Alınan abone verisi: " + subscription);

                        // Yanıt gönderme
                        String response = "Server1 - Abone verisi alındı!";
                        out.write(response.getBytes());
                    }
                } catch (IOException | InvalidProtocolBufferException e) {
                    errorCount++;
                    System.err.println("Hata oluştu: " + e.getMessage());
                    if (errorCount >= MAX_ERRORS) {
                        System.err.println("Maksimum hata sayısına ulaşıldı. Server1 kapatılıyor.");
                        break;  // Hata toleransı aşıldığında sunucu kapanır
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server1 hatası: " + e.getMessage());
        }
    }
}




