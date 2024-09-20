package de.construkter.events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getJDA().getGuildById(1199634208524599347L);
        assert guild != null;
        event.getJDA().getPresence().setActivity(Activity.watching(guild.getMembers().size() + " Members"));
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        Guild guild = event.getJDA().getGuildById(1199634208524599347L);
        assert guild != null;
        event.getJDA().getPresence().setActivity(Activity.watching(guild.getMembers().size() + " Members"));
    }
}
