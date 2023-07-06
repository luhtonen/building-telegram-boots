import asyncio
import os
import telegram


async def main():
    token = os.environ['BOT_TOKEN']
    bot = telegram.Bot(token)
    async with bot:
        print(await bot.get_me())


if __name__ == '__main__':
    asyncio.run(main())
