package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import utils.MuteUtil;

public class MuteCommand extends Command {
    public MuteCommand() {
        this.name = "mute";
        this.help = "Mutes a user";
    }

    @Override
    protected void execute(CommandEvent event) {
        MuteUtil mUtil = new MuteUtil();
        String[] args = event.getArgs().split(" ");
        mUtil.mute(event, args[2], args[1]);
    }
}
