import os

from telegram import Update
from telegram.ext import ApplicationBuilder, CommandHandler, ContextTypes


async def photo(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_photo(chat_id=update.effective_chat.id, photo='https://picsum.photos/200/300/?random')


if __name__ == '__main__':
    token = os.environ['BOT_TOKEN']
    application = ApplicationBuilder().token(token).build()

    photo_handler = CommandHandler('image', photo)
    application.add_handler(photo_handler)

    application.run_polling()
