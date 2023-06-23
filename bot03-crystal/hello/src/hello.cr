require "tourmaline"

# Need to install depedencies first with the following command:
# shards install
# to build and run execute following command in echo directory:
# crystal run src/hello.cr
# In telegram select this bot and send following message:
# /echo "How are you?"
client = Tourmaline::Client.new(ENV["BOT_TOKEN"])

echo_handler = Tourmaline::CommandHandler.new("hello") do |ctx|
  ctx.reply("world")
end

client.register(echo_handler)
client.poll
