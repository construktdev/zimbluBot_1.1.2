package de.construkter.modules.ticket;

import de.construkter.ressources.BotConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

public class CloseRequest extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("close-request")) {
            if (event.getChannel().getName().contains("ticket-")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Schließanfrage");
                eb.setDescription(event.getUser().getName() + " möchte diese Ticket schließen. Bitte beachte dass wenn du auf schließen drückst das Ticket für den Ticket-Besitzer nicht mehr verfügbar ist!");
                eb.setFooter(" • Zimblu Bot", event.getJDA().getSelfUser().getAvatarUrl());
                Button confirm = Button.success("confirmCloseRequest", "✅ • Ja, schließen");
                Button cancel = Button.primary("cancelCloseRequest", "❌ • Nein, offen lassen");
                event.replyEmbeds(eb.build()).addActionRow(confirm, cancel).queue();
            } else {
                event.reply("❌ » Du musst dich dafür in einem Ticket befinden").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        BotConfig config = new BotConfig();
        if (event.getComponentId().equals("confirmCloseRequest")) {
            ChannelPermissionManager.closeTicket(Objects.requireNonNull(event.getGuild()), event.getChannel().asTextChannel(), event.getGuild().getRoleById(config.getProperty("mod-role-id")), event.getJDA());
        } else if (event.getComponentId().equals("cancelCloseRequest")) {
            event.getMessage().delete().queue();
            event.reply("Dieses Ticket bleibt weiterhin offen").setEphemeral(true).queue();
        }
    }
}
