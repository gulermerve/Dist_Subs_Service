import com.example.protobuf.Subscription;
import java.io.*;
import java.net.*;

public class Server3 {
    private static final int SERVER_PORT = 5003;
    private static final int MAX_ERRORS_CONNECTION = 1;  // Bağlantı hatası toleransı
    private static final int MAX_ERRORS_PROTOBUF = 2;    // Protobuf hatası toleransı

    public static void main(String[] args) {
        int errorCountConnection = 0;  // Bağlantı hatası sayacı
        int errorCountProtoBuf = 0;    // Protobuf hatası sayacı
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server3 başlatıldı...");
            
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    try (InputStream in = socket.getInputStream();
                         OutputStream out = socket.getOutputStream()) {

                        // Protobuf mesajını okuma
                        Subscription subscription = Subscription.parseFrom(in);
                        System.out.println("Server3 - Alınan abone verisi: " + subscription);

                        // Yanıt gönderme
                        String response = "Server3 - Abone verisi alındı!";
                        out.write(response.getBytes());
                    }
                } catch (IOException e) {
                    errorCountConnection++;
                    System.err.println("Bağlantı hatası: " + e.getMessage());
                    if (errorCountConnection >= MAX_ERRORS_CONNECTION) {
                        System.err.println("Maksimum bağlantı hatası sayısına ulaşıldı. Server3 kapatılıyor.");
                        break;  // Bağlantı hatası toleransı aşıldığında sunucu kapanır
                    }
                } catch (InvalidProtocolBufferException e) {
                    errorCountProtoBuf++;
                    System.err.println("Protobuf hatası: " + e.getMessage());
                    if (errorCountProtoBuf >= MAX_ERRORS_PROTOBUF) {
                        System.err.println("Maksimum Protobuf hatası sayısına ulaşıldı. Server3 kapatılıyor.");
                        break;  // Protobuf hatası toleransı aşıldığında sunucu kapanır
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server3 hatası: " + e.getMessage());
        }
    }
}