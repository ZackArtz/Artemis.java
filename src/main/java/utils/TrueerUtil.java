package utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TrueerUtil extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if ((event.getGuild().getId().equals("566901455395618838")) || event.getGuild().getId().equals("609572230141182002")) {
            if (event.getMessage().getContentRaw().toLowerCase().equals("true :lulw:")) {
                event.getMessage().getChannel().sendMessage("TRUE <:LULW:660717129795895307>").queue();
            }
        }
    }

}
