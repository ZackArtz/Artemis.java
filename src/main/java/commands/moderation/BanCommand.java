package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Ban a user.
 *
 */

public class BanCommand extends Command {
    public BanCommand() {
        this.name = "ban";
        this.arguments = "[person to ban]";
        this.botPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.category = new Category("Moderation");
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = (TextChannel) event.getChannel();
        Member member = event.getMessage().getMentionedMembers().get(0);

        if (member == null) {
            channel.sendMessage("You have not provided someone to ban!").queue();
            return;
        }
        try {
            member.ban(0, "Gamer Reason").queue();
            channel.sendMessage("Banned user " + member.getEffectiveName()).queue();
        } catch (Exception e) {
            channel.sendMessage("Could not ban member: " + e).queue();
        }

    }
}
