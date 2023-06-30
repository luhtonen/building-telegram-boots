package main

import (
	"gobot1/gobot1"
	"os"
)

func main() {
	token := os.Getenv("TELEGRAM_TOKEN")
	gobot1.TelegramBot(token)
}
