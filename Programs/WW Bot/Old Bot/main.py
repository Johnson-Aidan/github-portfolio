# bot.py
import random

import discord
from discord.ext import commands

bot = commands.Bot(command_prefix='%')



with open("players.txt", "r") as playerFile:
    playerFile.writelines()

with open("votes.txt", "r") as voteFile:
    voteList = voteFile.readlines()


@bot.event
async def on_ready():
    print(f'{bot.user.name} has connected to Discord!')
    await bot.change_presence(activity=discord.Activity(type=discord.ActivityType.listening, name="some villagers."))


@bot.command(name='ping')
async def ping(ctx):
    await ctx.send(f'Pong! {round(bot.latency * 1000)}ms ')


@bot.command(name='roll', help='Simulates rolling dice.')
async def roll(ctx, number_of_dice: int, number_of_sides: int):
    dice = [
        str(random.choice(range(1, number_of_sides + 1)))
        for _ in range(number_of_dice)
    ]
    await ctx.send(', '.join(dice))


@bot.command(name='pm', help='DM another user.', pass_context=True)
@commands.has_any_role('Game Master', 'Owner', 'Villager')
async def send_dm(ctx, member: discord.Member, *, message):
    channel = await member.create_dm()
    await channel.send(f"*{ctx.message.author.mention} whispers to you:*\n\t\t{message}")
    await ctx.send(f'Message sent to {member}')
    print(f'PM sent by {ctx.message.author} to {member}')

    user = ctx.author.mention
    voted = member.mention
    channel = bot.get_channel(711659288837226532)
    embed = discord.Embed(title="PM Log", description=f'{user}  to {voted}', color=discord.Color.blue())
    embed.add_field(name="Message Sent", value=f"{message}")

    await channel.send(embed=embed)


@bot.command(name='lynch', help='Votes someone to be killed.')
@commands.has_any_role('Game Master', 'Owner', 'Villager')
async def lynch(ctx, member: discord.Member):
    user = ctx.message.author
    if user not in playerList:
        playerList.append(user)
        print(f'{user} added to playerList')

    if member == user:
        await ctx.send(f'{ctx.message.author.mention}, you may not vote for yourself!')
        raise

    if user in playerList:
        print(f'{user} found in playerList')
        voteList[playerList.index(user)] = member
        print(voteList[playerList.index(user)])
        await ctx.send(f'<@&711659287151247503>: Vote placed by {ctx.message.author.mention} for {member.mention}')

    channel = bot.get_channel(711659288837226533)
    embed = discord.Embed(title="Vote Log", description=f"{ctx.author.mention} voted", color=discord.Color.blue())
    embed.add_field(name="Vote placed for", value=f"{member.mention}")

    await channel.send(embed=embed)


@bot.command(name='results', help='Reveals the votes and who is the most voted.')
@commands.has_any_role('Game Master', 'Owner')
async def results(ctx):
    for z in voteList:
        await ctx.send(playerList[voteList[z]], ' voted for ', z)
    if not voteList:
        await ctx.send(f'No Votes have been placed.')


@bot.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.errors.CheckFailure):
        await ctx.send('You do not have the correct role for this command.')


@bot.event
async def on_error(event, *args):
    with open('err.log', 'a') as f:
        if event == 'on_message':
            f.write(f'Unhandled message: {args[0]}\n')
        else:
            raise


bot.run('NTA4ODA0NTU2MTg1MDEwMjE2.W9-TKw.5CN8TcR8n-PXzT7C7tfGNlu0-Ag')
