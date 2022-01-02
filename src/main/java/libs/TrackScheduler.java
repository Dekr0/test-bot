package libs;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * TrackScheduler class handle the received events triggered by an AudioPlayer
 * instance.
 *
 * It's responsible for scheduling tracks by overwriting the behavior some
 * functions when corresponded events is triggered.
 */

/**
 * Producer and Consumer model -> Queue (Blocking)
 * Producer = members / users who feed audio request into the bot
 * Consumer = AudioPlayer, consume audio request from member / users
 */
public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;

        queue = new LinkedBlockingQueue<>();
    }

    /** Credit: from lavaplayer-master demo-jda
     * Add the next track to queue or play right away if nothing is in the queue.
     * @param track The track to play or add to queue
     */
    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) queue.offer(track);
    }


    /** Credit: from lavaplayer-master demo-jda
     * Start the next track, stopping the current one if it is playing
     *
     * Start the next track, regardless of if something is already playing or
     * not. In case queue was empty, we are giving null to startTrack, which is
     * a valid argument and will simply stop the player.
     * @param player
     */
    public void nextTrack() {
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    /** Credit: from lavaplayer-master demo-jda
     * Only start the next track if the end reason is suitable for it
     * (FINISHED or LOAD_FAILED)
     * @param player
     * @param track
     * @param endReason
     */
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}
