package libs;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * CommandListener class overwrites the behavior of onMessageReceived() method
 * after a message (command, starts with "!") is sent by a member. It includes
 * few other methods for decompose the content of the command.
 *
 */
public class CommandListener extends ListenerAdapter {

    private AudioCommandHandler audioCommandHandler;
    private TextCommandHandler textCommandHandler;

    public String getCommand(Queue<String> stringStack) {
        return stringStack.remove();
    }

    public Hashtable<String, String> getParams(Queue<String> stringStack) throws IllegalArgumentException {
        String arg, val;
        Hashtable<String, String> argVals = new Hashtable<>();

        while (!stringStack.isEmpty()) {
            arg = stringStack.remove();
            if (!arg.startsWith("--")) break;
            val = stringStack.remove();
            argVals.put(arg, val);
        }

        return argVals;
    }


    /**
     * Call when a Message is received in either a guild- or private channel
     * @param event
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String msg = message.getContentRaw();

        if (!msg.startsWith("!")) return;

        Queue<String> cmdParams = queueIn(msg);
        String cmd = getCommand(cmdParams);

        switch (cmd) {
            case "!ping":
                TextCommandHandler.pingPongCommand(event);
            case "!disconnect":
                audioCommandHandler.disconnectVoiceChannel();
                break;
            case "!join":
                AudioCommandHandler.joinVoiceChannel(event);
                break;
            case "!sam":
                audioCommandHandler.samTextToSpeech(msg);
            default:
                break;
        }
    }

    /**
     * Call and GuildReadyEvent is triggered if a guild finished setting up
     * during login phase.
     *
     * @param event
     */
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        if (audioCommandHandler == null)
            audioCommandHandler = new AudioCommandHandler(event.getGuild());
        if (textCommandHandler == null)
            textCommandHandler = new TextCommandHandler(event.getGuild());
    }

    static public Queue<String> queueIn(String request) {
        return new LinkedList<>(Arrays.asList(request.split(" ")));
    }
}
