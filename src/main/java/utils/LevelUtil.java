package utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelUtil extends ListenerAdapter {

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        /*
          Define the connector to the SQL Server
         */
        MySQLUtils sqlUtils = new MySQLUtils();

        /*
        Get the current level from the database.
         */

        int currentLevel = 0;
        try {
            currentLevel = getCurrentLevel(event.getAuthor().getId(), event.getGuild().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        Simple Error handling for if the server doesn't respond.
         */

        if (currentLevel == 0) {
            System.out.println("Could not get data from server, fuck.");
            return;
        } else if (currentLevel == -1) {
            addUser(event.getAuthor().getAsTag(), event.getAuthor().getId(), event.getGuild().getId());
            return;
        }

        /*
        TODO: Change this into its own function.
         */

        String query = "update levels set level = " + (currentLevel + 1) + " where userID like " + event.getAuthor().getId() + " and guildID like " + event.getGuild().getId() + ";";

        try {
            PreparedStatement statement = sqlUtils.connect().prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        If the user deserves a congrats message, they can get it here.
         */

        try {
            Congratulate(getMainLevel(event.getAuthor().getId(), event.getGuild().getId()), event, currentLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    Add user to database if they aren't already in it.
     */

    private void addUser(String asTag, String id, String guildID) {

        MySQLUtils sqlUtils = new MySQLUtils();

        String query = "insert into levels (userName, userID, guildID, level, mainLevel) values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = sqlUtils.connect().prepareStatement(query);
            statement.setString(1, asTag);
            statement.setString(2, id);
            statement.setString(3, guildID);
            statement.setInt(4, 1);
            statement.setInt(5, 1);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    Get code for getting currentlevel of user.
     */

    public int getCurrentLevel(String userID, String guildID) throws SQLException {

        MySQLUtils sqlUtils = new MySQLUtils();

        String query = "select level from levels where userID like " + '"' + userID + '"' + " and guildID like " + '"' + guildID + '"' + ";";
        return intelliJSaidSo(sqlUtils, query);

    };

    /*
    Code for getting the main level (the one that's displayed)
     */

    private int getMainLevel(String userId, String guildId) throws SQLException {

        MySQLUtils sqlUtils = new MySQLUtils();

        String query = "select mainLevel from levels where userID like " + '"' + userId + '"' + " and guildID like " + '"' + guildId + '"' + ";";
        return intelliJSaidSo(sqlUtils, query);

    }

    /*
    idk
     */

    private int intelliJSaidSo(MySQLUtils sqlUtils, String query) throws SQLException {
        ResultSet rs = null;

        try {
            PreparedStatement statement = sqlUtils.connect().prepareStatement(query);
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (rs == null) {
            System.out.println("Could not get a level");
            return 0;
        } else if (!rs.first()) {
            return -1;
        } else {
            return rs.getInt(1);
        }
    }

    /*
    Check to see if the user needs to be congratulated.
     */

    private void Congratulate(int CurrentLevel, MessageReceivedEvent event, int dbLevel) {
        int mainLevel = 0;
        for (int i = 0; i < dbLevel; i++) {
            if (i%15 == 0) {
                mainLevel++;
            }
        }

        if (mainLevel > CurrentLevel) {
            event.getChannel().sendMessage("Congrats on reaching level " + mainLevel + " " + event.getAuthor().getName() + "!").queue();
            updateMainLevel(event.getAuthor().getId(), event.getGuild().getId(), mainLevel);
        }
    }

    /*
    Code for updating the main level of the user.
     */

    private void updateMainLevel(String id, String id1, int mainlevel) {
        MySQLUtils sqlUtils = new MySQLUtils();

        String query = "update levels set mainLevel = " + mainlevel + " where userID like " + id + " and guildID like " + id1 + ";";

        try {
            PreparedStatement statement = sqlUtils.connect().prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}