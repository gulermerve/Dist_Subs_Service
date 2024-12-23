require_relative 'admin'


File.open('test_dist_subs.conf', 'w') do |file|
  file.puts 'fault_tolerance_level = 1000'
end


servers = [['localhost', 5001], ['localhost', 5002], ['localhost', 5003]]
config = read_config('test_dist_subs.conf')

puts "Configuration loaded: #{config.fault_tolerance_level}, #{config.method}"

send_start_command(servers, config)
