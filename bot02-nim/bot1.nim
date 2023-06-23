# to execute run following command: nim c -d:ssl -r bot1.nim
import telebot, asyncdispatch, logging, options, strutils

var L = newConsoleLogger(fmtStr="$levelname, [$time] ")
addHandler(L)

const API_KEY = strip(slurp(".secrets"))

proc updateHandler(b: Telebot, u: Update): Future[bool] {.async.} =
    var response = u.message.get
    discard await b.sendMessage(response.chat.id, "hello")

let bot = newTeleBot(API_KEY)
bot.onUpdate(updateHandler)
bot.poll(timeout=300)
