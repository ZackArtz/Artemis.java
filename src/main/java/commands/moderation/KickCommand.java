package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Kick a user.
 * Pretty simple.
 */

public class KickCommand extends Command {
    public KickCommand() {
        this.name = "kick";
        this.arguments = "[person to kick]";
        this.botPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.category = new Category("Moderation");
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        Member member = event.getMessage().getMentionedMembers().get(0);

        if (member == null) {
            channel.sendMessage("You have not provided someone to kick!").queue();
            return;
        }
        try {
            member.kick().queue();
            channel.sendMessage("Kicked user " + member.getEffectiveName()).queue();
        } catch (Exception e) {
            channel.sendMessage("Could not kick member: " + e).queue();
        }

    }
}
