package utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedUtils {
    public static MessageEmbed embed(String desc) {
        return new EmbedBuilder()
                .setDescription(desc)
                .setColor(Color.CYAN)
                .build();
    }
}
