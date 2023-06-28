package org.elu.learn.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class Simple {
    public static void main(String[] args) {
        final String token = System.getenv("TELEGRAM_TOKEN");
        if (token == null) {
            throw new IllegalArgumentException("Environment variable 'TELEGRAM_TOKEN' is not set");
        }
        final var bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            if (!updates.isEmpty()) {
                final var chatId = updates.get(0).message().chat().id();
                final var requestText = new SendMessage(chatId, "*hello from java*").parseMode(ParseMode.MarkdownV2);
                bot.execute(requestText);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
