require 'socket'

def read_config(file)
  config_data = {}
  File.foreach(file) do |line|
    key, value = line.strip.split('=').map(&:strip)
    config_data[key] = value
  end
  Configuration.new(config_data['fault_tolerance_level'].to_i, 'STRT')
end


def send_start_command(servers, config)
  active_servers = []
  
  servers.each do |host, port|
    begin
      socket = TCPSocket.new(host, port)
      message = "STRT #{config.fault_tolerance_level}"
      socket.puts(message)
      puts "Sent to #{host}:#{port} -> #{message}"

     
      response = socket.gets
      puts "Response from #{host}:#{port} -> #{response}"

      
      if response.include?("YEP")
        active_servers << [host, port]
      end

      socket.close
    rescue StandardError => e
      puts "Failed to connect to #{host}:#{port} - #{e.message}"
    end
  end

  active_servers
end


def query_capacity(servers)
  servers.each do |host, port|
    Thread.new do
      loop do
        begin
          socket = TCPSocket.new(host, port)
          message = "CPCTY"
          socket.puts(message)
          puts "Sent capacity query to #{host}:#{port} -> #{message}"

          
          response = socket.gets
          puts "Capacity response from #{host}:#{port} -> #{response}"

          socket.close
        rescue StandardError => e
          puts "Failed to query capacity for #{host}:#{port} - #{e.message}"
        end

        sleep(5) # 5 saniyelik sorgu aralığı
      end
    end
  end
end

# Ana işlem
config_file = 'dist_subs.conf'
servers = [['localhost', 5001], ['localhost', 5002], ['localhost', 5003]]

config = read_config(config_file)
active_servers = send_start_command(servers, config)

# Kapasite sorgusu başlat
query_capacity(active_servers)

