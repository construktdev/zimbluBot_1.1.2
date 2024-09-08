package de.construkter;

import de.construkter.commands.SlashCommandListener;
import de.construkter.events.OnReady;
import de.construkter.modules.embedBuilder.CommandListener;
import de.construkter.modules.embedBuilder.ModalListener;
import de.construkter.modules.logging.LoggingListener;
import de.construkter.modules.ticket.CloseCommand;
import de.construkter.modules.ticket.OpenTicket;
import de.construkter.modules.ticket.TicketPanel;
import de.construkter.ressources.BotConfig;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Logger.event("Starting Log-In to the Discord-API...");
        BotConfig config = new BotConfig();
        JDA jda = JDABuilder.create(config.getProperty("token"), GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS)
                .addEventListeners(new OnReady())
                .addEventListeners(new TicketPanel())
                .addEventListeners(new OpenTicket())
                .addEventListeners(new SlashCommandListener())
                .addEventListeners(new LoggingListener())
                .addEventListeners(new CommandListener())
                .addEventListeners(new ModalListener())
                .addEventListeners(new CloseCommand())
                .build();
        Logger.event("Updating Commands");
        jda.updateCommands().addCommands(
                Commands.slash("send-panel", "[TICKETS] Sende ein neues ticket Panel im aktuellen Channel [ADMIN]").setGuildOnly(true),
                Commands.slash("help", "[UTILS] Gibt dir einen Überblick an Befehlen").setGuildOnly(true),
                Commands.slash("stats", "[UTILS] Zeigt dir ein paar Infos über den Bot").setGuildOnly(true),
                Commands.slash("reaction-role", "[UTILS] Setzt eine neue Reaktionsnachricht [ADMIN]")
                        .addOption(OptionType.STRING, "message", "Die Nachrichten ID wo reagiert werden soll")
                        .addOption(OptionType.CHANNEL, "channel", "Der Kanal wo sich die Nachricht befindet")
                        .addOption(OptionType.ROLE, "role", "Die Rolle die Nutzer bekommen sollen")
                        .addOption(OptionType.STRING, "emoji", "Der Emoji (KEINE DISCORD EMOJIS)")
                        .setGuildOnly(true),
                Commands.slash("embed", "[UTILS] Erstelle einen Embed und sende ihn [ADMIN]").setGuildOnly(true),
                Commands.slash("close", "[TICKETS] Schließe das aktuelle Ticket").setGuildOnly(true)
        ).queue();
        Logger.event("Succesfully updated all Commands");
    }
}