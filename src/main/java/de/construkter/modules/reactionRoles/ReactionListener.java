package de.construkter.modules.reactionRoles;

import de.construkter.ressources.RolesConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Member member = event.getMember();
        Emoji emoji = event.getEmoji();
        RolesConfig config = new RolesConfig();
        long messageId = Long.parseLong(event.getMessageId());

        if (messageId == Long.parseLong(config.getProperty("message"))) {
            
        }
    }
}
