package utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LogToSQL extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        MySQLUtils sqlUtils = new MySQLUtils();

        String query = "insert into logs (guildName, guildID, userName, userID, message, messageID, messageCreated, channelName, channelID)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement qStatement = sqlUtils.connect().prepareStatement(query);
            qStatement.setString(1, event.getGuild().getName());
            qStatement.setString(2, event.getGuild().getId());
            qStatement.setString(3, event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator());
            qStatement.setString(4, event.getAuthor().getId());
            qStatement.setString(5, event.getMessage().getContentRaw());
            qStatement.setString(6, event.getMessage().getId());
            qStatement.setString(7, String.valueOf(event.getMessage().getTimeCreated()));
            qStatement.setString(8, event.getChannel().getName());
            qStatement.setString(9, event.getChannel().getId());


            qStatement.execute();
            qStatement.closeOnCompletion();
            sqlUtils.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
