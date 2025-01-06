import socket
import time
import matplotlib.pyplot as plt

# Sunucu bağlantı bilgileri
SERVER1_PORT = 5001
SERVER2_PORT = 5002
SERVER3_PORT = 5003
HOST = 'localhost'

# Sunucuya bağlanıp yanıt almak için bir fonksiyon
def connect_to_server(server_port):
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((HOST, server_port))
            s.sendall(b"SUBS, ID:123, NAME_SURNAME:\"John Doe\", START_DATE:1680000000000, LAST_ACCESSED:1685000000000, INTERESTS:[\"Music, Technology\"], IS_ONLINE:true")
            response = s.recv(1024)
            return response.decode('utf-8')
    except Exception as e:
        return f"Hata: {e}"

# Hata sayacını başlat
error_count = {'server1': 0, 'server2': 0, 'server3': 0}
responses = {'server1': [], 'server2': [], 'server3': []}

# Sunuculardan yanıt al
servers = [(SERVER1_PORT, 'server1'), (SERVER2_PORT, 'server2'), (SERVER3_PORT, 'server3')]

for port, server_name in servers:
    print(f"Connecting to {server_name} on port {port}...")
    response = connect_to_server(port)
    responses[server_name].append(response)
    if "Hata" in response:
        error_count[server_name] += 1

# Verileri yazdır ve grafiğe dök
print("Yanıtlar:")
for server_name, response_list in responses.items():
    print(f"{server_name}: {response_list}")

print("Hata sayıları:")
for server_name, count in error_count.items():
    print(f"{server_name}: {count}")

# Hata sayılarının görselleştirilmesi
plt.bar(error_count.keys(), error_count.values(), color='red')
plt.title('Sunucularda Oluşan Hatalar')
plt.ylabel('Hata Sayısı')
plt.show()