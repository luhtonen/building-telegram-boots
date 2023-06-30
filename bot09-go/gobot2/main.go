package main

import (
	"gobot2/gobot2"
	"os"
)

func main() {
	token := os.Getenv("TELEGRAM_TOKEN")
	gobot2.TelegramBot(token)
}
