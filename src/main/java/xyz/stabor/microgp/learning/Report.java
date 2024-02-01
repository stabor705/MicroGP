package xyz.stabor.microgp.learning;

import xyz.stabor.microgp.geneticast.GeneticAST;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Report {
    public record Record(double avgFitness, double bestFitness) {}

    public List<Record> records = new ArrayList<>();
    public GeneticAST bestIndividual = null;

    public void appendRecord(double avgFitness, double bestFitness) {
        this.records.add(new Record(avgFitness, bestFitness));
    }

    public void setBestIndividual(GeneticAST bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public void writeFitnessHistoryToCsv(String filename) throws FileNotFoundException {
        File output = new File(filename);
        try (PrintWriter printWriter = new PrintWriter(output)) {
            records.stream()
                    .map(record -> String.join(",", List.of(String.valueOf(record.avgFitness), String.valueOf(record.bestFitness))))
                    .forEach(printWriter::println);
        }
    }
}
