package commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.examples.doc.Author;

/**
 *
 * @author zack
 */
@CommandInfo(
        name = "Shutdown",
        description = "Safely shuts down the bot"
)
@Author("Zachary Myers (zxck@zxck.codes)")
public class Shutdown extends Command {

    public Shutdown() {
        this.name = "shutdown";
        this.help = "Turns off the bot";
        this.guildOnly = false;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reactWarning();
        event.getJDA().shutdown();
    }
}
