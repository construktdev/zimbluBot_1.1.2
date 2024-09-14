package de.construkter.modules.embedBuilder;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
         if (event.getName().equals("embed")) {
             if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                 Modal modal = Modal.create("embed-builder", "Embed Builder")
                         .addComponents(
                                 ActionRow.of(TextInput.create("title", "Titel", TextInputStyle.SHORT).setRequired(true).build()),
                                 ActionRow.of(TextInput.create("description", "Beschriebung", TextInputStyle.PARAGRAPH).setRequired(true).build()),
                                 ActionRow.of(TextInput.create("footer", "Fußzeile", TextInputStyle.SHORT).setRequired(true).build()),
                                 ActionRow.of(TextInput.create("channel", "Kanal ID", TextInputStyle.SHORT).setRequired(true).build())
                         ).build();
                 event.replyModal(modal).queue();
             } else {
                 event.reply("❌ » Du hast dafür keine Rechte").setEphemeral(true).queue();
             }
         }
    }
}
