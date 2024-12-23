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
  servers.each do |host, port|
    begin
      socket = TCPSocket.new(host, port)
      message = "STRT #{config.fault_tolerance_level}"
      socket.puts(message)
      puts "Sent to #{host}:#{port} -> #{message}"

   
      response = socket.gets
      puts "Response from #{host}:#{port} -> #{response}"

      socket.close
    rescue StandardError => e
      puts "Failed to connect to #{host}:#{port} - #{e.message}"
    end
  end
end


config_file = 'dist_subs.conf'
servers = [['localhost', 5001], ['localhost', 5002], ['localhost', 5003]]

config = read_config(config_file)
send_start_command(servers, config)
