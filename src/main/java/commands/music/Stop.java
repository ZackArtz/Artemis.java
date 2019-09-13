package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Stop extends Command {
    public Stop() {
        this.name = "stop";
        this.aliases = new String[]{"st"};
        this.help = "Leaves the VC";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        if (!audioManager.isConnected()) {
            channel.sendMessage("I'm not connected to a VC").queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        audioManager.closeAudioConnection();
        channel.sendMessage("Leaving!").queue();
    }
}
