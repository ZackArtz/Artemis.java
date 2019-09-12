package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import commands.audio.YouTubeSearchHandler;

import java.util.Arrays;

public class Play extends Command {

    public Play() {
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.help = "Gets the good shit goin";
    }

    @Override

    // TODO: AUUKI FIX YOUR CRAP

    protected void execute(CommandEvent event) {
        YouTubeSearchHandler.search("billy marchiafava");
    }
}
