package ru.didcvee.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.didcvee.GroupAnalyzer;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class Bench {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    public void testMethod(Blackhole blackhole) {
        blackhole.consume(methodForTest());
    }

    public static int methodForTest() {
        GroupAnalyzer analyzer = new GroupAnalyzer("lng-big.csv"); // путь к файлу
        return analyzer.analyze();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Bench.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(options).run();
    }
}
