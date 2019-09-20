package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Simple command to get the uptime and return it as a message in the discord channel.
 * Does utilize some cool formatting, and even channel.sendMessageFormat, which before
 * I made this command I didn't know existed.
 */

public class Uptime extends Command {
    public Uptime() {
        this.name = "uptime";
        this.help = "Shows how long the bot has been up";
        this.aliases = new String[]{"ut"};
    }

    @Override
    protected void execute(CommandEvent event) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;

        event.getChannel().sendMessageFormat(
                "My uptime is `%s hours, %s minutes, %s seconds`",
                numberOfHours, numberOfMinutes, numberOfSeconds
        ).queue();
    }
}
