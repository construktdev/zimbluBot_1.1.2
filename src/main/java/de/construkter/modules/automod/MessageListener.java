package de.construkter.modules.automod;

import de.construkter.modules.automod.words.DontOpenMe;
import de.construkter.ressources.BotConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class MessageListener extends ListenerAdapter {
    BotConfig config = new BotConfig();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        TextChannel logChannel = event.getJDA().getTextChannelById(config.getProperty("logging-channel"));
        String[] badwords = DontOpenMe.getWords();
        for (String badword : badwords) {

            if (event.getMessage().getContentRaw().contains(badword)) {
                event.getMessage().delete().queue();
                Objects.requireNonNull(event.getMember()).getUser().openPrivateChannel().queue(
                        privateChannel -> {
                            EmbedBuilder embed = new EmbedBuilder();
                            embed.setTitle("Zimblu AutoMod");
                            embed.setDescription("Du hast ein verbotenes Wort genutzt! Wenn du dies erneut machst, wirst du bestraft.\n" +
                                    "Das Wort: ||" + badword + "||");
                            embed.setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl());
                            embed.setTimestamp(Instant.now());
                            embed.setColor(Color.RED);
                            privateChannel.sendMessageEmbeds(embed.build()).queue();
                        }
                );
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Zimblu AutoMod");
                eb.setDescription("Der Nutzer " + event.getMember().getEffectiveName() + " hat ein verbotenes Wort genutzt und wurde gewarnt\n" +
                        "Das Wort: ||" + badword + "||\n" +
                        "Kanal: <#" + event.getChannel().getId() + ">");
                eb.setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl());
                eb.setTimestamp(Instant.now());
                eb.setColor(Color.RED);
                assert logChannel != null;
                logChannel.sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}
