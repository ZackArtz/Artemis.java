package utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Here comes the SQL, this is a modified onMessageReceived Event.
 * Java is a strong-typed language, meaning that every variable's type is defined
 * A example of this is the public void onMessageReceived below.
 * In a language like JavaScript which is soft-typed, we would call event with var or let, but in Java
 * we don't have that. Instead of var we say that we are using a MessageReceivedEvent variable named event.
 */

public class LogToSQL extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        /*
          Include the MySQLUtils.java file, here we are making a new instance of the MySQLUtils class.
         */
        MySQLUtils sqlUtils = new MySQLUtils();


        /*
          Below we see the query that we will send to the MySQL Database.
         */
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


            /*
            Execute the query and disconnect from the server.
             */
            qStatement.execute();
            qStatement.closeOnCompletion();
            sqlUtils.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
