package de.construkter.commands;

import de.construkter.utils.FakeJSONResponse;
import de.construkter.utils.Logger;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextBasedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        switch (event.getMessage().getContentRaw()) {
            case "!stop":
                stop(event);
                break;
        }
    }

    private static void stop(MessageReceivedEvent event) {
        if (event.getAuthor().getName().equalsIgnoreCase("construkter")) {
            event.getMessage().delete().queue();
            Logger.event("Stopping...");
            event.getJDA().shutdown();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Logger.event("Failed to stop");
            }
            System.exit(130);
        } else {
            event.getMessage().delete().queue();
            Logger.event(event.getAuthor().getName() + " versuchte zu stoppen. Response: "  + FakeJSONResponse.setResponse(500, "error", "Missing Permission: de.construkter.permissions.zimblu.bot.poweroff"));
        }
    }
}
