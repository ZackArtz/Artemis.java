package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class BalladsCommand extends Command {

    public BalladsCommand() {
        this.name = "ballads";
        this.help = "Play ballads 1 at max volume";
        this.guildOnly = true;
        this.isHidden();
    }


    @Override
    protected void execute(CommandEvent event) {
        /*
        Define Vars
         */

        TextChannel channel = (TextChannel) event.getChannel();

        AudioManager audioManager = event.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        /*
        Basic If statements, checking for users being in a vc, etc.
         */

        assert memberVoiceState != null;
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Please join a VC to play music!").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        assert voiceChannel != null;
        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessage("I am unable to connect to the channel!").queue();
            return;
        }

        /*
        Connect to the channel.
         */

        audioManager.openAudioConnection(voiceChannel);

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay((TextChannel) event.getChannel(), "https://www.youtube.com/playlist?list=PLoMFXBI1wdsoANYWRsvmQ80Ee2jeWSXxt");

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(100000);
    }

}