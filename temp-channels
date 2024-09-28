import discord
from discord.ext import tasks
from discord.utils import get
import asyncio

# Deine Konfigurationswerte (IDs müssen korrekt sein)
TEMP_CREATE_CHANNEL_ID = 1277225099899965471  # Die ID des Voice-Kanals, der überwacht wird
TEMP_CATEGORY_ID = 1289297819277987931  # Die ID der Kategorie, in der die temporären Kanäle erstellt werden sollen
GUILD_ID = 1199634208524599347  # Die ID der Gilde (Server)
BOT_TOKEN = 'TOKEN'  # Dein Bot-Token

intents = discord.Intents.default()
intents.members = True  # Aktiviert das Abrufen von Mitglieder-Informationen
intents.voice_states = True  # Aktiviert das Abrufen von Voice-Status

client = discord.Client(intents=intents)

@client.event
async def on_ready():
    print(f'Bot ist bereit. Eingeloggt als {client.user}')
    check_voice_channel.start()  # Startet den Check alle 3 Sekunden

@tasks.loop(seconds=3)
async def check_voice_channel():
    guild = client.get_guild(GUILD_ID)
    
    if guild is None:
        print("Gilde nicht gefunden")
        return
    
    # Hole den Sprachkanal
    voice_channel = guild.get_channel(TEMP_CREATE_CHANNEL_ID)
    
    if voice_channel is None:
        print("Sprachkanal nicht gefunden")
        return
    
    # Prüfe, ob Mitglieder im Kanal sind
    members_in_channel = voice_channel.members
    
    if members_in_channel:
        print(f"Mitglieder im Kanal {voice_channel.name}: {len(members_in_channel)}")
        
        for member in members_in_channel:
            # Hole den Usernamen und erstelle einen temporären Kanal
            user_name = member.name
            category = guild.get_channel(TEMP_CATEGORY_ID)
            
            if category is not None:
                temp_channel = await category.create_voice_channel(name=user_name)
                
                # Verschiebe das Mitglied in den neuen Kanal
                await member.move_to(temp_channel)
                print(f"Mitglied {user_name} wurde in den temporären Kanal {temp_channel.name} verschoben.")
    else:
        print(f"Der Kanal {voice_channel.name} ist leer.")

@client.event
async def on_voice_state_update(member, before, after):
    # Wenn ein Mitglied den Voice-Kanal verlässt, wird der temporäre Kanal nach einer Verzögerung gelöscht
    if before.channel is not None and before.channel.name == member.name:
        await asyncio.sleep(30)  # Warte 30 Sekunden vor dem Löschen
        if before.channel is not None and len(before.channel.members) == 0:
            await before.channel.delete()
            print(f"Der temporäre Kanal {before.channel.name} wurde gelöscht.")

client.run(BOT_TOKEN)
