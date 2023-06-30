package gobot1

import (
	tele "gopkg.in/telebot.v3"
	"log"
	"time"
)

func TelegramBot(token string) {
	bot, err := tele.NewBot(tele.Settings{
		Token:  token,
		Poller: &tele.LongPoller{Timeout: 10 * time.Second},
	})

	if err != nil {
		log.Fatal(err)
		return
	}

	log.Println("Starting Go bot")
	bot.Handle("/hello", func(c tele.Context) error {
		return c.Send("hello go")
	})
	bot.Start()
}
