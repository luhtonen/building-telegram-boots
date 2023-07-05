const { Telegraf } = require('telegraf');
const { message } = require('telegraf/filters');
const Koa = require('koa');
const { koaBody } = require('koa-body');
require('dotenv');

const bot = new Telegraf(process.env.BOT_TOKEN);
const app = new Koa();
const port = 3000;
// get this domain from localtunnel
const webhookDomain = 'strong-games-live.loca.lt';

// First reply will be served via webhook response,
// but messages order not guaranteed due to `koa` pipeline design.
// Details: https://github.com/telegraf/telegraf/issues/294
bot.command("image", ctx =>
    ctx.replyWithPhoto({ url: "https://picsum.photos/200/300/?random" }),
);
bot.on(message("text"), ctx => ctx.reply("Hello"));

app.use(koaBody());
app.use(async (ctx, next) => {
    console.log('creating webhook...');
    const webhook = await bot.createWebhook({domain: webhookDomain});
    console.log('calling webhook...');
    return webhook(ctx.req, ctx.res, next);
});
app.listen(port, () => console.log("Listening on port", port));
