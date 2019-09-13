package commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import jdk.internal.jline.internal.Nullable;
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


public class Play extends Command {
    private final YouTube youTube;

    public Play() {
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.arguments = "[song]";
        this.help = "Joins the VC";

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

        TextChannel channel = (TextChannel) event.getChannel();

        String[] args = event.getArgs().split(" ");

        AudioManager audioManager = event.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

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

        audioManager.openAudioConnection(voiceChannel);
        channel.sendMessage("Joining VC!").queue();

        if (event.getArgs().equalsIgnoreCase("")) {
            channel.sendMessage("Please provide a song!").queue();

            return;
        }

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

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        String result = null;
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey("AIzaSyCBHZvzfh1j6YqykqsJrcafZBKy-ZwEhdE")
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
