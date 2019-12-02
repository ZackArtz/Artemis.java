package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import utils.MuteUtil;

public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        this.name = "unmute";
        this.help = "Unmutes a user";
        this.aliases = new String[]{"um"};
        this.arguments = "[user]";
        this.category = new Category("Moderation");
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent event) {

        if (event.getMessage().getMentionedMembers().get(0) == null) {
            event.getChannel().sendMessage("Please provide a user!").queue();
        }

        MuteUtil muteUtil = new MuteUtil();
        muteUtil.unMute(event.getMessage().getMentionedMembers().get(0), event.getGuild());

    }

}
