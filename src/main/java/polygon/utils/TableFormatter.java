package polygon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A utility for creating tables.
 */
public final class TableFormatter {
    private TableFormatter() {}

    /**
     * A horizontal line: {@code -}.
     */
    private static final String HORIZONTAL_LINE = "\u2500";
    /**
     * A vertical line: {@code |}.
     */
    private static final String VERTICAL_LINE = "\u2502";
    /**
     * Single character version of:
     * <pre><code>
     * +-
     * |
     * </code></pre>
     */
    private static final String TOP_LEFT_CORNER = "\u250C";
    /**
     * Single character version of:
     * <pre><code>
     * -+
     *  |
     * </code></pre>
     */
    private static final String TOP_RIGHT_CORNER = "\u2510";
    /**
     * Single character version of:
     * <pre><code>
     * |
     * +-
     * </code></pre>
     */
    private static final String BOTTOM_LEFT_CORNER = "\u2514";
    /**
     * Single character version of:
     * <pre><code>
     *  |
     * -+
     * </code></pre>
     */
    private static final String BOTTOM_RIGHT_CORNER = "\u2518";
    /**
     * Single character version of:
     * <pre><code>
     *  |
     * -+-
     *  |
     * </code></pre>
     */
    private static final String CROSS = "\u253C";
    /**
     * Single character version of:
     * <pre><code>
     * -+-
     *  |
     * </code></pre>
     */
    private static final String TOP_CROSS = "\u252C";
    /**
     * Single character version of:
     * <pre><code>
     * |
     * +-
     * |
     * </code></pre>
     */
    private static final String LEFT_CROSS = "\u251C";
    /**
     * Single character version of:
     * <pre><code>
     *  |
     * -+
     *  |
     * </code></pre>
     */
    private static final String RIGHT_CROSS = "\u2525";
    /**
     * Single character version of:
     * <pre><code>
     *  |
     * -+-
     * </code></pre>
     */
    private static final String BOTTOM_CROSS = "\u2534";

    /**
     * Repeats a string {@code times} times. The same as {@code String#repeat} in Java 11.
     *
     * @param str The string to repeat.
     * @param times The times to repeat.
     * @return The string repeated {@code times} times.
     */
    private static String repeat(final String str, final int times) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * Centers a string, padding it with spaces, until it
     * reaches the desired width.
     *
     * @param str The string to center.
     * @param desiredWidth The desired width to reach.
     * @return The centered string.
     */
    private static String centerString(final String str, final int desiredWidth) {
        final int start = (desiredWidth - str.length()) / 2;
        return repeat(" ", start) + str + repeat(" ", desiredWidth - start - str.length());
    }

    /**
     * Pads a string, adding spaced to the end, until
     * it reaches the desired width.
     *
     * @param str The string to pad.
     * @param desiredWidth The desired width to reach.
     * @return The padded string.
     */
    private static String padEnd(final String str, final int desiredWidth) {
        return str + repeat(" ", desiredWidth - str.length());
    }

    /**
     * Creates a table for {@link polygon.commands.run.RunCommand /run languages}.
     *
     * @param langInfos A table of name - version pairs.
     * @return The formatted table.
     */
    public static String languagesTable(final Map<String, String> langInfos) {
        final List<Map.Entry<String, String>> list = new ArrayList<>(langInfos.entrySet());
        final int langsWidth = Math.max("Language".length(), langInfos.keySet().stream().mapToInt(String::length).max().getAsInt());
        final int versionsWidth = Math.max("Version".length(), langInfos.values().stream().mapToInt(String::length).max().getAsInt());
        final String tableFramework = String.format(
                "%s%%s%s%%s%s%%n" +
                "%s %%%%s %s %%%%s %s%%n" +
                "%s%%s%s%%s%s%%n" +
                "%%s" +
                "%s%%s%s%%s%s",
                TOP_LEFT_CORNER, TOP_CROSS, TOP_RIGHT_CORNER,
                VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE,
                LEFT_CROSS, CROSS, RIGHT_CROSS,
                BOTTOM_LEFT_CORNER, BOTTOM_CROSS, BOTTOM_RIGHT_CORNER
        );
        final String tableFormat = String.format(
                tableFramework,
                repeat(HORIZONTAL_LINE, langsWidth + 2), repeat(HORIZONTAL_LINE, versionsWidth + 2),
                repeat(HORIZONTAL_LINE, langsWidth + 2), repeat(HORIZONTAL_LINE, versionsWidth + 2),
                repeat(String.format("%s %%s %s %%s %s%%n", VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE), langInfos.size()),
                repeat(HORIZONTAL_LINE, langsWidth + 2), repeat(HORIZONTAL_LINE, versionsWidth + 2)
        );
        final Object[] entities = new Object[2 * langInfos.size() + 2];
        entities[0] = centerString("Language", langsWidth);
        entities[1] = centerString("Version", versionsWidth);
        for (int i = 0; i < langInfos.size(); ++i) {
            entities[i * 2 + 2] = padEnd(list.get(i).getKey(), langsWidth);
            entities[i * 2 + 3] = padEnd(list.get(i).getValue(), versionsWidth);
        }
        return String.format(tableFormat, entities);
    }
}
