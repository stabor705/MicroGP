package xyz.stabor.microgp.report;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.learning.LearningProcess;
import xyz.stabor.microgp.learning.Report;
import xyz.stabor.microgp.learning.fitness.OutputContains;
import xyz.stabor.microgp.learning.fitness.SquareDistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicTests {
    public static void main(String[] args) {
        ex12A();
    }

    private static void ex11A() {
        List<List<Double>> data = emptyData(100);
        LearningProcess learningProcess = new LearningProcess(
                data,
                data,
                new OutputContains(1.0),
                new GenerationContext(5, 4, 3, 10),
                100,
                100,
                0.0
        );
        runAndPrintResults("ex11A", learningProcess);
    }

    private static void ex11B() {
        List<List<Double>> data = emptyData(1);
        LearningProcess learningProcess = new LearningProcess(
                data,
                data,
                new OutputContains(789.0),
                new GenerationContext(5, 4, 3, 1000),
                100,
                10000,
                0.0
        );
        runAndPrintResults("ex11B", learningProcess);
    }

    private static void ex12A() {
        int dataLen = 100;
        Random rng = new Random();

        List<List<Double>> inputs = new ArrayList<>();
        List<List<Double>> outputs = new ArrayList<>();
        for (int i = 0; i < dataLen; i++) {
            Double a = (double)rng.nextInt(10);
            Double b = (double)rng.nextInt(10);
            inputs.add(List.of(a, b));
            outputs.add(List.of(a + b));

        }
        LearningProcess learningProcess = new LearningProcess(
                inputs,
                outputs,
                new SquareDistance(),
                new GenerationContext(5, 4, 2, 10),
                100,
                1000,
                -1.0
        );
        runAndPrintResults("ex12A", learningProcess);
    }

    private static List<List<Double>> emptyData(int size) {
        List<List<Double>> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(List.of());
        }
        return data;
    }

    private static void runAndPrintResults(String testName, LearningProcess learningProcess) {
        Report report = learningProcess.run();
        printFitnessHistory(testName, report);
        printProgram(testName, report);
        System.out.println(report.bestIndividual.toString());
    }

    private static void printFitnessHistory(String testName, Report report) {
        String csvName = String.format("%s-fitness.csv", testName);
        try {
            report.writeFitnessHistoryToCsv(csvName);
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + csvName + ": " + e);
        }
    }

    private static void printProgram(String testName, Report report) {
        String progName = String.format("%s-best.txt", testName);
        File file = new File(progName);
        try {
            try(PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.println(report.bestIndividual.toString());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + progName + ": " + e);
        }
    }
}
