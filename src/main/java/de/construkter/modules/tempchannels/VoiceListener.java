package de.construkter.modules.tempchannels;

import de.construkter.ressources.BotConfig;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class VoiceListener extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelLeft() == null && event.getChannelJoined() != null) {
            VoiceChannel channel = event.getChannelJoined().asVoiceChannel();
            BotConfig config = new BotConfig();
            if (channel.getId().equals(config.getProperty("temp-create-channel"))) {
                Category category = event.getGuild().getCategoryById(config.getProperty("temp-category-id"));
                assert category != null;
                category.createVoiceChannel(event.getMember().getUser().getName()).queue(voiceChannel -> {
                    event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue(success -> {
                        event.getMember().getUser().openPrivateChannel().queue(channel1 -> {
                            channel1.sendMessage("Du hast einen temporären Kanal erstellt! Klicke hier um beizutreten <#" + voiceChannel.getId() + ">").queue();
                        });
                    });
                });
            }
        } else if (event.getChannelLeft() != null && event.getChannelJoined() == null) {
            BotConfig config = new BotConfig();
            if (Objects.equals(event.getChannelLeft().getParentCategoryId(), config.getProperty("temp-category-id")) && event.getChannelLeft().getMembers().isEmpty()) {
                event.getChannelLeft().delete().queue();
            }
        }
    }
}
