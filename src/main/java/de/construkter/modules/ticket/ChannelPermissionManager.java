package de.construkter.modules.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;


public class ChannelPermissionManager extends ListenerAdapter {
    public static void closeTicket(Guild guild, TextChannel channel, Role role, JDA jda) {
        channel.upsertPermissionOverride(guild.getPublicRole())
                .deny(Permission.VIEW_CHANNEL)
                .queue();

        channel.upsertPermissionOverride(role)
                .grant(Permission.VIEW_CHANNEL)
                .queue();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Ticket geschlossen.");
        eb.setDescription("Dieses Ticket ist geschlossen und kann nurnoch von Moderatoren amgesehen werden. Nutze den Button um das Ticket zu löschen");
        eb.setFooter(" • Zimblu Bot", jda.getSelfUser().getAvatarUrl());
        Button delete = Button.danger("delete", "🗑️ » Löschen");
        channel.sendMessageEmbeds(eb.build()).addActionRow(delete).queue();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("delete")) {
            event.reply("Das Ticket wird in wenigen Sekunden gelöscht").setEphemeral(true).queue();
            try {
                Thread.sleep(7847);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.getChannel().delete().queue();
        }
    }
}