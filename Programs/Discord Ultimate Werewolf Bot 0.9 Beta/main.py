import os
import discord
import requests
import json
import random
import difflib
import datetime
from discord.ext import commands
from discord import Intents
from apscheduler.schedulers.asyncio import AsyncIOScheduler
from apscheduler.triggers.cron import CronTrigger
import pytz

intents = Intents.default()
intents.members = True
intents.message_content = True # Enable the message content intent

bot = commands.Bot(command_prefix='%', intents=intents)

playerList = []  # List to store players with the 'Villager' role
voteList = []  # List to store votes
vote_message = None  # Variable to store the vote message
open_time = None
close_time = None
currentDay = 1  # Track the current day of the game
eatList = []  # List to store Werewolf votes
wolfList = []  # List to store Werewolf who have voted

scheduler = AsyncIOScheduler()

@bot.event
async def on_ready():
    print(f'{bot.user} has connected to Discord!')
    await bot.change_presence(activity=discord.Activity(type=discord.ActivityType.listening, name="some villagers."))
    scheduler.start()

async def open_village():
    # Find the channel by name
    target_channel = None
    for channel in bot.get_all_channels():
        if channel.name.lower() == 'the-village🏠'.lower():
            target_channel = channel
            break

    if target_channel is None:
        print("The 'the-village🏠' channel was not found.")
        return

    # Send notification to Game Master
    game_master_channel = bot.get_channel(711659288463933486)

    # Output the Werewolf choice
    global eatList
    most_voted_player = None
    max_votes = 0
    for voter, votee in eatList:
        if votee != "Abstained":  # Ignore "Abstained" votes
            vote_count = eatList.count((voter, votee))
            if vote_count > max_votes:
                max_votes = vote_count
                most_voted_player = votee

    if most_voted_player:
        await game_master_channel.send(f"The Wolves have chosen to feast upon {most_voted_player.mention}!")
    eatList = [] # Reset eatList

    await game_master_channel.send("The village is now open. Please write a message to start the new day:")

    def check_game_master(m):
        return m.channel.id == 711659288463933486

    try:
        opening_message_msg = await bot.wait_for('message', check=check_game_master)
        opening_message = opening_message_msg.content
        await target_channel.send(opening_message)

        # Find the villager role
        villager_role = None
        for role in target_channel.guild.roles:
            if role.name.lower() == 'villager'.lower():
                villager_role = role
                break

        if villager_role is None:
            print("The 'Villager' role was not found.")
            return

        # Enable send messages permission for villagers
        await target_channel.set_permissions(villager_role, send_messages=True)

        global currentDay
        currentDay += 1
        global voteList
        voteList = [None for _ in range(len(playerList))]
        # Create the initial vote message
        vote_message_text = f"**__Day {currentDay}__**\n" + "\n".join(f"{player.name} votes for: " for player in playerList)
        await vote_message.edit(content=vote_message_text)
    except TimeoutError:
        await game_master_channel.send("Timed out waiting for opening message.")

async def close_village():
    # Find the channel by name
    target_channel = None
    for channel in bot.get_all_channels():
        if channel.name.lower() == 'the-village🏠'.lower():
            target_channel = channel
            break

    if target_channel is None:
        print("The 'the-village🏠' channel was not found.")
        return

    # Find the villager role
    villager_role = None
    for role in target_channel.guild.roles:
        if role.name.lower() == 'villager'.lower():
            villager_role = role
            break

    if villager_role is None:
        print("The 'Villager' role was not found.")
        return

    # Disable send messages permission for villagers
    await target_channel.set_permissions(villager_role, send_messages=False)

    # Calculate the most voted player
    most_voted_player = None
    max_votes = 0
    for player, vote in zip(playerList, voteList):
        if vote is not None and vote != "Abstained":  # Ignore "Abstained" votes
            vote_count = voteList.count(vote)
            if vote_count > max_votes:
                max_votes = vote_count
                most_voted_player = vote

    # Send notification to Game Master
    game_master_channel = bot.get_channel(711659288463933486)
    await game_master_channel.send(f"The village has closed. The most voted player is: {most_voted_player.mention if most_voted_player else 'No one'}.")

    # Prompt for closing message
    await game_master_channel.send("Please write a closing message for the village:")

    def check_game_master(m):
        return m.channel.id == 711659288463933486

    try:
        closing_message_msg = await bot.wait_for('message', check=check_game_master)
        closing_message = closing_message_msg.content
        await target_channel.send(closing_message)
    except TimeoutError:
        await game_master_channel.send("Timed out waiting for closing message.")

