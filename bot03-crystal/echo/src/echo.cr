require "tourmaline"

# to build and run execute following command in echo directory:
# crystal run src/echo.cr
# In telegram select this bot and send following message:
# /echo "How are you?"
client = Tourmaline::Client.new(ENV["BOT_TOKEN"])

echo_handler = Tourmaline::CommandHandler.new("echo") do |ctx|
  text = ctx.text.to_s
  ctx.reply(text) unless text.empty?
end

client.register(echo_handler)
client.poll
