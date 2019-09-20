import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.ServerInfo;
import commands.Uptime;
import commands.moderation.BanCommand;
import commands.moderation.KickCommand;
import commands.moderation.LogLookupCommand;
import commands.moderation.UserLookup;
import commands.music.*;
import commands.owner.Eval;
import commands.owner.Shutdown;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.LogToSQL;

import static secret.InfoUtil.*;

// Main function, what gets executed when the program is run.

public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder()
                .setToken(TOKEN)
                .build();

        /*
          We use .setToken(TOKEN) here to avoid using hardcoded strings for the login.
          More info can be found in the secret/InfoUtil.java file.

          Below you can see the command manager being setup
          TODO: Fix it so that I don't have to manually add each command to the .addCommand
         */

        CommandClientBuilder builder = new CommandClientBuilder().setOwnerId(botOwner)
                .setPrefix(PREFIX)
                .setHelpWord("help")
                .addCommands(new ServerInfo(), new Play(), new Stop(), new Queue(), new Skip(), new NowPlaying(), new Volume(), new Uptime(), new Shutdown(), new KickCommand(), new BanCommand(), new UserLookup(), new LogLookupCommand(), new Eval());

        CommandClient client = builder.build();
        jda.addEventListener(client);
        jda.addEventListener(new LogToSQL());

        // Above we add event listeners, when something happens like a message being received, these are called.
    }
}
