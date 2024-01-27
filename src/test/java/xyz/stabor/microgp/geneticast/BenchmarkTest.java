package xyz.stabor.microgp.geneticast;

import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.adaptations.functions.Function11BC;
import xyz.stabor.microgp.adaptations.functions.Function12_13_14;
import xyz.stabor.microgp.adaptations.functions.FunctionBoolean;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BenchmarkTest {
    Random rand = new Random();

    String filename = "BenchmarkTest.txt";

    List<GeneticAST> initializePrograms(int numOfPrograms, int initalHeight, int maxConstValue, int maxVars){
        List<GeneticAST> resultList = new ArrayList<>();
        for(int i =0 ; i < numOfPrograms; ++i){
            resultList.add(GeneticAST.generate(initalHeight, maxVars, maxConstValue));
        }
        return resultList;
    }

    private List<Double> castListToDouble(List<Integer> list){
        List<Double> resultList = new ArrayList<>();
        for(Integer value : list){
            resultList.add(value.doubleValue());
        }
        return resultList;
    }

    private void writeToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true); // true for append mode
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    void test1() {
        int numOfGenerations = 10;
        int maxConstValue = 5;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(1000), rand.nextInt(1000)));
        Integer targetValue = inputValues.stream().mapToInt(Integer::intValue).sum();
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function12_13_14 sixthFunction = new Function12_13_14();
        sixthFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, sixthFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 0.5) && output.size() == 1);
    }

    @Test
    void test17() {
        int numOfGenerations = 15;
        int maxConstValue = 100;
        int randomValue = rand.nextInt(1, 10);
        double targetValue = 0;
        for(int i = 1; i <= randomValue; ++i){
            targetValue += Math.pow(i, 2);
        }
        System.out.println(randomValue + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function11BC fitnessFunction = new Function11BC();
        fitnessFunction.initializeTargetValue(targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of test17.:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        double finalTargetValue = targetValue;
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - finalTargetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test27() {
        int numOfGenerations = 100;
        int maxConstValue = 1000;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(), rand.nextInt(), rand.nextInt()));
        Collections.sort(inputValues);
        System.out.println(inputValues + " target: " + inputValues.get(1));
        List<GeneticAST> programs = initializePrograms(30, 5, maxConstValue, 2);
        Function12_13_14 sixthFunction = new Function12_13_14();
        sixthFunction.readInput(inputValues, inputValues.get(1));
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, sixthFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of test27.:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - inputValues.get(1)) < 0.5) && output.size() == 1);
    }

    @Test
    void testBoolean() {
        int numOfGenerations = 10;
        int maxConstValue = 1;
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        FunctionBoolean fitnessFun = new FunctionBoolean(2);
        List<Boolean> targetValues = fitnessFun.getExpectedOutputs();
        System.out.println("Expected outputs: " + targetValues);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFun, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of testBoolean.:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertEquals(output.size(), targetValues.size());
        for(int i = 0 ; i < output.size(); ++i){
            assertEquals((output.get(i) == 1.0), targetValues.get(i));
        }
    }

}
