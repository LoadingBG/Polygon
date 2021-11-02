package polygon.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

/**
 * A utility class for making embeds.
 *
 * <p>This class should be used for all embeds.</p>
 */
public final class EmbedUtils {
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color GENERIC_COLOR = Color.CYAN;

    private EmbedUtils() {}

    /**
     * Creates an error embed.
     *
     * @param description The error description.
     * @return The embed.
     */
    public static MessageEmbed error(String description) {
        return new EmbedBuilder()
                .setTitle("Error")
                .setColor(ERROR_COLOR)
                .setDescription(description)
                .build();
    }

    /**
     * Creates a generic embed.
     *
     * @param title The title of the embed.
     * @param description The description of the embed.
     * @return The embed.
     */
    public static MessageEmbed generic(final String title, final String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setColor(Color.CYAN)
                .setDescription(description)
                .build();
    }

    /**
     * Creates a generic embed with fields.
     *
     * @param title The title of the embed.
     * @param fields The fields of the embed.
     * @return The embed.
     */
    public static MessageEmbed genericWithFields(String title, MessageEmbed.Field... fields) {
        final EmbedBuilder builder = new EmbedBuilder()
                .setTitle(title)
                .setColor(GENERIC_COLOR);
        for (MessageEmbed.Field field : fields) {
            builder.addField(field);
        }
        return builder.build();
    }
}
