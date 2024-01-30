package xyz.stabor.microgp.learning;

import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.interpreter.InterpreterUtils;
import xyz.stabor.microgp.learning.fitness.OutputContains;

import java.util.ArrayList;
import java.util.List;

public class BasicTests {
    @Test
    void learnProgramThatOuputs1() {
        List<List<Double>> inputs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            inputs.add(List.of());
        }
        LearningProcess learningProcess = new LearningProcess(
                inputs,
                inputs,
                new OutputContains(1.0),
                new GenerationContext(5, 4, 3, 10),
                100
        );
        Report report = learningProcess.run(100, 0.0);
        List<Double> output = InterpreterUtils.runProgram(report.bestIndividual.toString(), List.of());
        System.out.println(report.bestIndividual);
        System.out.println(output);
    }

    @Test
    void learnProgramThatOutputs789() {
        List<List<Double>> inputs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            inputs.add(List.of());
        }
        LearningProcess learningProcess = new LearningProcess(
                inputs,
                inputs,
                new OutputContains(798.0),
                new GenerationContext(5, 4, 3, 10000),
                100
        );
        Report report = learningProcess.run(1000, 0.0);
        List<Double> output = InterpreterUtils.runProgram(report.bestIndividual.toString(), List.of());
        System.out.println(report.bestIndividual);
        System.out.println(output);
    }
}
