package ru.didcvee;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GroupWriter {
    public static int writeGroups(BufferedWriter writer, List<String> lines, Map<Integer, List<Integer>> groupElements, List<Map.Entry<Integer, Integer>> sortedGroups) throws IOException {
        int groupNumber = 1;
        for (Map.Entry<Integer, Integer> group : sortedGroups) {
            List<Integer> elements = groupElements.get(group.getKey());
            if (elements.size() <= 1) continue;

            writer.write(String.format("Group %d\n", groupNumber));
            for (int element : elements) {
                writer.write(lines.get(element) + "\n");
            }
            writer.write("\n");
            groupNumber++;
        }
        return groupNumber - 1;
    }
}
