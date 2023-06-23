# to execute run following command: nim c -d:ssl -r photobot.nim
import telebot, asyncdispatch, logging, options, strutils, std/sets

const API_KEY = strip(slurp(".secrets"))

proc updateHandler(b: Telebot, u: Update): Future[bool] {.async.} =
  var response = u.message.get
  if response.text.isSome:
    let animal = response.text.get
    if toHashSet(["cat", "dog"]).contains(animal):
      let file = "file://" & animal & ".jpg"
      discard await b.sendPhoto(response.chat.id, file, caption = animal)
    else:
      discard await b.sendMessage(response.chat.id, "Choose either 'cat' or 'dog'")

let bot = newTeleBot(API_KEY)
bot.onUpdate(updateHandler)
bot.poll(timeout=300)
