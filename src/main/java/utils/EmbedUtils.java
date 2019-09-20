package utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * This exists to make my life easier when making embeds, i just call a new EmbedUtils.embed() instead of a whole new EmbedBuilder.
 */

public class EmbedUtils {
    public static MessageEmbed embed(String desc) {
        return new EmbedBuilder()
                .setDescription(desc)
                .setColor(Color.CYAN)
                .build();
    }
}