@bot.command(name='startGame', help='Starts the game and collects players.')
@commands.has_any_role('Game Master', 'Owner') 
async def start_game(ctx):
    global playerList, voteList, vote_message, open_time, close_time, currentDay  # Access the global variables
    playerList = []  # Reset the player list
    voteList = []  # Reset the vote list
    currentDay = 1  # Reset the current day counter

    await ctx.send(f"Please provide the opening time for the village in CST (HH:MM) format.")
    def check(m):
        return m.author == ctx.author and m.channel == ctx.channel
    try:
        open_time_msg = await bot.wait_for('message', check=check)
        open_time_str = open_time_msg.content.strip()
        open_time = datetime.datetime.strptime(open_time_str, "%H:%M").time()
    except TimeoutError:
        await ctx.send("Timed out waiting for opening time.")
        return

    await ctx.send(f"Please provide the closing time for the village in CST (HH:MM) format.")
    try:
        close_time_msg = await bot.wait_for('message', check=check)
        close_time_str = close_time_msg.content.strip()
        close_time = datetime.datetime.strptime(close_time_str, "%H:%M").time()
    except TimeoutError:
        await ctx.send("Timed out waiting for closing time.")
        return

    for member in ctx.guild.members:
        if 'Villager' in [role.name for role in member.roles]:
            playerList.append(member)
            voteList.append(None)  # Initialize voteList with None for each player
            await ctx.send(f"{member.mention} has been added to the game!")

    await ctx.send(f"Game started with {len(playerList)} players!")

    # Get the vote channel
    vote_channel = bot.get_channel(711659287654563889)

    # Create the initial vote message
    vote_message_text = f"**__Day {currentDay}__**\n" + "\n".join(f"{player.name} votes for: " for player in playerList)
    vote_message = await vote_channel.send(vote_message_text)

    # Find the "in-game channels" category
    category = None
    for cat in ctx.guild.categories:
        if cat.name.lower() == "in-game channels".lower():
            category = cat
            break

    if category is None:
        await ctx.send("The 'in-game channels' category was not found.")
        return

    # Recreate the village chat channel inside the category
    overwrites = {
        ctx.guild.default_role: discord.PermissionOverwrite(read_messages=True, send_messages=False),
        ctx.guild.me: discord.PermissionOverwrite(read_messages=True, send_messages=True),
        discord.utils.get(ctx.guild.roles, name='Villager'): discord.PermissionOverwrite(read_messages=True, send_messages=True, embed_links=False, attach_files=False, add_reactions=False)
    }
    village_channel = await ctx.guild.create_text_channel('the-village🏠', overwrites=overwrites, category=category)

    # Set up the closing cron job
    close_time_cst = datetime.datetime.combine(datetime.date.today(), close_time, tzinfo=pytz.timezone('US/Central'))
    scheduler.add_job(close_village, 'cron', hour=close_time_cst.hour, minute=close_time_cst.minute, timezone='US/Central')

    # Set up the opening cron job
    open_time_cst = datetime.datetime.combine(datetime.date.today(), open_time, tzinfo=pytz.timezone('US/Central'))
    scheduler.add_job(open_village, 'cron', hour=open_time_cst.hour, minute=open_time_cst.minute, timezone='US/Central')

@bot.command(name='lynch', help='Votes someone to be killed.')
@commands.has_any_role('Game Master', 'Owner', 'Villager')
@commands.guild_only() # Restrict the command to guild channels
async def lynch(ctx, *, member_name): 
    # Find the channel by name
    target_channel = None
    for channel in ctx.guild.channels:
        if channel.name.lower() == 'the-village🏠'.lower():
            target_channel = channel
            break

    # Check if the channel was found
    if target_channel is None:
        await ctx.send(f"The 'the-village🏠' channel was not found. Please check the channel name.")
        return

    # Check if the command is invoked in the correct channel
    if ctx.channel != target_channel:
        await ctx.send(f"You can only use the 'lynch' command in the 'the-village🏠' channel.")
        return

    user = ctx.message.author

    # Check if the voter is a player
    if user not in playerList:
        playerList.append(user)
        voteList.append(None)  # Add the voter to voteList
        print(f'{user} added to playerList')

    # Check if the voter is trying to vote for themselves
    if member_name.lower() == user.name.lower():
        await ctx.send(f'{ctx.message.author.mention}, you may not vote for yourself!')
        return

    # Find the member based on name (case-insensitive)
    member = None
    for m in ctx.guild.members:
        if m.name.lower() == member_name.lower():
            member = m
            break

    # Check if the member was found
    if member is None:
        # Get close matches using difflib
        close_matches = difflib.get_close_matches(member_name, [m.name for m in ctx.guild.members], n=1, cutoff=0.6)
        if close_matches:
            suggestion = close_matches[0]
            await ctx.send(f"{ctx.message.author.mention}, did you mean '{suggestion}'?  Please try again with the correct spelling.")
        else:
            await ctx.send(f"{ctx.message.author.mention}, the player '{member_name}' was not found in the game.")
        return

    # Check if the votee is a player
    if member not in playerList:
        await ctx.send(f'{ctx.message.author.mention}, {member.mention} is not a player in the game.')
        return

    # Store the vote
    if user in playerList:
        print(f'{user} found in playerList')
        voteList[playerList.index(user)] = member
        print(voteList[playerList.index(user)])
        await ctx.send(f'Vote placed by {ctx.message.author.mention} for {member.mention}')

    # Log the vote
    channel = bot.get_channel(711659288837226533)
    embed = discord.Embed(title="Vote Log", description=f"{ctx.author.mention} voted", color=discord.Color.blue())
    embed.add_field(name="Vote placed for", value=f"{member.mention}")
    await channel.send(embed=embed)

    # Update the vote message
    vote_message_text = f"**__Day {currentDay}__**\n" + "\n".join(f"{player.name} votes for: {vote.name if vote else ''}" for player, vote in zip(playerList, voteList))
    await vote_message.edit(content=vote_message_text)


