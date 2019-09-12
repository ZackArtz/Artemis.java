package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Arrays;

public class ServerInfo extends Command {

    public ServerInfo() {
        this.name = "serverinfo";
        this.aliases = new String[]{"si"};
        this.help = "Displays Information about the server";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] members = new String[event.getGuild().getMembers().size()];
        for (int i = 0; i < event.getGuild().getMembers().size(); i++) {
            members[i] = event.getGuild().getMembers().get(i).getEffectiveName();
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor(event.getGuild().getName());
        eb.setThumbnail(event.getGuild().getIconUrl());
        eb.addField("Server Owner: ", event.getGuild().getOwner().getEffectiveName(), true);
        eb.addField("Member Count:", Integer.toString(event.getGuild().getMembers().size()), true);
        eb.setDescription("**Members:** \n" + Arrays.toString(members));

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
