import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.Play;
import commands.ServerInfo;
import lavalink.client.io.jda.JdaLavalink;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static secret.InfoUtil.*;

import java.util.Arrays;


public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).build();

        CommandClientBuilder builder = new CommandClientBuilder().setOwnerId(botOwner)
                .setPrefix(PREFIX)
                .setHelpWord("help")
                .addCommands(new ServerInfo(), new Play());

        CommandClient client = builder.build();

        jda.addEventListener(client);
    }
}
