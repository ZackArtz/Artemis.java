package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.Permission;
import utils.MuteUtil;

public class MuteCommand extends Command {

    public MuteCommand() {
        this.name = "mute";
        this.help = "Mutes a user";
        this.aliases = new String[]{"m"};
        this.arguments = "[user] [time] [reason] - ALL REQUIRED!";
        this.category = new Category("Moderation");
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent event) {
        MuteUtil mUtil = new MuteUtil();
        String[] args = event.getArgs().split(" ");

        if (args[1].isEmpty()) {
            event.getChannel().sendMessage("Please provide a time").queue();
            return;
        }

        if (args[2].isEmpty()) {
            event.getChannel().sendMessage("Please provide a reason").queue();
        }

        mUtil.mute(event, args[2], args[1]);
    }

}
