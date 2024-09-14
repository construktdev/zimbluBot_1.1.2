package de.construkter.modules.tempChannels;

import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VoiceListener extends ListenerAdapter {
    @Override
    public void onGenericGuildVoice(@NotNull GenericGuildVoiceEvent event) {
        System.out.println("Voice event: " + event.getClass().getSimpleName());
        System.out.println("Guild: " + event.getGuild().getName());
        System.out.println("Member: " + event.getMember().getEffectiveName());
    }
}
