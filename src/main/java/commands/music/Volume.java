package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

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
        TextChannel channel = (TextChannel) event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
        String[] args = event.getArgs().split(" ");
        guildMusicManager.player.setVolume(Integer.parseInt(args[0]));
        channel.sendMessage("Changed volume to **" + args[0] + "**").queue();
    }
}
