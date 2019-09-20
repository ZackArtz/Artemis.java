package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import music.GuildMusicManager;
import music.PlayerManager;
import music.TrackScheduler;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Skip a item in a array and play the next item.
 */

public class Skip extends Command {
    public Skip() {
        this.name = "skip";
        this.aliases = new String[]{"s"};
        this.help = "Skip a song, or 20";
        this.category = new Category("Music");
    }

    @Override
    protected void execute(CommandEvent event) {
        /*
        Define Vars.
         */
        TextChannel channel = (TextChannel) event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        /*
        Check if the player is playing something, obv.
         */

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player isn't playing anything!").queue();

            return;
            // The return here stops the command from executing anything else if the statement above is true.
        }

        scheduler.nextTrack();

        channel.sendMessage("Skipped 1 song!").queue();
    }
}
