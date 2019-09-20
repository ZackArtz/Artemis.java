package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import secret.InfoUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * So going through these commands you might be thinking
 * "What does the "extends Command" mean?
 * extends Command means what it sounds like, it extends
 * the functionality of the Command class of the
 * JDA-Utilities package.
 *
 * This command returns the server info of a given server
 * and is a good demo of how to do a EmbedBuilder.
 *
 * The EmbedBuilder here is a constructor for the class and
 * that's why I use new here to make a new instance of that
 * constructor.
 */

public class ServerInfo extends Command {

    public ServerInfo() {
        this.name = "serverinfo";
        this.aliases = new String[]{"si"};
        this.help = "Displays Information about the server";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED)
                .setAuthor(event.getGuild().getName())
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Server Owner", Objects.requireNonNull(event.getGuild().getOwner()).getEffectiveName(), true)
                .addField("Member Count", Integer.toString(event.getGuild().getMembers().size()), true)
                .addField("Members", Arrays.toString(event.getGuild().getMembers().stream().map(Member::getEffectiveName).toArray(String[]::new)).replace("[", "").replace("]", ""), true)
                .setFooter(String.format("%s v%s", InfoUtil.CODE_NAME, InfoUtil.CODE_VERSION), event.getSelfUser().getAvatarUrl());

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
