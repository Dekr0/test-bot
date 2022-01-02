package libs;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.util.Hashtable;
import java.util.Queue;


/**
 * AudioCommandHandler is a class that only handle command related to audio and
 * voice channel.
 */
public class AudioCommandHandler extends CommandHandler {

    static public void disconnectVoiceChannel(Guild guild) {
        guild.getAudioManager().closeAudioConnection();
    }

    static void samTextToSpeech(Guild guild, String cmd) {
        try {
            String path = Sam.createWAV(cmd);
            if (path != null)
                sendSamAudioFile(guild, path);
            else
                System.out.println("Playing audio file");
        } catch (IllegalArgumentException ie) {
            System.err.println(ie.getMessage());
        }

    }

    static private void sendSamAudioFile(Guild guild, String filepath) {
        TextChannel botChannel = guild.getTextChannelById(926971913266401411L);
        File samAudioFile = new File(filepath);

        assert botChannel != null;

        if (samAudioFile.exists()) {
            botChannel.sendFile(samAudioFile).queue();
        } else {
            botChannel.sendMessage("Invalid Argument").queue();
        }
    }

    static public void joinVoiceChannel(Member inVoiceMember, Guild guild) {
        try {
            assert inVoiceMember != null;
            GuildVoiceState memberVoiceState = inVoiceMember.getVoiceState();

            assert memberVoiceState != null;
            AudioChannel audioChannel = memberVoiceState.getChannel();

            AudioManager audioManager = guild.getAudioManager();
            audioManager.openAudioConnection(audioChannel);

        } catch (AssertionError a) {
            System.err.println(a.getMessage());
        }
    }

}
