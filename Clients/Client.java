import com.example.protobuf.Subscription; // Protobuf sınıfının doğru yolu

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
                     OutputStream out = socket.getOutputStream();
                     InputStream in = socket.getInputStream()) {

                    // Protobuf mesajını oluşturma
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

                        // Protobuf mesajını oluştur
                        Subscription subscription = Subscription.newBuilder()
                                .setId(id)
                                .setNameSurname(nameSurname)
                                .setStartDate(startDate)
                                .setLastAccessed(lastAccessed)
                                .setInterests(interests)
                                .setIsOnline(isOnline)
                                .build();

                        // Protobuf mesajını gönderme
                        subscription.writeTo(out);
                    }

                    // Sunucudan gelen yanıtı okuma
                    byte[] responseBytes = new byte[1024];
                    int bytesRead = in.read(responseBytes);
                    String response = new String(responseBytes, 0, bytesRead);
                    System.out.println("Sunucudan gelen yanıt: " + response);

                } catch (IOException e) {
                    System.err.println("Sunucuya bağlanırken bir hata oluştu: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }
}

