package de.construkter;

import de.construkter.commands.SlashCommandListener;
import de.construkter.commands.TextBasedListener;
import de.construkter.events.MemberEvent;
import de.construkter.events.OnReady;
import de.construkter.modules.embedBuilder.CommandListener;
import de.construkter.modules.embedBuilder.ModalListener;
import de.construkter.modules.logging.LoggingListener;
import de.construkter.modules.tempChannels.TempChannels;
import de.construkter.modules.tempChannels.VoiceListener;
import de.construkter.modules.ticket.*;
import de.construkter.ressources.BotConfig;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    static BotConfig config = new BotConfig();

    public static void main(String[] args) {
        Logger.event("Starting Log-In to the Discord-API...");
        JDA jda = JDABuilder.create(config.getProperty("token"), GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS)
                .addEventListeners(new OnReady())
                .addEventListeners(new TicketPanel())
                .addEventListeners(new OpenTicket())
                .addEventListeners(new SlashCommandListener())
                .addEventListeners(new CommandListener())
                .addEventListeners(new ModalListener())
                .addEventListeners(new CloseCommand())
                .addEventListeners(new TempChannels())
                .addEventListeners(new VoiceListener())
                .addEventListeners(new MemberEvent())
                .addEventListeners(new ChannelPermissionManager())
                .addEventListeners(new CloseRequest())
                .addEventListeners(new TextBasedListener())
                .addEventListeners(new LoggingListener())
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .build();
        Logger.event("Updating Commands");
        jda.updateCommands().addCommands(
                Commands.slash("send-panel", "[TICKETS] Sende ein neues ticket Panel im aktuellen Channel [ADMIN]").setGuildOnly(true),
                Commands.slash("help", "[UTILS] Gibt dir einen Überblick an Befehlen").setGuildOnly(true),
                Commands.slash("stats", "[UTILS] Zeigt dir ein paar Infos über den Bot").setGuildOnly(true),
                Commands.slash("reaction-role", "[RR] Setzt eine neue Reaktionsnachricht [ADMIN]")
                        .addOption(OptionType.STRING, "message", "Die Nachrichten ID wo reagiert werden soll")
                        .addOption(OptionType.CHANNEL, "channel", "Der Kanal wo sich die Nachricht befindet")
                        .addOption(OptionType.ROLE, "role", "Die Rolle die Nutzer bekommen sollen")
                        .addOption(OptionType.STRING, "emoji", "Der Emoji (KEINE DISCORD EMOJIS)")
                        .setGuildOnly(true),
                Commands.slash("embed", "[UTILS] Erstelle einen Embed und sende ihn [ADMIN]").setGuildOnly(true),
                Commands.slash("close", "[TICKETS] Schließe das aktuelle Ticket").setGuildOnly(true),
                Commands.slash("close-request", "[TICKETS] Frage an das aktuelle Ticket zu schließen").setGuildOnly(true),
                Commands.slash("about", "[UTILS] Zeige dir alle Funktionen zum Zimblu Netzwerk an").setGuildOnly(true),
                Commands.slash("status", "[UTILS] Zeigt dir welche Server online sind").setGuildOnly(true),
                Commands.slash("send-status-embed", "[UTILS] Sende den Status embed").setGuildOnly(true)
        ).queue();
        Logger.event("Successfully updated all Commands");
    }
}