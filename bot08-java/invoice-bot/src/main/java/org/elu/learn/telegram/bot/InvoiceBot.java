package org.elu.learn.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.LabeledPrice;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ShippingOption;
import com.pengrad.telegrambot.request.AnswerPreCheckoutQuery;
import com.pengrad.telegrambot.request.AnswerShippingQuery;
import com.pengrad.telegrambot.request.SendInvoice;
import com.pengrad.telegrambot.request.SendMessage;

public class InvoiceBot {
    public static void main(String[] args) {
        final String token = System.getenv("TELEGRAM_TOKEN");
        final String providerToken = System.getenv("PROVIDER_TOKEN");
        if (token == null) {
            throw new IllegalArgumentException("Environment variable 'TELEGRAM_TOKEN' is not set");
        }
        if (providerToken == null) {
            throw new IllegalArgumentException("Environment variable 'PROVIDER_TOKEN' is not set");
        }
        final var bot = new TelegramBot(token);
        System.out.println("Invoice bot is listening...");
        bot.setUpdatesListener(updates -> {
            try {
                if (!updates.isEmpty()) {
                    final var update = updates.get(0);
                    final var shipping = update.shippingQuery();
                    if (shipping != null) {
                        final var option = new ShippingOption("dhl", "DHL", new LabeledPrice("CHF", 20));
                        final var query = new AnswerShippingQuery(shipping.id(), option);
                        bot.execute(query);

                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    final var query = update.preCheckoutQuery();
                    if (query != null) {
                        final var request = new AnswerPreCheckoutQuery(query.id());
                        bot.execute(request);

                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    final var chatId = update.message().chat().id();
                    final var payment = update.message().successfulPayment();
                    if (payment != null) {
                        final var thankYou = new SendMessage(chatId, "*thank you*").parseMode(ParseMode.MarkdownV2);
                        bot.execute(thankYou);

                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    final var invoice = new SendInvoice(chatId, "Lemon", "lemons", "my_payload", providerToken, "my_start_param", "CHF", new LabeledPrice("label", 20))
                        .needPhoneNumber(false)
                        .needShippingAddress(false)
                        .isFlexible(true)
                        .replyMarkup(new InlineKeyboardMarkup(
                            new InlineKeyboardButton("just pay").pay(),
                            new InlineKeyboardButton("google it").url("www.google.com")));
                    final var response = bot.execute(invoice);
                    System.out.println("response: " + response);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            } catch (Exception ex) {
                System.out.println("Exception caught: " + ex.getLocalizedMessage());
                return UpdatesListener.CONFIRMED_UPDATES_NONE;
            }
        });
    }
}
