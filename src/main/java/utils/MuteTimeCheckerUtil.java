package utils;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class MuteTimeCheckerUtil extends ListenerAdapter {

    public void onReady(ReadyEvent event) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MuteUtil muteUtil = new MuteUtil();
                try {
                    muteUtil.checkMutes(event);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60*1000);
    }

}
