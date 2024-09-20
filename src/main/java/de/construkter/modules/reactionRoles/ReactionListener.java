package de.construkter.modules.reactionRoles;

import de.construkter.ressources.RolesConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class ReactionListener extends ListenerAdapter {
    EmbedBuilder eb = new EmbedBuilder();
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Member member = event.getMember();
        assert member != null;
        Emoji emoji = event.getEmoji();
        RolesConfig config = new RolesConfig();
        long messageId = Long.parseLong(event.getMessageId());

        eb.setTitle("Reaction Roles");
        eb.setDescription("Deine Rollen auf dem **Zimblu-Netzwerk** wurden erfolgreich aktualisiert!");
        eb.setTimestamp(Instant.now());
        eb.setColor(Color.pink);

        if (messageId == Long.parseLong(config.getProperty("message"))) {
            if (emoji.equals(Emoji.fromUnicode("📣"))) {
                //news
                if (member.getRoles().contains(event.getGuild().getRoleById(123456789012121212L))) {
                    member.getRoles().remove(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                } else {
                    member.getRoles().add(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                }
            } else if (emoji.equals(Emoji.fromUnicode("🤝"))) {
                //partner
                if (member.getRoles().contains(event.getGuild().getRoleById(12345678901212121L))) {
                    member.getRoles().remove(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                } else {
                    member.getRoles().add(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                }
            } else if (emoji.equals(Emoji.fromUnicode("✅"))) {
                //status updates
                if (member.getRoles().contains(event.getGuild().getRoleById(1234567890121212122L))) {
                    member.getRoles().remove(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                } else {
                    member.getRoles().add(event.getGuild().getRoleById(1234567890121212122L));
                    member.getUser().openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessageEmbeds(eb.build()).queue();
                    });
                }
            }
        }
    }
}
