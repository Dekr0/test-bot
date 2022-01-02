package libs;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * TextCommandHandler is a class that specifically handle any type of
 * non-audio related command.
 */
public class TextCommandHandler extends CommandHandler{
    private final Guild guild;

    public TextCommandHandler(Guild guild) {
        this.guild = guild;
    }

    static public void pingPongCommand(MessageReceivedEvent event) {
        TextChannel currentChannel = event.getTextChannel();

        currentChannel.sendMessage("Pong").queue();
    }
}
