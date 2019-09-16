package commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.entities.TextChannel;

public class Eval extends Command {
    private final GroovyShell engine;
    private final String imports;

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
        catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
