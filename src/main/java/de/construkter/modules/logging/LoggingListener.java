package de.construkter.modules.logging;

import de.construkter.utils.Logger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class LoggingListener extends ListenerAdapter {

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
       Message message = event.getMessage();
        String contentNew = event.getMessage().getContentRaw();
        String contentOld = message.getContentDisplay();
        DiscordLogger.log("MessageUpdate", event.getAuthor(), event.getChannel().asTextChannel(), event.getJDA(), contentOld, contentNew, null);
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {

    }
}
