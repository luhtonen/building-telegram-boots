defmodule MyBot.Bot do
  @bot :my_bot

  use ExGram.Bot,
    name: @bot,
    setup_commands: true

  command("start")
  command("help", description: "Print the bot's help")
  command("echo", description: "Sends text back")
  command("dog", description: "Sends dog's picture")

  middleware(ExGram.Middleware.IgnoreUsername)

  def bot(), do: @bot

  def handle({:command, :start, _msg}, context) do
    answer(context, "Hi!")
  end

  def handle({:command, :help, _msg}, context) do
    answer(context, "Here is your help:")
  end

  def handle({:command, :echo, %{text: t}}, context) do
    context |> answer(t)
  end

  def handle({:command, :dog, msg}, context) do
    ExGram.send_document(msg.chat.id, {:file, "./dog.jpg"})
  end

  def handle(msg, context) do
    IO.puts("Unknown message #{inspect(msg)}")
  end
end
