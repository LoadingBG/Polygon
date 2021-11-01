package polygon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TableFormatter {
    private TableFormatter() {}

    private static String repeat(final String str, final int times) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            builder.append(str);
        }
        return builder.toString();
    }

    private static String centerString(final String str, final int desiredWidth) {
        final int start = (desiredWidth - str.length()) / 2;
        return repeat(" ", start) + str + repeat(" ", desiredWidth - start - str.length());
    }

    private static String padEnd(final String str, final int desiredWidth) {
        return str + repeat(" ", desiredWidth - str.length());
    }

    public static String languagesTable(final Map<String, String> langInfos) {
        final List<Map.Entry<String, String>> list = new ArrayList<>(langInfos.entrySet());
        final int langsWidth = Math.max("Language".length(), langInfos.keySet().stream().mapToInt(String::length).max().getAsInt());
        final int versionsWidth = Math.max("Version".length(), langInfos.values().stream().mapToInt(String::length).max().getAsInt());
        final String tableFormat = String.format(
                "\u250C%s\u252C%s\u2510%n\u2502 %%s \u2502 %%s \u2502%n\u251C%s\u253C%s\u2525%n%s\u2514%s\u2534%s\u2518",
                repeat("\u2500", langsWidth + 2), repeat("\u2500", versionsWidth + 2),
                repeat("\u2500", langsWidth + 2), repeat("\u2500", versionsWidth + 2),
                repeat("\u2502 %s \u2502 %s \u2502%n", langInfos.size()),
                repeat("\u2500", langsWidth + 2), repeat("\u2500", versionsWidth + 2)
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
