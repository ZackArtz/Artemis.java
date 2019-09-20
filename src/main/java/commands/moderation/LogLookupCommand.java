package commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import utils.MySQLUtils;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class LogLookupCommand extends Command {
    public LogLookupCommand() {
        this.name = "loglookup";
        this.aliases = new String[]{"ll"};
        this.arguments = "[searchBy (userID, channelID, etc)";
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.category = new Category("Moderation");
    }

    @Override
    protected void execute(CommandEvent event) {

        Message message = event.getMessage();
        String[] args = event.getArgs().split(" ");

        String filename = event.getChannel().getName() + "-" + event.getGuild().getName() + "-" + fileNameGen() + ".txt";

        MySQLUtils sqlUtils = new MySQLUtils();
        String query;
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
                query = "Unexpected value: " + args[0];
                break;
        }

        createFile f = new createFile();

        try {
            Statement stmt = sqlUtils.connect().createStatement();
            ResultSet rs = stmt.executeQuery(query);



            f.Openfile(filename);

            while (rs.next()) {
                f.addRecords(rs.getString(7), rs.getString(4), rs.getString(3), rs.getString(5));
            }

            f.closeFile();

        } catch (SQLException e) {
            message.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("That failed, sorry." + e).queue();
            });
        }

        message.getAuthor().openPrivateChannel().queue((channel) -> {
            channel.sendFile(new File(filename)).queue();
        });

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("frick");
        }

        f.delFile(new File(filename));

    }

    private String fileNameGen() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private class createFile {
        private Formatter x;

        public void Openfile(String filename) {
            try {
                x = new Formatter(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void addRecords(String one, String two, String three, String four) {
            x.format("[%s] (%s) %s: %s \n", one, two, three, four);
        }

        public void closeFile() {
            x.close();
        }

        public void delFile(File file) {
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
