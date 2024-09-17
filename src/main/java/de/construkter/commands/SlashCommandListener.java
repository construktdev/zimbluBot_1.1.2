package de.construkter.commands;

import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
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
        }
        Logger.event(Objects.requireNonNull(event.getMember()).getEffectiveName() + " hat den Befehl /" + event.getName() + " in " + Objects.requireNonNull(event.getGuild()).getName() + " genutzt");
    }

    private static void help(SlashCommandInteractionEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("🤖 • ZimbluBot Hilfe");
        eb.setDescription("'/help' - Zeigt dir diese Liste \n" +
                "'/stats' - zeigt dir ein paar stats zum Bot wie Server, RAM etc.");
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

        int memUsageMB = (int) Math.floor((usedMem / 1024) / 1024);

        return memUsageMB;
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
}
