import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.ServerInfo;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Artemis extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder(AccountType.BOT).setToken("NjIxOTE3NzA0NDA3ODc1NjE0.XXsUAw.GRR-QlgaO_OnvIXRnYqksL58mKQ").build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId("133314498214756352");
        builder.setPrefix("$");
        builder.setHelpWord("help");
        builder.addCommand(new ServerInfo());

        CommandClient client = builder.build();

        jda.addEventListener(client);
    }
}
