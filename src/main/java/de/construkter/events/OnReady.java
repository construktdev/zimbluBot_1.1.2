package de.construkter.events;

import de.construkter.ressources.BotConfig;
import de.construkter.utils.ColorManager;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class OnReady extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        BotConfig config = new BotConfig();
        Logger.event("Bot is ready!");
        Logger.event(event.getJDA().getInviteUrl());
        System.out.println("");
        Logger.event("Servers: " + event.getJDA().getGuilds().size());
        Logger.event("Status: " + event.getJDA().getStatus());
        Logger.event(ColorManager.BOLD + "Loading Done!");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("🧾 • ZimbluBot Logging");
        eb.setDescription("Bot loaded and is ready");
        eb.addField("Benutzer", "Internal Event", true);
        eb.addField("Channel", "Internal Event", true);
        eb.setColor(Color.BLUE);
        eb.setFooter("\uD83E\uDD16 • ZimbluBot Logger", event.getJDA().getSelfUser().getAvatarUrl());
        TextChannel logChannel = event.getJDA().getTextChannelById(config.getProperty("logging-channel"));
        assert logChannel != null;
        logChannel.sendMessageEmbeds(eb.build()).queue();
        Guild guild = event.getJDA().getGuildById(1253435238273650728L);
        assert guild != null;
        event.getJDA().getPresence().setActivity(Activity.watching(guild.getMembers().size() + " Members"));
    }
}