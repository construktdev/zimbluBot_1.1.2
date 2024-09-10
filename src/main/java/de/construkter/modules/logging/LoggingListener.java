package de.construkter.modules.logging;

import de.construkter.utils.Logger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class LoggingListener extends ListenerAdapter {

    private final Map<String, String> messageCache = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String messageId = message.getId();
        String messageContent = message.getContentRaw();

        // Cache the message content
        messageCache.put(messageId, messageContent);
    }
    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
       Message message = event.getMessage();
        String contentNew = event.getMessage().getContentRaw();
        String contentOld = message.getContentDisplay();
        DiscordLogger.log("MessageUpdate", event.getAuthor(), event.getChannel().asTextChannel(), event.getJDA(), contentOld, contentNew, null);
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        String messageId = event.getMessageId();

        if (messageCache.containsKey(messageId)) {
            String deletedMessageContent = messageCache.get(messageId);
            DiscordLogger.log("MessageDelete", null, event.getChannel().asTextChannel(), event.getJDA(), null, null, deletedMessageContent);
        } else {
            Logger.event("Message was not cached.");
        }
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent event) {
        Channel channel = event.getChannel();
        DiscordLogger.log("ChannelCreate", null, event.getChannel().asTextChannel(), event.getJDA(), null, null, null);
    }

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        Channel channel = event.getChannel();
        DiscordLogger.log("ChannelDelete", null, event.getChannel().asTextChannel(), event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildBan(GuildBanEvent event) {
        DiscordLogger.log("GuildBan", event.getUser(), null, event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        DiscordLogger.log("GuildUnban", event.getUser(), null, event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        DiscordLogger.log("GuildMemberLeave", event.getUser(), null, event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        DiscordLogger.log("GuildMemberJoin", event.getUser(), null, event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
        DiscordLogger.log("GuildUpdateIcon", null, null, event.getJDA(), null, null, null);
    }

    @Override
    public void onGuildUpdateName(GuildUpdateNameEvent event) {
        DiscordLogger.log("GuildUpdateName", null, null, event.getJDA(), null, null, null);
    }
}
