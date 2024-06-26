package ru.didcvee;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java -Xmx1G -jar target/my-new-maven-project-1.0-SNAPSHOT.jar <input-file>");
            return;
        }

        LocalTime startTime = LocalTime.now();
        Thread timerThread = createTimerThread();
        timerThread.start();

        System.out.println("--------- начало чтения файла...");

        GroupAnalyzer lineStat = new GroupAnalyzer(args[0]);
        int groups = lineStat.analyze();

        timerThread.interrupt();

        System.out.println("--------- итоговое количество групп: " + groups);

        long secondsOfWork = ChronoUnit.SECONDS.between(startTime, LocalTime.now());
        System.out.printf("Выполнено, время работы алгоритма: %d сек. \n", secondsOfWork);
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
                System.out.println("--- текущее время чтения файла: " + countTime++ + " сек.");
            }
        });
    }
}