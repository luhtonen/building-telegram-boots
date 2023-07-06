import os
from telegram import Update
from telegram.ext import filters, ApplicationBuilder, ContextTypes, MessageHandler
import cv2 as cv
import pprint
import tempfile

classifier = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml')


def detect(image):
    gray = cv.cvtColor(image, cv.COLOR_BGR2GRAY)
    faces = classifier.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5)
    count = 0
    for (x, y, w, h) in faces:
        count = count + 1
        cv.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 5)
    return image, count


async def photo_handle(update: Update, context: ContextTypes.DEFAULT_TYPE):
    msg = update.message
    chat_id = update.effective_chat.id
    if msg.photo:
        file_id = msg.photo[-1]['file_id']
        new_file = await context.bot.get_file(file_id)
        f = tempfile.NamedTemporaryFile(delete=True).name+'.png'
        await new_file.download_to_drive(custom_path=f)
        p = cv.imread(f)
        hsv, f_count = detect(p)
        cv.imwrite(f, hsv)
        await context.bot.send_photo(chat_id=chat_id, photo=open(f, 'rb'), caption="found %i faces" % f_count)
    else:
        pprint.pprint('no photo')


async def msg_handle(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text='Please, send photo')


if __name__ == '__main__':
    token = os.environ['BOT_TOKEN']
    application = ApplicationBuilder().token(token).build()

    photo_handler = MessageHandler(filters.PHOTO, photo_handle)
    msg_handler = MessageHandler(filters.TEXT, msg_handle)
    application.add_handler(photo_handler)
    application.add_handler(msg_handler)

    application.run_polling()
