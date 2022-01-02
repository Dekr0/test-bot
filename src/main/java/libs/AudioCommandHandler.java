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

    private final Guild guild;
    private final GuildAudioManager manager;

    public AudioCommandHandler(Guild guild) {
        this.guild = guild;
        manager = new GuildAudioManager(guild);
    }

    public void disconnectVoiceChannel() {
        guild.getAudioManager().closeAudioConnection();
    }

    public void samTextToSpeech(String cmd) {
        try {
            String path = Sam.createWAV(cmd);
            if (path != null)
                sendSamAudioFile(path);
            else
                System.out.println("Playing audio file");
        } catch (IllegalArgumentException ie) {
            System.err.println(ie.getMessage());
        }

    }

    private void sendSamAudioFile(String filepath) {
        TextChannel botChannel = guild.getTextChannelById(926971913266401411L);
        File samAudioFile = new File(filepath);

        assert botChannel != null;

        if (samAudioFile.exists()) {
            botChannel.sendFile(samAudioFile).queue();
        } else {
            botChannel.sendMessage("Invalid Argument").queue();
        }
    }

    static public void joinVoiceChannel(MessageReceivedEvent event) {
        Member inVoiceMember = event.getMember();

        try {
            assert inVoiceMember != null;
            GuildVoiceState memberVoiceState = inVoiceMember.getVoiceState();

            assert memberVoiceState != null;
            AudioChannel audioChannel = memberVoiceState.getChannel();

            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.openAudioConnection(audioChannel);

        } catch (AssertionError a) {
            System.err.println(a.getMessage());
        }
    }

}
