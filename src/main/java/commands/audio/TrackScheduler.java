package commands.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lavalink.client.player.IPlayer;
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;

public class TrackScheduler extends PlayerEventListenerAdapter {


    private Guild guild;
    private AudioTrack bg = null, last = null;
    private boolean looping = false;
    private final LavalinkPlayer player;
    public final Queue<AudioTrack> queue;
    private ScheduledFuture timeout;


    TrackScheduler(Guild guild, LavalinkPlayer player) {
        this.guild = guild;
        this.player = player;
        this.queue = new LinkedList<>();
    }

    public boolean hasNextTrack() {
        return (queue.peek() != null);
    }

    public void nextTrack() {
        try {
            AudioTrack track = queue.poll();
            if (track == null) {
                if (bg != null) {
                    bg = bg.makeClone();
                    player.playTrack(bg);
                    return;
                }
//
//                timeout = ScheduleHandler.registerUniqueJob(new VoiceTimeoutJob(guild));

                return;
            }
            if (timeout != null) {
                timeout.cancel(true);
                timeout = null;
            }
            player.playTrack(track);

            // nowplaying message


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queue(AudioTrack audioTrack) {
        if (player.getPlayingTrack() == null || (bg != null && player.getPlayingTrack() == bg)) {
            queue.add(audioTrack);
            nextTrack();
            return;
        }
        queue.offer(audioTrack);
    }

    @Override
    public void onTrackEnd(IPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.last = track;

        if (endReason.mayStartNext) {
            if (looping) {
                queue.add(track);
                nextTrack();
                return;
            }
            nextTrack();
        }
    }

    @Override
    public void onTrackException(IPlayer player, AudioTrack track, Exception exception) {
        super.onTrackException(player, track, exception);
    }

    @Override
    public void onTrackStuck(IPlayer player, AudioTrack track, long thresholdMs) {
        super.onTrackStuck(player, track, thresholdMs);
    }

    public boolean isLooping() {
        return looping;
    }

    public AudioTrack getLast() {
        return last;
    }

//    public void shuffle() {
//        Collections.shuffle((List<?> queue);
//    }


    public void setBg(AudioTrack bg) {
        this.bg = bg;
    }
}
