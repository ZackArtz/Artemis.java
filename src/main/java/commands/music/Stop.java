package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * This command stops the bot, and just interfaces with lavaplayer and the classes we built
 * to achieve that.
 */

public class Stop extends Command {
    public Stop() {
        this.name = "stop";
        this.aliases = new String[]{"st"};
        this.help = "Leaves the VC";
        this.category = new Category("Music");
    }

    @Override
    protected void execute(CommandEvent event) {
        /*
        Define vars.
         */
        TextChannel channel = (TextChannel) event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        /*
        Check if the bot is connected before we stop it.
         */

        if (!audioManager.isConnected()) {
            channel.sendMessage("I'm not connected to a VC").queue();
            return;
        }

        /*
        Stop the bot, clear the queue, and make sure the bot is not paused.
         */
        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        audioManager.closeAudioConnection();
        channel.sendMessage("Leaving!").queue();
    }
}
