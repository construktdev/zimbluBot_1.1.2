package de.construkter.modules.ticket;

import de.construkter.utils.ColorManager;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.Objects;

public class CloseCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("close")) {
            if (event.getChannel().getName().contains("ticket-")) {
                Button confirm = Button.success("confirmClose", "✅ • Ja, schließen");
                Button cancel = Button.primary("cancelClose", "❌ • Nein, offen lassen");
                event.reply("Bist du dir sicher?").addActionRow(confirm, cancel).queue();
            } else {
                event.reply("❌ » Du musst dich in einem Ticket befinden.").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("confirmClose")) {
            event.reply("Das Ticket wird in wenigen Sekunden gelöscht.").queue();
            try {
                Thread.sleep(3546);
            } catch (InterruptedException e) {
                Logger.event(ColorManager.RED + " [!] " + ColorManager.GREEN + "Fehler beim löschen des Channels");
            }
            event.getMessage().delete().queue();
            event.getChannel().delete().queue();
            Logger.event(Objects.requireNonNull(event.getMember()).getEffectiveName() + " hat das Ticket " + event.getChannel().getName() + " geschlossen.");
        } else if (event.getComponentId().equals("cancelClose")) {
            event.getMessage().delete().queue();
            event.deferReply(true).queue();
            event.reply("✅ • Erfolgreich").queue();
        }
    }
}
