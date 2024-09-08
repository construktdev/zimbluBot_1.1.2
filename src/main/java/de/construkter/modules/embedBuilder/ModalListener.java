package de.construkter.modules.embedBuilder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class ModalListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("embed-builder")) {
            String title = Objects.requireNonNull(event.getValue("title")).getAsString();
            String description = Objects.requireNonNull(event.getValue("description")).getAsString();
            String footer = Objects.requireNonNull(event.getValue("footer")).getAsString();
            long cid = Long.parseLong(Objects.requireNonNull(event.getValue("channel")).getAsString());
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setFooter(footer, event.getJDA().getSelfUser().getAvatarUrl())
                    .setColor(Color.BLUE);
            Objects.requireNonNull(event.getJDA().getTextChannelById(cid)).sendMessageEmbeds(embed.build()).queue();
            event.reply("Sending the built Embed in <#" + cid + "> " + event.getUser().getAsMention()).queue();
        }
    }
}
