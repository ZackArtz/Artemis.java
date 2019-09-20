package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * This command is to show the queue, so we got a nice for loop in here.
 */

public class Queue extends Command {
    public Queue() {
        this.name = "queue";
        this.aliases = new String[]{"q"};
        this.help = "Shows a list of songs in the queue";
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
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        if (queue.isEmpty()) {
            channel.sendMessage("The queue is empty").queue();

            return;
        }

        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Current Queue (Total: " + queue.size() + ")")
                .setAuthor(event.getAuthor().getName())
                .setColor(Color.DARK_GRAY);

        // Good ol' for loop here.
        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = tracks.get(i);
            AudioTrackInfo info = track.getInfo();

            builder.appendDescription(String.format(
                    "%s - %s",
                    info.title,
                    info.author
            ));
        }

        channel.sendMessage(builder.build()).queue();

    }
}
