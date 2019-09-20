package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import utils.EmbedUtils;

import java.util.concurrent.TimeUnit;

/*
The job of this one is to show the nowplaying song in a embed.
 */

public class NowPlaying extends Command {
    public NowPlaying() {
        this.name = "nowplaying";
        this.aliases = new String[]{"np"};
        this.help = "Shows what's currently playing!";
        this.category = new Category("Music");
    }

    @Override
    protected void execute(CommandEvent event) {
        /*
        Define vars with the basic if-statements, as usual.
         */
        TextChannel channel = (TextChannel) event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The bot is not playing anything").queue();

            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        /*
        Some nice String formatting here, %s replaced with whatever comes after the string in order.
        Still not as nice as JS's formatting tho.
         */

        channel.sendMessage(EmbedUtils.embed(String.format(
                "**Playing** [%s]{%s}\n %s %S - %s",
                info.title,
                info.uri,
                player.isPaused() ? "\u23F8" : "▶",
                formatTime(player.getPlayingTrack().getPosition()),
                formatTime(player.getPlayingTrack().getDuration())
        ))).queue();
    }

    // Format the time to something readable.
    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
