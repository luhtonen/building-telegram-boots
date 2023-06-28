package org.elu.learn.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

public class Simple {
    public static void main(String[] args) {
        final String token = System.getenv("TELEGRAM_TOKEN");
        if (token == null) {
            throw new IllegalArgumentException("Environment variable 'TELEGRAM_TOKEN' is not set");
        }
        final var bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            System.out.println("updates: " + updates.toString());
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
