import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.ServerInfo;
import commands.Uptime;
import commands.moderation.KickCommand;
import commands.moderation.UserLookup;
import commands.music.*;
import commands.owner.Shutdown;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static secret.InfoUtil.*;

// TODO: AUUKI, YOU FUCK DO COMMAND MANAGEMENT

public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder()
                .setToken(TOKEN)
                .build();

        CommandClientBuilder builder = new CommandClientBuilder().setOwnerId(botOwner)
                .setPrefix(PREFIX)
                .setHelpWord("help")
                .addCommands(new ServerInfo(), new Play(), new Stop(), new Queue(), new Skip(), new NowPlaying(), new Volume(), new Uptime(), new Shutdown(), new KickCommand(), new UserLookup());

        CommandClient client = builder.build();
        jda.addEventListener(client);
    }
}
