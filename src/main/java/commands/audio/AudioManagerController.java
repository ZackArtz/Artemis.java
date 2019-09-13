package commands.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import lavalink.client.io.Link;
import net.dv8tion.jda.api.entities.Guild;

import java.net.URL;
import java.util.HashMap;

public class AudioManagerController {



    private static HashMap<Guild, GuildAudioManager> managers;
    private static AudioPlayerManager playerManager;

    public AudioManagerController() {
        managers = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();

    }

    public static HashMap<Guild, GuildAudioManager> getGuildAudioManagers() {
        return managers;
    }

    private static void addGuildAudioManager(Guild guild, GuildAudioManager manager) {
        managers.put(guild, manager);
    }

    public static void removeGuildAudioManger(Guild guild) {
        managers.remove(guild);
    }

    public static AudioPlayerManager getPlayerManager() {
        return playerManager;
    }

    // TODO: IDK I'm bored, you do this chief.

    public static Link getExistingLink(Guild guild) {
        return null;
    }

    public static GuildAudioManager getGuildAudioManager(Guild guild) {
        GuildAudioManager manager = managers.get(guild);
        if(manager == null) {
            synchronized (AudioManagerController.getGuildAudioManagers()) {
                manager = new GuildAudioManager(guild);
                addGuildAudioManager(guild, manager);

            }
        }
        return manager;
    }
}
