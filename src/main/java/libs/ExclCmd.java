package libs;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;


public class ExclCmd extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Message from bot is ignored
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();  // Get message that triggered the event

        if (!message.getContentRaw().startsWith("!join")) return;

        String[] cmdAndParams = message.getContentRaw().split("!join");
        Member member = event.getMember();
        Guild guild = event.getGuild();
        String channelId = member.getVoiceState().getChannel().getId();
        VoiceChannel channel = guild.getVoiceChannelById(channelId);
        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(channel);
    }
}

