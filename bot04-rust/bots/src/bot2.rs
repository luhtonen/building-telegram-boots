use teloxide::prelude::*;
use teloxide::Bot;

// need to set TELOXIDE_TOKEN environment variable with Telegram token
#[tokio::main]
async fn main() {
    let bot = Bot::from_env();

    let locations = [
        (35.652832, 139.839478),
        (35.652832, 138.839478),
        (35.652832, 137.839478),
        (35.652832, 136.839478),
    ];
    // TODO: iterate over multiple locations
    teloxide::repl(bot, |bot: Bot, msg: Message| async move {
        bot.send_location(msg.chat.id, 35.652832, 139.839478)
            .live_period(60)
            .await?;
        Ok(())
    })
    .await;
}