# ..... pm <player> <message> .....
@bot.command(name='pm', help='DM another user.', pass_context=True)
@commands.has_any_role('Game Master', 'Owner', 'Villager')
async def send_dm(ctx, member_name: str, *, message: str): 
    user = ctx.message.author

    # Find the member based on name (case-insensitive)
    member = None
    for m in ctx.guild.members:
        if m.name.lower() == member_name.lower():
            member = m
            break

    # Check if the member was found
    if member is None:
        # Get close matches using difflib
        close_matches = difflib.get_close_matches(member_name, [m.name for m in ctx.guild.members], n=1, cutoff=0.6)
        if close_matches:
            suggestion = close_matches[0]
            await ctx.send(f"{ctx.message.author.mention}, did you mean '{suggestion}'?  Please try again with the correct spelling.")
        else:
            await ctx.send(f"{ctx.message.author.mention}, the player '{member_name}' was not found in the game.")
        return

    # Check if the recipient is a player
    if member not in playerList:
        await ctx.send(f'{ctx.message.author.mention}, {member.mention} is not a player.')
        return

    channel = await member.create_dm()
    await channel.send(f"*{ctx.message.author.mention} whispers to you:*\n\t\t{message}")
    await ctx.send(f'Message sent to {member}')
    print(f'PM sent by {ctx.message.author} to {member}')

    # Log the PM
    user = ctx.author.mention
    voted = member.mention
    channel = bot.get_channel(711659288837226532)
    embed = discord.Embed(title="PM Log", description=f'{user}  to {voted}', color=discord.Color.blue())
    embed.add_field(name="Message Sent", value=f"{message}")
    await channel.send(embed=embed)

@bot.command(name='abstain', help='Remove your vote.')
@commands.has_any_role('Game Master', 'Owner', 'Villager')
@commands.guild_only()
async def abstain(ctx):
    user = ctx.message.author

    # Find the channel by name
    target_channel = None
    for channel in ctx.guild.channels:
        if channel.name.lower() == 'the-village🏠'.lower():
            target_channel = channel
            break

    # Check if the channel was found
    if target_channel is None:
        await ctx.send(f"The 'the-village🏠' channel was not found. Please check the channel name.")
        return

    # Check if the command is invoked in the correct channel
    if ctx.channel != target_channel:
        await ctx.send(f"You can only use the 'abstain' command in the 'the-village🏠' channel.")
        return

    # Remove the vote from voteList
    if user in playerList:
        voteList[playerList.index(user)] = "Abstained"
        await ctx.send(f"{user.mention} has abstained from the vote.")

        # Update the vote message
        vote_message_text = f"**__Day {currentDay}__**\n" + "\n".join(f"{player.name} votes for: {vote.name if vote else 'Abstained'}" for player, vote in zip(playerList, voteList))
        await vote_message.edit(content=vote_message_text)
    # Log the vote
    channel = bot.get_channel(711659288837226533)
    embed = discord.Embed(title="Vote Log", description=f"{ctx.author.mention} abstained", color=discord.Color.blue())
    await channel.send(embed=embed)

