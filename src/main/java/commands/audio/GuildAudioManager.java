package commands.audio;

import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.net.URL;

public class GuildAudioManager {

    private final Guild guild;
    private final JdaLink link;
    private final LavalinkPlayer player;
    private final TrackScheduler scheduler;

    public GuildAudioManager(Guild guild) {
        this.guild = guild;
        // TODO: I dunno chief, this is your job.
        this.link = null;
        this.player = link.getPlayer();
        this.scheduler = new TrackScheduler(guild, player);
        this.player.addListener(scheduler);
    }

    public JdaLink getLink() {
        return link;
    }

    public LavalinkPlayer getPlayer() {
        return player;
    }

    public void resetPlayer(Guild guild) {
        link.resetPlayer();
    }

    public void openConnection(VoiceChannel channel) {
        link.connect(channel);
    }

    public void closeConnection(Guild guild) {
        link.disconnect();
    }

    public void destroy() {
        link.destroy();
        AudioManagerController.removeGuildAudioManger(guild);
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }
}
