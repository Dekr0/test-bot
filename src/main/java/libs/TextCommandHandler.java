package libs;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * TextCommandHandler is a class that specifically handle any type of
 * non-audio related command.
 */
public class TextCommandHandler extends CommandHandler{
    private final Guild guild;

    public TextCommandHandler(Guild guild) {
        this.guild = guild;
    }

    static public void pingPongCommand(TextChannel currentChannel) {
        currentChannel.sendMessage("Pong").queue();
    }

    static public boolean isBotChannel(TextChannel channel) {
        if (channel.getName().contains("bot")) return false;

        channel.sendMessage("Please send the command to a text channel specific for bot").queue();
        return true;
    }
}