@bot.command(name='eat', help='Werewolf vote.')
@commands.has_any_role('Game Master', 'Owner', 'Villager')
@commands.guild_only()
async def eat(ctx, *, member_name):
    user = ctx.message.author

    # Check if the user is a wolf
    if user not in wolfList:
        wolfList.append(user)
        eatList.append((user, None))  # Add the user to the wolfList and initialize their vote as None

    # Check if the votee is a player
    if member_name.lower() == user.name.lower():
        await ctx.send(f'{ctx.message.author.mention}, you may not vote for yourself!')
        return

    # Find the member based on name (case-insensitive)
    member = None
    for m in ctx.guild.members:
        if m.name.lower() == member_name.lower():
            member = m
            break

    # Check if the member was found
    if member is None:
        # Get close matches using difflib
        close_matches = difflib.get_close_matches(member_name, [m.name for m in ctx.guild.members], n=1, cutoff=0.6)
        if close_matches:
            suggestion = close_matches[0]
            await ctx.send(f"{ctx.message.author.mention}, did you mean '{suggestion}'?  Please try again with the correct spelling.")
        else:
            await ctx.send(f"{ctx.message.author.mention}, the player '{member_name}' was not found in the game.")
        return

    # Check if the votee is a player
    if member not in playerList:
        await ctx.send(f'{ctx.message.author.mention}, {member.mention} is not a player in the game.')
        return

    # Update the vote
    eatList[wolfList.index(user)] = (user, member)  # Update the user's vote in eatList
    await ctx.send(f'Vote placed by {ctx.message.author.mention} for {member.mention}')

@bot.command(name='starve', help='Remove your Werewolf vote.')
@commands.has_any_role('Game Master', 'Owner', 'Villager')
@commands.guild_only()
async def starve(ctx):
    user = ctx.message.author

    # Find the channel by name
    target_channel = None
    for channel in ctx.guild.channels:
        if channel.name.lower() == 'wolf-chat🐺'.lower():
            target_channel = channel
            break

    # Check if the channel was found
    if target_channel is None:
        await ctx.send(f"The 'wolf-chat🐺' channel was not found. Please check the channel name.")
        return

    # Check if the command is invoked in the correct channel
    if ctx.channel != target_channel:
        await ctx.send(f"You can only use the 'starve' command in the 'wolf-chat🐺' channel.")
        return

    # Remove the vote from eatList
    if user in wolfList:
        eatList[wolfList.index(user)] = (user, "Abstained")
        await ctx.send(f"{user.mention} has abstained from the Werewolf vote.")


@bot.command(name='endGame', help='Ends the game.')
@commands.has_any_role('Game Master', 'Owner')
async def end_game(ctx):
    global playerList, voteList, eatList, wolfList
    playerList = []
    voteList = []
    eatList = []
    wolfList = []

    # Remove roles from everyone
    for member in ctx.guild.members:
        if any(role.name in ['Villager', 'Deceased', 'Spectator'] for role in member.roles):
            await member.remove_roles(discord.utils.get(member.guild.roles, name='Villager'),
                                   discord.utils.get(member.guild.roles, name='Deceased'),
                                   discord.utils.get(member.guild.roles, name='Spectator'))

    game_master_channel = bot.get_channel(711659288463933486)
    await game_master_channel.send("The game has ended. Please write a final message for the village:")

    def check_game_master(m):
        return m.channel.id == 711659288463933486

    try:
        closing_message_msg = await bot.wait_for('message', check=check_game_master)
        closing_message = closing_message_msg.content
        scheduler.remove_job(open_village)
        scheduler.remove_job(close_village)
        
        # Find the village channel
        village_channel = None
        for channel in bot.get_all_channels():
            if channel.name.lower() == 'the-village🏠'.lower():
                village_channel = channel
                break

        if village_channel:
            await village_channel.send(closing_message)
        else:
            await game_master_channel.send("The village channel was not found.")
    except TimeoutError:
        await game_master_channel.send("Timed out waiting for final message.")


@bot.command(name='removePlayer', help='Removes a player from the game.')
@commands.has_any_role('Game Master', 'Owner')
@commands.guild_only() 
async def remove_player(ctx, *, member_name):
    # Find the member based on name (case-insensitive)
    member = None
    for m in ctx.guild.members:
        if m.name.lower() == member_name.lower():
            member = m
            break

    # Check if the member was found
    if member is None:
        # Get close matches using difflib
        close_matches = difflib.get_close_matches(member_name, [m.name for m in ctx.guild.members], n=1, cutoff=0.6)
        if close_matches:
            suggestion = close_matches[0]
            await ctx.send(f"{ctx.message.author.mention}, did you mean '{suggestion}'?  Please try again with the correct spelling.")
        else:
            await ctx.send(f"{ctx.message.author.mention}, the player '{member_name}' was not found in the game.")
        return

    # Check if the member is a player
    if member not in playerList:
        await ctx.send(f"{ctx.message.author.mention}, {member.mention} is not a player in the game.")
        return

    # Remove the 'Villager' role and add the 'Deceased' role
    villager_role = discord.utils.get(ctx.guild.roles, name='Villager')
    deceased_role = discord.utils.get(ctx.guild.roles, name='Deceased')
    await member.remove_roles(villager_role)
    await member.add_roles(deceased_role)

    # Remove the player from playerList and voteList
    playerList.remove(member)
    voteList.remove(voteList[playerList.index(member)])

    await ctx.send(f"{member.mention} has been removed from the game.")

bot.run('bot token')
