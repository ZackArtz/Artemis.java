package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Changes the volume.
 * Not much more to say.
 */

public class Volume extends Command {
    public Volume() {
        this.name = "volume";
        this.aliases = new String[]{"v"};
        this.help = "Change the volume for the current player";
        this.arguments = "[volume]";
        this.category = new Category("Music");
    }

    @Override
    protected void execute(CommandEvent event) {
        /*
          Define Variables.
         */
        TextChannel channel = (TextChannel) event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
        String[] args = event.getArgs().split(" ");
        // The command manager I use likes to deliver args as one string, and I want them as a array, so we use .split(" ") here to break it into a array.
        // The " " says "break it wherever there's a space. So "howdy gamers" would become {"howdy", "gamers"}
        guildMusicManager.player.setVolume(Integer.parseInt(args[0]));
        // Thanks to lavaplayer we can do things like .setVolume with minimal code.
        channel.sendMessage("Changed volume to **" + args[0] + "**").queue();
        // Add the ** to bold the text.
    }
}
