import socket
import matplotlib.pyplot as plt

plotter_port = 6000

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('localhost', plotter_port))
server_socket.listen(5)
print(f"Plotter sunucusu {plotter_port} portunda çalışıyor.")

server_colors = {
    'Server1': 'red',
    'Server2': 'blue',
    'Server3': 'green'
}


server_data = {
    'Server1': [],
    'Server2': [],
    'Server3': []
}

while True:
    
    client_socket, addr = server_socket.accept()
    print(f"Yeni bağlantı kabul edildi: {addr}")

    try:
       
        data = client_socket.recv(1024).decode('utf-8')
        print(f"Gelen veri: {data}")

       
        parts = data.split(':')
        if len(parts) == 3:
            command, capacity, timestamp = parts
            if command == "CPCTY":
                server_name = f"Server{timestamp[-1]}"  
                server_data[server_name].append(int(capacity))
                print(f"Sunucu verisi eklendi: {server_name} -> {capacity}")

        
        plt.clf() 
        for server, color in server_colors.items():
            plt.plot(server_data[server], color=color, label=server)

        plt.xlabel('Zaman')
        plt.ylabel('Kapasite')
        plt.title('Sunucu Kapasite Takibi')
        plt.legend()

      
        plt.draw()
        plt.pause(1)

    except Exception as e:
        print(f"Bir hata oluştu: {e}")
    finally:
        client_socket.close()