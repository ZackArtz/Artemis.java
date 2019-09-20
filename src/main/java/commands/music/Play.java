package commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import secret.InfoUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * The job of the play command is to get arguments from the user, turn that into a link,
 * talk to lavaplayer and connect to VC, then start playing the song.
 */


public class Play extends Command {
    private final YouTube youTube;

    public Play() {
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.arguments = "[song]";
        this.help = "Joins the VC";
        this.category = new Category("Music");

        YouTube search = null;

        try {
            search = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("Menudocs JDA tutorial bot")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        youTube = search;
    }



    @Override
    protected void execute(CommandEvent event) {

        /*
        Define Vars
         */

        TextChannel channel = (TextChannel) event.getChannel();

        String[] args = event.getArgs().split(" ");

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

        /*
        If there isn't a song provided, tell the user to provide one.
         */
        if (event.getArgs().equalsIgnoreCase("")) {
            channel.sendMessage("Please provide a song!").queue();

            return;
        }

        /*
        Begin YT search
         */
        String input = String.join(" ", args);

        if (!isUrl(input)) {
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
                channel.sendMessage("Youtube returned no results").queue();

                return;
            }

            input = ytSearched;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay((TextChannel) event.getChannel(), input);

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
    }

    /*
    These functions down here are for checking if a String is a url, and the searchYoutube one is self-explanatory.
     */

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    private String searchYoutube(String input) {
        String result = null;
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(InfoUtil.G_API_KEY)
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                result = "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
