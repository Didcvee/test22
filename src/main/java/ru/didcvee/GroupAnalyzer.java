package ru.didcvee;

import java.io.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class GroupAnalyzer {
    private final String inputFilename;
    private final List<String> lines = new ArrayList<>();
    private final Set<String> uniqueLines = new HashSet<>();
    private final List<Map<String, Integer>> places = new ArrayList<>();
    private final UnionFind unionFind = new UnionFind();

    public GroupAnalyzer(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public int analyze() {
        String outputFilename = "src/main/resources/out.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {

            readAndProcessLines(reader);
            uniqueLines.clear();
            places.clear();

            Map<Integer, List<Integer>> groupElements = new HashMap<>();
            List<Map.Entry<Integer, Integer>> sortedGroups = sortGroups(groupElements);
            int totalGroups = GroupWriter.writeGroups(writer, lines, groupElements, sortedGroups);

            return totalGroups;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readAndProcessLines(BufferedReader reader) throws IOException {
        String line;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            String[] parsedLine = LineParser.parseAndValidateLine(line);
            if (parsedLine == null || uniqueLines.contains(line)) continue;

            saveLine(line, lineCount);
            updateGroups(parsedLine, lineCount);
            lineCount++;
        }
    }

    private void saveLine(String line, int lineCount) {
        unionFind.add(lineCount);
        uniqueLines.add(line);
        lines.add(line);
    }

    private void updateGroups(String[] lineStrings, int lineNumber) {
        for (int i = 0; i < lineStrings.length; i++) {
            String currentString = lineStrings[i];
            if (i >= places.size()) {
                Map<String, Integer> map = new HashMap<>();
                map.put(currentString, lineNumber);
                places.add(map);
            } else if (!LineParser.isEmptyString(currentString) && places.get(i).containsKey(currentString)) {
                int matchedLineNumber = places.get(i).get(currentString);
                unionFind.union(matchedLineNumber, lineNumber);
            } else {
                places.get(i).put(currentString, lineNumber);
            }
        }
    }

    private List<Map.Entry<Integer, Integer>> sortGroups(Map<Integer, List<Integer>> elementsByGroup) {
        Map<Integer, Integer> groupSizes = new HashMap<>();
        for (int i = 0; i < unionFind.size(); i++) {
            int root = unionFind.findRoot(i);
            elementsByGroup.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
            groupSizes.put(root, groupSizes.getOrDefault(root, 0) + 1);
        }
        return groupSizes.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        LocalTime startTime = LocalTime.now();
        Thread timerThread = createTimerThread();
        timerThread.start();

        System.out.println("Starting to read file");

        GroupAnalyzer analyzer = new GroupAnalyzer("lng-big.csv");
        int groups = analyzer.analyze();

        System.out.println("Total Groups: " + groups);
        timerThread.interrupt();

        long secondsOfWork = ChronoUnit.SECONDS.between(startTime, LocalTime.now());
        System.out.printf("Complete, work time: %d sec \n", secondsOfWork);
    }

    private static Thread createTimerThread() {
        return new Thread(() -> {
            int countTime = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(countTime++);
            }
        });
    }
}
