package ru.didcvee;

import java.util.Objects;

public class LineParser {
    public static String[] parseAndValidateLine(String line) {
        return isValidLine(line) ? line.split(";") : null;
    }

    public static boolean isValidLine(String line) {
        return line.matches("(\"\\d*(.\\d)?\")(;(\"\\d*(.\\d)?\")?)*");
    }

    public static boolean isEmptyString(String string) {
        return Objects.equals(string, "") || Objects.equals(string, "\"\"");
    }
}
