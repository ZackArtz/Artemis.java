package utils;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;

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

        try {
            final PreparedStatement statement = sqlUtils.connect()
                    .prepareStatement("insert into mutes values (?, ?, ?, ?, ?)");
            statement.setString(1, member.getUser().getId());
            statement.setString(2, guild.getId());
            statement.setString(3, reason);
            statement.setLong(4, System.currentTimeMillis());
            statement.setLong(5, System.currentTimeMillis() + (60000 * Integer.parseInt(time)));
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Muted " + member.getEffectiveName());
        sqlUtils.disconnect();
    }

    public void checkMutes(ReadyEvent event) throws SQLException {
        System.out.println("Checking for people to unmute");
        long time = System.currentTimeMillis();
        MySQLUtils sqlUtils = new MySQLUtils();
        PreparedStatement statement = sqlUtils.connect().prepareStatement("select * from mutes");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            long umTime = set.getLong(5);
            String id = set.getString(1);
            String gID = set.getString(2);
            long sTime = set.getLong(4);
            if (time > umTime) {
                Guild guild = event.getJDA().getGuildById(gID);
                assert guild != null;
                Member member = guild.getMemberById(id);
                assert member != null;
                unMute(member, guild);
                PreparedStatement stmt = sqlUtils.connect().prepareStatement("delete from mutes where userID = " + id + " and currentTime = " + sTime + " limit 1");
                stmt.execute();
            }
        }
        sqlUtils.disconnect();
    }

    public void unMute(Member member, Guild guild) {
        Role role = guild.getRolesByName("Artemis Muted", false).get(0);
        guild.removeRoleFromMember(member, role).queue();
        System.out.println("Unmuted " + member.getEffectiveName());
    }
}