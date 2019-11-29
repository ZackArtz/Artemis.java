package utils;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class MuteUtil {
    public void mute(final CommandEvent event, String reason, final String time) {
        final TextChannel channel = event.getMessage().getTextChannel();
        final Member member = event.getMessage().getMentionedMembers().get(0);
        final Guild guild = event.getGuild();
        Role role = null;
        final MySQLUtils sqlUtils = new MySQLUtils();

        if (reason.isEmpty()) {
            reason = " ";
        }

        if (time == null) {
            channel.sendMessage("You must provide a time in minutes!").queue();
            return;
        }

        try {
            role = guild.getRolesByName("Artemis Muted", false).get(0);
            guild.addRoleToMember(member, role).queue();
        } catch (final IndexOutOfBoundsException ignored) {
        }

        if (role == null) {
            guild.createRole().setColor(Color.BLACK).setPermissions(Permission.MESSAGE_READ).setName("Artemis Muted")
                    .queue();
            try {
                Thread.sleep(1000);
                role = guild.getRolesByName("Artemis Muted", false).get(0);
                guild.addRoleToMember(member, role).queue();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        final LocalDateTime currentTime = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyyHH:mm:ss");

        try {
            final PreparedStatement statement = sqlUtils.connect()
                    .prepareStatement("insert into mutes values (?, ?, ?, ?)");
            statement.setString(1, member.getUser().getId());
            statement.setString(2, reason);
            statement.setString(3, currentTime.format(formatter));
            statement.setString(4, currentTime.plusMinutes(Long.parseLong(time)).format(formatter));
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        sqlUtils.disconnect();
    }
}