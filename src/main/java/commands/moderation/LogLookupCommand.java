package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import utils.MySQLUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

/**
 * Ah yes, more SQL.
 * This command was a royal pain-in-the-ass.
 * Because I wanted to name the log files, (.txts that we send
 * to users) in a naming scheme that follows this
 * "exampleChannel-exampleGuild-exampleTime.txt"
 * I had problems with the time being mismatched and not being found.
 * To solve this issue, I had to set a variable to be the name, and its defined on line 44
 */

public class LogLookupCommand extends Command {
    public LogLookupCommand() {
        this.name = "loglookup";
        this.aliases = new String[]{"ll"};
        this.arguments = "[searchBy (userID, channelID, etc)] [ID]";
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.category = new Category("Moderation");
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {

        Message message = event.getMessage();
        String[] args = event.getArgs().split(" ");

        String filename = event.getChannel().getName() + "-" + event.getGuild().getName() + "-" + timeGen() + ".txt";
        // Could have made this simpler, but hey, I'm lazy.

        MySQLUtils sqlUtils = new MySQLUtils();
        String query;
        // Define query string here, so its available to be used outside of this switch statement.

        switch (args[0]) {
            case "userID": {
                query = "SELECT * from logs where userID = " + '"' + args[1] + '"';
                break;
            }
            case "channelID": {
                query = "SELECT * from logs where channelID = " + '"' + args[1] + '"';
                break;
            }
            case "guildID": {
                query = "SELECT * from logs where guildID = " + '"' + args[1] + '"';
                break;
            }
            default:
                message.getChannel().sendMessage("Unexpected value: " + args[0] + "try channelID, guildID, or userID").queue();
                return;
        }

        /*
        This switch statement allows a user to select between three different ways of searching the database,
        and if they provide a way that isn't recognised, it will send a "Unexpected value"
         */

        createFile f = new createFile();
        // Create the file that will be sent to the user.

        /*
        Try-Catch for error-handling, again.
         */
        try {
            Statement stmt = sqlUtils.connect().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Create the Statement and ResultSet.

            f.openFile(filename);
            // Start writing to the file.

            if (args[0].equals("guildID")) {
                while (rs.next()) {
                    f.addRecords2(rs.getString(7), rs.getString(8), rs.getString(4), rs.getString(3), rs.getString(5));
                }
            }

            while (rs.next()) {
                f.addRecords(rs.getString(7), rs.getString(4), rs.getString(3), rs.getString(5));
            }

            // Write every line to the file.

            f.closeFile();

            // Stop writing.

        } catch (SQLException e) {
            message.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("That failed, sorry." + e).queue());
            // Tell the author if the command failed.
        }


        message.getAuthor().openPrivateChannel().queue((channel) -> channel.sendFile(new File(filename)).queue());

        // DM the user their text file.

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("frick");
        }

        // If it breaks oh well, this waits for the file to be sent before deleting it.

        f.delFile(new File(filename));
        // Delete the file

    }
    /*
    Get the time in a nice formatted way.
     */
    private String timeGen() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    // Here comes the create file class, inside here you'll find the functions we use to write the files.
    private static class createFile {
        private Formatter x;

        void openFile(String filename) {
            try {
                x = new Formatter(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        void addRecords(String one, String two, String three, String four) {
            x.format("[%s] (%s) %s: %s \n", one, two, three, four);
        }

        void addRecords2(String one, String two, String three, String four, String five) {
            x.format("[%s] {%s} (%s) %s: %s \n", one, two, three, four, five);
        }

        void closeFile() {
            x.close();
        }

        void delFile(File file) {
            try {
                if (file.delete()) {
                    System.out.println("File Deleted!");
                } else {
                    System.out.println("Fuck");
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }
    }


}
