package commands.testing;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TestMenuCommand extends Command {

    public TestMenuCommand() {
        this.name = "menu";
        this.help = "Test menu, dw about it.";
        this.cooldown = 3;
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        Message message = event.getMessage();


        channel.sendMessage("yes").queue();
    }
}
