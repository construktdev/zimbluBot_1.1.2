package de.construkter.modules.tempChannels;

import de.construkter.ressources.BotConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TempChannelsChecker {

    private final BotConfig config = new BotConfig();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startChannelChecker(JDA jda) {
        scheduler.scheduleAtFixedRate(() -> {

            // Hier wird die Channel-ID aus der Konfiguration geladen
            String tempCreateChannelId = config.getProperty("temp-create-channel");
            String tempCategoryId = config.getProperty("temp-category-id");

            // Hole die Gilde (Server)
            Guild guild = jda.getGuildById(config.getProperty("guild-id"));
            if (guild == null) {
                System.out.println("Gilde nicht gefunden.");
                return;
            }

            // Hole den Sprachkanal, der überwacht werden soll
            VoiceChannel voiceChannel = guild.getVoiceChannelById(tempCreateChannelId);
            if (voiceChannel == null) {
                System.out.println("Sprachkanal nicht gefunden.");
                return;
            }

            // Prüfe, ob sich jemand im Sprachkanal befindet
            Member m = guild.getMemberById("1147942681620795452"); // Teste mit deiner eigenen ID
            if (m != null && m.getVoiceState() != null && m.getVoiceState().inAudioChannel()) {
                System.out.println(m.getEffectiveName() + " befindet sich in einem Sprachkanal.");
            } else {
                System.out.println(m.getEffectiveName() + " befindet sich in keinem Sprachkanal.");
            }

            List<Member> members = voiceChannel.getMembers();
            if (!members.isEmpty()) {
                System.out.println("Es sind Mitglieder im Kanal " + voiceChannel.getName());

                for (Member member : members) {
                    User user = member.getUser();
                    System.out.println("Mitglied " + user.getName() + " ist im Kanal.");

                    // Erstelle einen temporären Kanal für das Mitglied und verschiebe es dorthin
                    Objects.requireNonNull(guild.getCategoryById(tempCategoryId))
                            .createVoiceChannel(user.getName()).queue(tempVoiceChannel -> {
                                guild.moveVoiceMember(member, tempVoiceChannel).queue();
                                System.out.println("Mitglied " + user.getName() + " wurde in den temporären Kanal verschoben.");
                            });
                }
            } else {
                System.out.println("Der Kanal " + voiceChannel.getName() + " ist leer.");
            }

        }, 0, 3, TimeUnit.SECONDS); // Überprüfe alle 3 Sekunden
    }
}
