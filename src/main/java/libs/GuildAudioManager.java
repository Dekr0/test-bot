package libs;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

/**
 * GuildAudioManager is a class that manage all the audio related class instances.
 *
 * 1.AudioPlayerManager
 *  - single instance
 *  - manage a set of instance
 *
 * 2.Each AudioPlayer
 *  - created from AudioPlayerManager
 *  - separate from different player
 *  - register to a listener, TrackScheduler
 *      - extends AudioEventAdapter
 *  - can play instances of AudioTrack
 *
 * 2-1.Load audio tracks
 *  - use AudioPlayerManager.loadItem to load a track
 *      - require an additional load handler
 *
 * 2-2.AudioLoadResultHandler
 *  - handle the events triggered by loading items, use TrackScheduler to
 *  handle the events since it responsible to schedule tracks
 *  - loading process can has different process
 *      - load a single track
 *      - load a playlist (a set of tracks)
 *      - load a single track but there's no match
 *      - load something but failed
 *
 * 3.TrackScheduler
 *  - Receives the events triggered by an audio player
 *  - Responsible to schedule tracks
 */
public class GuildAudioManager {

    private final AudioPlayerManager manager;
    private final AudioPlayer player;
    private final Guild guild;
    private final TrackLoadResultHandler handler;
    private final TrackScheduler scheduler;

    public GuildAudioManager(Guild guild) {
        this.guild = guild;

        manager = new DefaultAudioPlayerManager();
        manager.registerSourceManager(new LocalAudioSourceManager());

        player = manager.createPlayer();

        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);

        handler = new TrackLoadResultHandler();
    }

    public void loadTrack(String audioPath) {
        manager.loadItem(audioPath, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                scheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks())
                    scheduler.queue(track);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }
}
