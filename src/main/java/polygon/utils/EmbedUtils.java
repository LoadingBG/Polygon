package polygon.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public final class EmbedUtils {
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color GENERIC_COLOR = Color.CYAN;

    private EmbedUtils() {}

    public static MessageEmbed error(String description) {
        return new EmbedBuilder()
                .setTitle("Error")
                .setColor(ERROR_COLOR)
                .setDescription(description)
                .build();
    }

    public static MessageEmbed genericWithFields(String title, String description, MessageEmbed.Field... fields) {
        final EmbedBuilder builder = new EmbedBuilder()
                .setTitle(title)
                .setColor(GENERIC_COLOR);
        for (MessageEmbed.Field field : fields) {
            builder.addField(field);
        }
        return builder.build();
    }
}
