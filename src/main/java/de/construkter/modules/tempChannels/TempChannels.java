package de.construkter.modules.tempChannels;

import de.construkter.ressources.BotConfig;
import de.construkter.utils.ColorManager;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class TempChannels extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        BotConfig config = new BotConfig();
        Logger.event("Recieved VoiceUpadet Event");
        if (event.getChannelJoined() != null && event.getChannelLeft() == null) {
            if (!(event.getChannelJoined().getId().equals(config.getProperty("temp-create-channel")))) {
                return;
            }
            User user = event.getMember().getUser();
            Objects.requireNonNull(event.getGuild().getCategoryById(config.getProperty("temp-category-id"))).createVoiceChannel(user.getName()).queue(voiceChannel -> {
                event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
            });
        } else if (event.getChannelLeft() != null && event.getChannelJoined() != null) {
            if (event.getChannelLeft().getName().equals(event.getMember().getUser().getName())) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    Logger.event(ColorManager.RED + "[!] " + ColorManager.GREEN + "Failed to delete temp channel");
                }
            }
        }
    }
}
