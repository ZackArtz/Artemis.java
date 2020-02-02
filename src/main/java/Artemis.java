import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.GuildlistCommand;
import commands.ServerInfo;
import commands.Uptime;
import commands.moderation.*;
import commands.music.*;
import commands.owner.Eval;
import commands.owner.Shutdown;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import utils.LogToSQL;
import utils.MuteTimeCheckerUtil;
import utils.TrueerUtil;

import static secret.InfoUtil.*;

// Main function, what gets executed when the program is run.

public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {

        CommandClientBuilder builder = new CommandClientBuilder().setOwnerId(botOwner)
                .setPrefix(PREFIX)
                .setHelpWord("help")
                .setCoOwnerIds("203938969136791552")
                .addCommands(new ServerInfo(),
                        new Play(),
                        new Stop(),
                        new Queue(),
                        new Skip(),
                        new NowPlaying(),
                        new Volume(),
                        new Uptime(),
                        new Shutdown(),
                        new KickCommand(),
                        new BanCommand(),
                        new UserLookup(),
                        new LogLookupCommand(),
                        new Eval(),
                        new GuildlistCommand(new EventWaiter()),
                        new MuteCommand(),
                        new UnmuteCommand(),
                        new BalladsCommand()
                );

        CommandClient client = builder.build();

        DefaultShardManagerBuilder builder1 = new DefaultShardManagerBuilder();
        builder1.setToken(TOKEN);
        builder1.addEventListeners(new LogToSQL(), new MuteTimeCheckerUtil(), new TrueerUtil(), client);
        builder1.build();

        // Above we add event listeners, when something happens like a message being received, these are called.
    }
}
