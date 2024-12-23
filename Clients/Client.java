import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final int SERVER1_PORT = 5001;
    private static final int SERVER2_PORT = 5002;
    private static final int SERVER3_PORT = 5003;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nLütfen bir komut girin (STRT, SUBS, DEL, CPCTY, EXIT):");
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("EXIT")) {
                    System.out.println("Çıkılıyor...");
                    break;
                }

               
                System.out.println("Hangi sunucuya bağlanmak istiyorsunuz? (1, 2, 3):");
                int serverChoice = Integer.parseInt(scanner.nextLine().trim());

                int serverPort = switch (serverChoice) {
                    case 1 -> SERVER1_PORT;
                    case 2 -> SERVER2_PORT;
                    case 3 -> SERVER3_PORT;
                    default -> throw new IllegalArgumentException("Geçersiz sunucu seçimi");
                };

                
                try (Socket socket = new Socket("localhost", serverPort);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                  
                    out.println(command);

                  
                    String response = in.readLine();
                    System.out.println("Sunucudan gelen yanıt: " + response);

                    if (command.startsWith("SUBS")) {
                        System.out.println("Abone bilgileri gönderiliyor. Lütfen aşağıdaki bilgileri girin:");
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("İsim Soyisim: ");
                        String nameSurname = scanner.nextLine().trim();

                        System.out.print("Başlangıç Tarihi (ms): ");
                        long startDate = Long.parseLong(scanner.nextLine().trim());

                        System.out.print("Son Erişim Tarihi (ms): ");
                        long lastAccessed = Long.parseLong(scanner.nextLine().trim());

                        System.out.print("İlgi Alanları (virgülle ayırın): ");
                        String interests = scanner.nextLine().trim();

                        System.out.print("Çevrimiçi mi? (true/false): ");
                        boolean isOnline = Boolean.parseBoolean(scanner.nextLine().trim());

                        String subscriberData = String.format(
                                "SUBS, ID:%d, NAME_SURNAME:\"%s\", START_DATE:%d, LAST_ACCESSED:%d, INTERESTS:[%s], IS_ONLINE:%b",
                                id, nameSurname, startDate, lastAccessed, interests, isOnline);

                        out.println(subscriberData);
                        response = in.readLine();
                        System.out.println("Sunucudan gelen yanıt: " + response);
                    }

                } catch (IOException e) {
                    System.err.println("Sunucuya bağlanırken bir hata oluştu: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }
}
