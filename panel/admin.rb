

require 'socket'

# Sunucu bağlantı bilgileri
SERVER1_PORT = 5001
SERVER2_PORT = 5002
SERVER3_PORT = 5003
HOST = 'localhost'

# Sunucuya bağlanıp yanıt almak için bir fonksiyon
def connect_to_server(port)
  begin
    socket = TCPSocket.new(HOST, port)
    socket.puts "SUBS, ID:123, NAME_SURNAME:\"John Doe\", START_DATE:1680000000000, LAST_ACCESSED:1685000000000, INTERESTS:[\"Music, Technology\"], IS_ONLINE:true"
    response = socket.gets
    socket.close
    return response
  rescue => e
    return "Hata: #{e.message}"
  end
end

# Hata sayacı
error_count = {server1: 0, server2: 0, server3: 0}
responses = {server1: [], server2: [], server3: []}

# Sunuculardan yanıt al
servers = {server1: SERVER1_PORT, server2: SERVER2_PORT, server3: SERVER3_PORT}

servers.each do |server_name, port|
  puts "Connecting to #{server_name} on port #{port}..."
  response = connect_to_server(port)
  responses[server_name] << response
  if response.include?("Hata")
    error_count[server_name] += 1
  end
end

# Yanıtları yazdır
puts "Yanıtlar:"
responses.each do |server_name, response_list|
  puts "#{server_name}: #{response_list}"
end

# Hata sayılarının yazdırılması
puts "Hata sayıları:"
error_count.each do |server_name, count|
  puts "#{server_name}: #{count}"
end

# Sunucu hata toleransı seviyelerine göre aksiyon
error_count.each do |server_name, count|
  case server_name
  when :server1
    if count > 1
      puts "#{server_name.capitalize} hata toleransı aşıldı! Kapatılıyor."
    end
  when :server2
    if count > 2
      puts "#{server_name.capitalize} hata toleransı aşıldı! Kapatılıyor."
    end
  when :server3
    if count > 2
      puts "#{server_name.capitalize} hata toleransı aşıldı! Kapatılıyor."
    end
  end
end

