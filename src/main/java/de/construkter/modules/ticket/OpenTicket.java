package de.construkter.modules.ticket;

import de.construkter.ressources.BotConfig;
import de.construkter.utils.ColorManager;
import de.construkter.utils.FakeJSONResponse;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Objects;

public class OpenTicket extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        BotConfig config = new BotConfig();
        if (event.getComponentId().equals("ticketgeneral")) {
            createTicket(Objects.requireNonNull(event.getMember()), "general", Objects.requireNonNull(event.getGuild()));
        } else if (event.getComponentId().equals("ticketreport")) {
            createTicket(Objects.requireNonNull(event.getMember()), "reprt",  Objects.requireNonNull(event.getGuild()));
        } else if (event.getComponentId().equals("close")) {
            event.reply("Das Ticket wird in wenigen Sekunden gelöscht!").queue();
            try {
                Thread.sleep(3546);
            } catch (InterruptedException e) {
                Logger.event(ColorManager.RED + " [!] " + ColorManager.GREEN + "Fehler beim löschen des Channels");
            }
            event.getChannel().delete().queue();
            Logger.event(Objects.requireNonNull(event.getMember()).getEffectiveName() + " hat das Ticket " + event.getChannel().getName() + " geschlossen!");
        } else if (event.getComponentId().equals("claim")) {
            if (Objects.requireNonNull(event.getMember()).getRoles().contains(Objects.requireNonNull(event.getGuild()).getRoleById(config.getProperty("mod-role-id")))) {
                TextChannel c = event.getChannel().asTextChannel();
                c.sendMessage(event.getMember().getAsMention() + " hat das Ticket übernommen").queue();
                Logger.event(event.getMember().getEffectiveName() + " hat das Ticket " + event.getChannel().getName() + " beansprucht");
                event.reply("✅ • Erfolgreich").setEphemeral(true).queue();
            } else {
                Logger.event(event.getMember().getEffectiveName() + " versuchte das Ticket " + event.getChannel().getName() + " zu beanspruchen. Response: " + FakeJSONResponse.setResponse(403, "error", "Missing Permission: de.construkter.permissions.zimblu.ticket.claim"));
                event.reply("❌ • Du brauchst für diese Aktion das Recht 'de.construkter.permissions.zimblu.ticket.claim'").setEphemeral(true).queue();
            }
        }
    }

    private static void createTicket(@NotNull Member member, String id, Guild target) {
        BotConfig config = new BotConfig();
        Category tickets = target.getCategoryById(config.getProperty("ticket-category-id"));
        Role modRole = target.getRoleById(config.getProperty("mod-role-id"));
        Role everyone = target.getPublicRole();
        assert tickets != null;
        assert modRole != null;

        ChannelAction<TextChannel> action = tickets.createTextChannel("ticket-" + id + " " + member.getEffectiveName());

        action.addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                .addPermissionOverride(modRole, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                .addPermissionOverride(everyone, null, EnumSet.of(Permission.VIEW_CHANNEL));

        action.queue(ticket -> {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Willkommen in deinem Ticket, " + member.getEffectiveName());
            eb.setDescription("Je nach Uhrzeit antworten wir sehr schnell. Schildere bitte in dieser zeit dein Problem. \n" +
                    "Wenn nach 10 Minuten nix geschrieben wurde, werden wir das Ticket wieder schließen.\n" +
                    "Das pingen von Teammitgliedern lohnt sich auch nicht, da so die Priorität deines Tickets nach unten gesetzt wird.");
            eb.setFooter("🤖 • ZimbluBot Tickets");
            eb.setColor(Color.BLUE);
            Button claim = Button.success("claim", "🧷 • Claim");
            Button close = Button.danger("close", "❌ • Schließen");
            ticket.sendMessageEmbeds(eb.build())
                    .addActionRow(claim, close)
                    .queue();
            ticket.sendMessage("@everyone").queue();
            Logger.event("Ticket " + ticket.getName() + " erfolgreich erstellt!");
        });
    }

    public static String extractName(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String[] parts = input.split("-");
        return parts[parts.length - 1];
    }
}
