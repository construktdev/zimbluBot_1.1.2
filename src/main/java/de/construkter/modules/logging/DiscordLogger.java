package de.construkter.modules.logging;

import de.construkter.ressources.BotConfig;
import de.construkter.utils.ColorManager;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class DiscordLogger extends ListenerAdapter {
    public static boolean log(String event, User user, TextChannel channel, JDA jda, @Nullable String before, @Nullable String after, @Nullable String content) {
        BotConfig config = new BotConfig();
        boolean loggingEnabled = false;

        if (user == null && channel == null) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("🧾 • ZimbluBot Logging");
            eb.setDescription(event);
            eb.addField("Benutzer", "Unable to fetch due to JDA limitations", true);
            eb.addField("Channel", "Unable to fetch due to JDA limitations", true);
            eb.setColor(Color.BLUE);
            eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
            TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
            assert logChannel != null;
            logChannel.sendMessageEmbeds(eb.build()).queue();
            return true;
        }

        if (user == null) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("🧾 • ZimbluBot Logging");
            eb.setDescription(event);
            eb.addField("Benutzer", "Unable to fetch due to JDA limitations", true);
            eb.addField("Channel", channel.getName(), true);
            eb.setColor(Color.BLUE);
            eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
            TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
            assert logChannel != null;
            logChannel.sendMessageEmbeds(eb.build()).queue();
            return true;
        }

        if (channel == null) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("🧾 • ZimbluBot Logging");
            eb.setDescription(event);
            eb.addField("Benutzer", user.getName(), true);
            eb.addField("Channel", "Unable to fetch due to JDA limitations", true);
            eb.setColor(Color.BLUE);
            eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
            TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
            assert logChannel != null;
            logChannel.sendMessageEmbeds(eb.build()).queue();
            return true;
        }

        try {
           loggingEnabled = Boolean.parseBoolean(config.getProperty("logging"));
        } catch (Exception e) {
            Logger.event(ColorManager.RED + "[!] " + ColorManager.GREEN + "Du hast einen falschen Wert bei bot.txt - logging eingegeben (true oder false)");
            return false;
        }
        if (loggingEnabled) {
            if (event.equals("MessageUpdate")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("🧾 • ZimbluBot Logging");
                eb.setDescription(event);
                eb.addField("Benutzer", user.getName(), true);
                eb.addField("Channel", channel.getName(), true);
                assert before != null;
                eb.addField("Vorher", before, false);
                assert after != null;
                eb.addField("Nachher", after, false);
                eb.setColor(Color.BLUE);
                eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
                TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
                assert logChannel != null;
                logChannel.sendMessageEmbeds(eb.build()).queue();
                return true;
            } else if (event.equals("MessageDelete")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("🧾 • ZimbluBot Logging");
                eb.setDescription(event);
                eb.addField("Benutzer", user.getName(), true);
                eb.addField("Channel", channel.getName(), true);
                assert content != null;
                eb.addField(" Nachricht", content, false);
                eb.setColor(Color.BLUE);
                eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
                TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
                assert logChannel != null;
                logChannel.sendMessageEmbeds(eb.build()).queue();
                return true;
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("🧾 • ZimbluBot Logging");
            eb.setDescription(event);
            eb.addField("Benutzer", user.getName(), true);
            eb.addField("Channel", channel.getName(), true);
            eb.setColor(Color.BLUE);
            eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", jda.getSelfUser().getAvatarUrl());
            TextChannel logChannel = jda.getTextChannelById(config.getProperty("logging-channel"));
            assert logChannel != null;
            logChannel.sendMessageEmbeds(eb.build()).queue();
            return true;
        } else {
            return false;
        }
    }
}
