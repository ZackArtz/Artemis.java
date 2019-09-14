package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.examples.doc.Author;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author zack myers (zxck@zxck.codes)
 */
@CommandInfo(
        name = "Userlookup",
        description = "Finds a user and displays information about them"
)
@Author("Zack Myers (zxck@zxck.codes)")
public class UserLookup extends Command {
    public UserLookup() {
        this.name = "userlookup";
        this.aliases = new String[]{"ul"};
        this.help = "Looks up information on a given user";
        this.arguments = "[userID]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        TextChannel channel = (TextChannel) event.getChannel();
        User user = event.getJDA().getUserById(args[0]);

        assert user != null;
        channel.sendMessage(user.getTimeCreated().toString()).queue();
    }
}
