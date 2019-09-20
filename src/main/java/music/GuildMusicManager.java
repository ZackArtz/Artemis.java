package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Pretty simple class, just fracturing the bot so its per-guild (server)
 * More info in PlayerManager.java
 */

public class GuildMusicManager {
    public final AudioPlayer player;

    public final TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }


    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}
