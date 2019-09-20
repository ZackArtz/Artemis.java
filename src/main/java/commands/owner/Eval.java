package commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Eval commands are very dangerous, it is for this reason that this bot's constructor (public Eval())
 * has this.ownerCommand = true. This tells the command manager that this command should only be run by the owner.
 * It doesn't show a error message, however. This is something that I will likely change when I rebuild the
 * commandManager.
 */

public class Eval extends Command {
    private final GroovyShell engine;
    private final String imports;

    /*
    Build the constructor that we use to add the command.
     */

    public Eval() {
        this.name = "eval";
        this.guildOnly = false;
        this.aliases = new String[]{"ev"};
        this.help = "evaluates stuff";
        this.engine = new GroovyShell();
        this.imports = "import java.io.*\n" +
                "import java.lang.*\n" +
                "import java.util.*\n" +
                "import java.util.concurrent.*\n" +
                "import net.dv8tion.jda.core.*\n" +
                "import net.dv8tion.jda.core.entities.*\n" +
                "import net.dv8tion.jda.core.entities.impl.*\n" +
                "import net.dv8tion.jda.core.managers.*\n" +
                "import net.dv8tion.jda.core.managers.impl.*\n" +
                "import net.dv8tion.jda.core.utils.*\n";
        this.ownerCommand = true;
        this.arguments = "[thing to eval]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        TextChannel channel = (TextChannel) event.getChannel();

        if (args[0] == null) {
            channel.sendMessage("Missing Arguments").queue();

            return;
        }

        /*
        Try-Catch to prevent errors.
         */

        try {
            engine.setProperty("args", args);
            engine.setProperty("event", event);
            engine.setProperty("message", event.getMessage());
            engine.setProperty("channel", event.getChannel());
            engine.setProperty("jda", event.getJDA());
            engine.setProperty("guild", event.getGuild());
            engine.setProperty("member", event.getMember());

            String script = imports + event.getMessage().getContentRaw().split("\\s+", 2)[1];
            Object out = engine.evaluate(script);

            event.getChannel().sendMessage(out == null ? "Executed without error" : "```" + out.toString() + "```").queue();
        }
        /*
        To save time, it sends a message in chat if something goes wrong, instead of making me check the term.
         */
        catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
