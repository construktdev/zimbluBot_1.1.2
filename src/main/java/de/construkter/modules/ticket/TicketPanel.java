package de.construkter.modules.ticket;

import de.construkter.utils.ColorManager;
import de.construkter.utils.FakeJSONResponse;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.Objects;

public class TicketPanel extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("send-panel")) {
            if (!(Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR))) {
                event.reply("❌ • Du brauchst für diese Aktion Administrator-Rechte").setEphemeral(true).queue();
                Logger.event(event.getMember().getNickname() + " versuchte ein Ticket Panel zu senden. Response: " + FakeJSONResponse.setResponse(403, "error", "Du hast keine Rechte diesen Befehl zu nutzen"));
                return;
            }
            Button ticketgeneral = Button.primary("ticketgeneral", "🛠️ • Allgemein");
            Button ticketreport = Button.danger("ticketreport", "❌ • Spieler melden");
            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("🎫 • Ticket Support")
                    .setDescription("Herzlich Willkommen im Support Bereich des **Zimblu Netzwerks**" +
                            "\n" +
                            "\n" +
                            "Wähle unten aus was der Grund ist, weshalb du mit uns Kontakt aufnehmen möchtest." +
                            "\n" +
                            "\n" +
                            "Der Missbrauch dieses Systems ist Strafbar!")
                    .setFooter("🤖 • ZimbluBot Tickets")
                    .setColor(Color.BLUE);
            TextChannel channel = event.getChannel().asTextChannel();
            channel.sendMessageEmbeds(eb.build())
                    .addActionRow(ticketgeneral, ticketreport)
                    .queue();
            event.reply("✅ • Ticket Panel gesendet").setEphemeral(true).queue();
            Logger.event(event.getMember().getNickname() + " hat ein neues ticket Panel in " + channel.getId() + " erstellt");
        }
    }
}
