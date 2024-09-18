package de.construkter.modules.uptimeMonitor;

import de.construkter.ressources.BotConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

public class Uptimes {
    public static boolean ping(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);  // 2 seconds timeout
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void runTask(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        BotConfig config = new BotConfig();
        long guildId = 1253435238273650728L;
        String channelId = config.getProperty("embed-channel");
        String messageId = config.getProperty("embed-id");

        TextChannel channel = Objects.requireNonNull(event.getJDA().getGuildById(guildId)).getTextChannelById(channelId);

        if (channel != null) {
            channel.retrieveMessageById(messageId).queue(
                    (Message message) -> {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Status");

                        String description = "Proxy: ";
                        if (Uptimes.ping("127.0.0.1", 25565)) {
                            description += "✅\n";
                        } else {
                            description += "❌\n";
                        }

                        if (Uptimes.ping("127.0.0.1", 25566)) {
                            description += "Lobby: ✅\n";
                        } else {
                            description += "Lobby: ❌\n";
                        }

                        if (Uptimes.ping("127.0.0.1", 25567)) {
                            description += "Varo: ✅\n";
                        } else {
                            description += "Varo: ❌\n";
                        }

                        if (Uptimes.ping("127.0.0.1", 25568)) {
                            description += "JumpNRun: ✅\n";
                        } else {
                            description += "JumpNRun: ❌\n";
                        }

                        if (Uptimes.ping("127.0.0.1", 25569)) {
                            description += "SMP: ✅\n";
                        } else {
                            description += "SMP: ❌\n";
                        }

                        eb.setDescription(description);
                        eb.setColor(Color.GREEN);
                        eb.setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl());

                        message.editMessageEmbeds(eb.build()).queue();
                    },
                    (Throwable error) -> {
                        error.printStackTrace();
                    }
            );
        } else {
            System.out.println("Channel not found!");
        }
    }

}