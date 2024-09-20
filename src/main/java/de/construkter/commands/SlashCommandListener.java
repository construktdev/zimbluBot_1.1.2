package de.construkter.commands;

import de.construkter.Main;
import de.construkter.modules.uptimeMonitor.Uptimes;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "help":
                help(event);
                break;
            case "stats":
                stats(event);
                break;
            case "about":
                about(event);
                break;
            case "status":
                status(event);
                break;
            case "send-status-embed":
                sendStatus(event);
                break;
            case "repeat":
                repeat(event);
                break;
        }
        Logger.event(Objects.requireNonNull(event.getMember()).getEffectiveName() + " hat den Befehl /" + event.getName() + " in " + Objects.requireNonNull(event.getGuild()).getName() + " genutzt");
    }

    private static void help(SlashCommandInteractionEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("🤖 • ZimbluBot Hilfe");
        eb.setDescription("'/help' - Zeigt dir diese Liste \n" +
                "'/stats' - zeigt dir ein paar stats zum Bot wie Server, RAM etc. \n" +
                "'/about' - Wer wir sind und was wir machen \n" +
                "'/status' - Zeigt dir an welche Server online sind");
        eb.setFooter("\uD83E\uDD16 • ZimbluBot", event.getJDA().getSelfUser().getAvatarUrl());
        eb.setColor(Color.BLUE);
        eb.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        event.replyEmbeds(eb.build()).queue();
    }

    private static void stats(SlashCommandInteractionEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("🤖 • ZimbluBot Status");
        eb.setDescription(null);
        eb.addField("Aktueller Nutzer", event.getJDA().getSelfUser().getName(), true);
        eb.addField("Aktuelle Server Zahl", String.valueOf(event.getJDA().getGuilds().size()), true);
        eb.addField("Arbeitsspeicher", getRAM() + "MB / 1024MB", false);
        eb.addField("Mitglieder", String.valueOf(event.getGuild().getMembers().size()), false);
        eb.addField("Boosts", String.valueOf(event.getGuild().getBoostCount()), true);
        eb.setFooter("\uD83E\uDD16 • ZimbluBot", event.getJDA().getSelfUser().getAvatarUrl());
        eb.setColor(Color.BLUE);
        eb.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        event.replyEmbeds(eb.build()).queue();
    }

    public static int getRAM() {
        Runtime runtime = Runtime.getRuntime();
        long freeMem = runtime.freeMemory();
        long totalMem = runtime.totalMemory();

        long usedMem = totalMem - freeMem;

        return (int) Math.floor((usedMem / 1024) / 1024);
    }

    private static void about(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Über das Zimblu Netzwerk");
        eb.setDescription("Zimblu bietet ein außergewöhnliches Minecraft-Roleplay-Erlebnis mit fast\n" +
                "ausschließlich selbst entwickelten Inhalten, die eine tiefgreifende und immersive\n" +
                "Welt schaffen. Neben Roleplay bieten wir auch andere spannende Spielmodi an.\n" +
                "Unser Netzwerk ist für Java- und Bedrock-Spieler zugänglich.");
        eb.setColor(Color.GREEN);
        event.replyEmbeds(eb.build()).queue();
    }

    private static void status(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Status");
        String description = "Proxy: ";
        if (Uptimes.ping(Main.hostname, 25565)) {
            description += "✅\n";
        } else {
            description += "❌\n";
        }

        if (Uptimes.ping(Main.hostname, 25566)) {
            description += "Lobby: ✅\n";
        } else {
            description += "Lobby: ❌\n";
        }

        if (Uptimes.ping(Main.hostname, 25568)) {
            description += "Roleplay: ✅\n";
        } else {
            description += "Roleplay: ❌\n";
        }
        eb.setDescription(description);
        eb.setColor(Color.GREEN);
        eb.setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl());
        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
    }

    private static void sendStatus(SlashCommandInteractionEvent event) {
        if (!(Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR))) {
            event.reply("Du hast dafür keine Rechte").setEphemeral(true).queue();
            return;
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Status");
        String description = "Proxy: ";
        if (Uptimes.ping(Main.hostname, 25565)) {
            description += "✅\n";
        } else {
            description += "❌\n";
        }

        if (Uptimes.ping(Main.hostname, 25566)) {
            description += "Lobby: ✅\n";
        } else {
            description += "Lobby: ❌\n";
        }

        if (Uptimes.ping(Main.hostname, 25568)) {
            description += "Roleplay: ✅\n";
        } else {
            description += "Roleplay: ❌\n";
        }

        eb.setDescription(description);
        eb.setColor(Color.GREEN);
        eb.setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl());
        event.reply("✅ » Erfolgreich. Setze in der Config die ID der Nachricht, das sie automatisch aktualisiert wird.").setEphemeral(true).queue();
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    private static void repeat(SlashCommandInteractionEvent event) {
        if (!(Objects.requireNonNull(event.getMember()).getUser().getName().equalsIgnoreCase("construkter") || Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR))) {
            event.reply("Du hast dafür keine Rechte").setEphemeral(true).queue();
            return;
        }
        String message = Objects.requireNonNull(event.getOption("message")).getAsString();
        event.getChannel().sendMessage(message).queue();
        event.reply("Erfolgreich").setEphemeral(true).queue();
    }
}