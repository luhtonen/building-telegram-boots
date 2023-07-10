import os
import pprint
from telegram import Update
from telegram.ext import filters, ApplicationBuilder, ContextTypes, MessageHandler
import tempfile

import numpy as np
import PIL.Image as Image
import tensorflow as tf
import tensorflow_hub as hub


def download_data():
    mobilenet_v2 = "https://tfhub.dev/google/tf2-preview/mobilenet_v2/classification/4"
    # inception_v3 = "https://tfhub.dev/google/imagenet/inception_v3/classification/5"
    classifier_model = mobilenet_v2  # @param ["mobilenet_v2", "inception_v3"] {type:"raw"}

    image_shape = (224, 224)
    pprint.pprint("downloading classifier...")
    return (image_shape, tf.keras.Sequential([
        hub.KerasLayer(classifier_model, input_shape=image_shape + (3,))
    ]))


def predict(classifier, file, image_shape):
    pprint.pprint("Opening image...")
    image = Image.open(file).resize(image_shape)
    image = np.array(image)/255.0
    pprint.pprint("Starting prediction...")
    result = classifier.predict(image[np.newaxis, ...])
    predicted_class = tf.math.argmax(result[0], axis=-1)
    labels_path = tf.keras.utils.get_file('ImageNetLabels.txt',
                                          'https://storage.googleapis.com/download.tensorflow.org/data/ImageNetLabels.txt')
    imagenet_labels = np.array(open(labels_path).read().splitlines())
    predicted_class_name = imagenet_labels[predicted_class]
    return predicted_class_name.title()


async def photo_handle(update: Update, context: ContextTypes.DEFAULT_TYPE):
    msg = update.message
    chat_id = update.effective_chat.id
    if msg.photo:
        file_id = msg.photo[-1]['file_id']
        new_file = await context.bot.get_file(file_id)
        f = tempfile.NamedTemporaryFile(delete=True).name+'.png'
        await new_file.download_to_drive(custom_path=f)
        prediction = predict(classifier=classifier_global, file=f, image_shape=image_shape)
        await context.bot.send_message(chat_id=chat_id, text="I think this image is a %s" % prediction)
    else:
        pprint.pprint('no photo')


if __name__ == '__main__':
    token = os.environ['BOT_TOKEN']
    image_shape, classifier_global = download_data()

    application = ApplicationBuilder().token(token).build()

    photo_handler = MessageHandler(filters.PHOTO, photo_handle)
    application.add_handler(photo_handler)

    application.run_polling()
