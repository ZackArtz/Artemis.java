package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.examples.doc.Author;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import secret.InfoUtil;

import java.awt.*;

/**
 *
 * @author zack myers (zxck@zxck.codes)
 *
 * This command is to find info about a user, fun fact, you can see that we have both User and Member
 * defined, this is because someone's user object and member object have different properties, such as
 * a user object doesn't have a nickname but a member does.
 */
@CommandInfo(
        name = "Userlookup",
        description = "Finds a user and displays information about them"
)
@Author("Zack Myers (zxck@zxck.codes)")
public class UserLookup extends Command {
    public UserLookup() {
        this.name = "userlookup";
        this.aliases = new String[]{"ul"};
        this.help = "Looks up information on a given user";
        this.arguments = "[userID]";
        this.category = new Category("Moderation");
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        TextChannel channel = (TextChannel) event.getChannel();
        User user = event.getJDA().getUserById(args[0]);
        Member member = event.getGuild().getMemberById(args[0]);

        assert user != null;
        assert member != null;

        // Both of these if not found could return null, by having "assert member != null" this tells the code
        // That in order to continue these objects must not be null

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Information for " + user.getName())
                .setAuthor(user.getName())
                .addField("Created At", user.getTimeCreated().toString(), false)
                .addField("Is bot?", user.isBot() ? "is a bot" : "is not a bot", false)
                .addField("Roles", member.getRoles().toString(), false)
                .addField("Real name", user.getName() + "#" + user.getDiscriminator(), true)
                .setColor(Color.CYAN)
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter(InfoUtil.CODE_NAME + " " +  InfoUtil.CODE_VERSION);

        // Build a embed and send it.

        channel.sendMessage(embed.build()).queue();
    }
}
