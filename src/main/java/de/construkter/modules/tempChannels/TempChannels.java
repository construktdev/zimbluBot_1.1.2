package de.construkter.modules.tempChannels;

import de.construkter.ressources.BotConfig;
import de.construkter.utils.ColorManager;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TempChannels extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        BotConfig config = new BotConfig();
        Logger.event("Received VoiceUpdate Event");

        Guild guild = event.getGuild();
        List<VoiceChannel> voiceChannels = guild.getVoiceChannels();

        // Überprüfe alle Sprachkanäle
        for (VoiceChannel voiceChannel : voiceChannels) {
            List<Member> members = voiceChannel.getMembers();

            // Logge die Mitglieder in jedem Kanal
            System.out.println("Kanal: " + voiceChannel.getName() + " hat " + members.size() + " Mitglieder.");
            for (Member member : members) {
                System.out.println("Mitglied: " + member.getEffectiveName() + " ist im Kanal " + voiceChannel.getName());
            }
        }

        // Wenn der Benutzer einem Kanal beigetreten ist und nicht gleichzeitig einen verlassen hat
        if (event.getChannelJoined() != null && event.getChannelLeft() == null) {
            if (!event.getChannelJoined().getId().equals(config.getProperty("temp-create-channel"))) {
                return;
            }

            User user = event.getMember().getUser();
            System.out.println("Benutzer " + user.getName() + " ist dem temp-create-channel beigetreten.");

            // Erstelle einen neuen temporären Sprachkanal und verschiebe den Benutzer
            Objects.requireNonNull(guild.getCategoryById(config.getProperty("temp-category-id")))
                    .createVoiceChannel(user.getName()).queue(voiceChannel -> {
                        guild.moveVoiceMember(event.getMember(), voiceChannel).queue();
                    });
        }

        // Wenn der Benutzer einen Kanal verlassen hat und gleichzeitig einem anderen beigetreten ist
        if (event.getChannelLeft() != null && event.getChannelJoined() != null) {
            if (event.getChannelLeft().getName().equals(event.getMember().getUser().getName())) {
                System.out.println("Benutzer " + event.getMember().getUser().getName() + " hat den Kanal verlassen: " + event.getChannelLeft().getName());

                // Warte 30 Sekunden, bevor der Kanal gelöscht wird
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    System.out.println("Lösche den temporären Kanal: " + event.getChannelLeft().getName());
                    event.getChannelLeft().delete().queue();
                }, 30, TimeUnit.SECONDS);
            }
        }
    }
}