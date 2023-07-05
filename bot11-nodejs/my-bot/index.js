const { Telegraf } = require('telegraf');
const { message } = require('telegraf/filters');
require('dotenv');

const bot = new Telegraf(process.env.BOT_TOKEN);
// built-in commands
bot.start(ctx => ctx.reply('Welcome!'));
bot.help(ctx => ctx.reply('Send me a sticker'));
// message type
bot.on(message('sticker'), ctx => ctx.reply('👍'));
bot.hears('hi', (ctx) => ctx.reply('Hey there'));

// commands
bot.command('oldschool', (ctx) => ctx.reply('Hello'));
bot.command('hipster', Telegraf.reply('λ'));
bot.command('image', ctx => ctx.replyWithPhoto('https://picsum.photos/200/300/?random'));
bot.launch();

// Enable graceful stop
process.once('SIGINT', () => bot.stop('SIGINT'));
process.once('SIGTERM', () => bot.stop('SIGTERM'));
