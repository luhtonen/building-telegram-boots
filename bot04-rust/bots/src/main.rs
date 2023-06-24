use teloxide::Bot;
use teloxide::prelude::*;

// need to set TELOXIDE_TOKEN environment variable with Telegram token
#[tokio::main]
async fn main() {
    let bot = Bot::from_env();

    teloxide::repl(bot, |bot: Bot, msg: Message| async move {
        let response = format!("Hi, {}! You just wrote '{}'", msg.from().unwrap().first_name, msg.text().unwrap());
        bot.send_message(msg.chat.id, response).await?;
        Ok(())
    }).await;
}
