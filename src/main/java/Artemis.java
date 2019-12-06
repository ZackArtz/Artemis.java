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

import static secret.InfoUtil.*;

// Main function, what gets executed when the program is run.

public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {

        CommandClientBuilder builder = new CommandClientBuilder().setOwnerId(botOwner)
                .setPrefix(PREFIX)
                .setHelpWord("help")
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
                        new BalladsCommand()
                );

        CommandClient client = builder.build();

        DefaultShardManagerBuilder builder1 = new DefaultShardManagerBuilder();
        builder1.setToken(TOKEN);
        builder1.addEventListeners(new LogToSQL(), new MuteTimeCheckerUtil(), client);
        builder1.build();


        /*
          We use .setToken(TOKEN) here to avoid using hardcoded strings for the login.
          More info can be found in the secret/InfoUtil.java file.

          Below you can see the command manager being setup
          TODO: Fix it so that I don't have to manually add each command to the .addCommand
         */


        // Above we add event listeners, when something happens like a message being received, these are called.
    }
}
