package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.examples.doc.Author;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import secret.InfoUtil;
import utils.EmbedUtils;

import java.awt.*;

/**
 *
 * @author zack myers (zxck@zxck.codes)
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

        assert user != null;
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Information for " + user.getName())
                .setAuthor(user.getName())
                .addField("Created At", user.getTimeCreated().toString(), false)
                .addField("Is bot?", user.isBot() ? "is a bot" : "is not a bot", false)
                .setColor(Color.CYAN)
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter(InfoUtil.CODE_NAME + " " +  InfoUtil.CODE_VERSION);

        channel.sendMessage(embed.build()).queue();
    }
}
