package xyz.stabor.microgp.geneticast;

import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.functions.*;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvolveTest {
    Random rand = new Random();

    String filename = "EvolveTest.txt";

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
    void test11A() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue, 2);
        AdaptationInterface fitnessFunction = new Function11A();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.A:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11B() {
        int numOfGenerations = 10;
        double targetValue = 789.0;
        int maxConstValue = 100;
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function11BC fitnessFunction = new Function11BC();
        fitnessFunction.initializeTargetValue(targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction,maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.B:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0));
    }

    @Test
    void test11C() {
        int numOfGenerations = 10;
        double targetValue = 31415.0;
        int maxConstValue = 1000;
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function11BC fitnessFunction = new Function11BC();
        fitnessFunction.initializeTargetValue(targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction,maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.C:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0));
    }

    @Test
    void test11D() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        AdaptationInterface fitnessFunction = new Function11D();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.D:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11E() {
        int numOfGenerations = 10;
        double targetValue = 789.0;
        int maxConstValue = 10;
        List<GeneticAST> programs = initializePrograms(2000, 5, maxConstValue, 2);
        Function11EF fitnessFunction = new Function11EF();
        fitnessFunction.initializeTargetValue(targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.E:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.contains(targetValue) && output.size() == 1);
    }

    @Test
    void test11F() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue, 2);
        Function11EF fitnessFunction = new Function11EF();
        fitnessFunction.initializeTargetValue(targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        String content = "Output of 1.1.F:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.contains(targetValue) && output.size() == 1);
    }

    @Test
    void test12A() {
        int numOfGenerations = 10;
        int maxConstValue = 5;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(10), rand.nextInt(10)));
        Integer targetValue = inputValues.stream().mapToInt(Integer::intValue).sum();
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function14 sixthFunction = new Function14();
        sixthFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, sixthFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.2.A:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 0.5) && output.size() == 1);
    }

    @Test
    void test12B() {
        int numOfGenerations = 10;
        int maxConstValue = 5;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(-10, 10), rand.nextInt(-10, 10)));
        Integer targetValue = inputValues.stream().mapToInt(Integer::intValue).sum();
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, 2);
        Function12_13 sixthFunction = new Function12_13();
        sixthFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, sixthFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.2.B:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 0.5) && output.size() == 1);
    }

    @Test
    void test12C() {
        int numOfGenerations = 10;
        int maxConstValue = 1000;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(-9999, 9999), rand.nextInt(-9999, 9999)));
        Integer targetValue = inputValues.stream().mapToInt(Integer::intValue).sum();
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(3000, 5, maxConstValue, 2);
        Function12_13 seventhFunction = new Function12_13();
        seventhFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, seventhFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.1.C:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test12D() {
        int numOfGenerations = 10;
        int maxConstValue = 1000;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(-9999, 9999), rand.nextInt(-9999, 9999)));
        Integer targetValue = inputValues.stream()
                .mapToInt(Integer::intValue)
                .reduce((a, b) -> a - b)
                .orElse(0);
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(2000, 5, maxConstValue, 2);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.1.D:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test12E() {
        int numOfGenerations = 10;
        int maxConstValue = 1000;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(-9999, 9999), rand.nextInt(-9999, 9999)));
        Integer targetValue = inputValues.stream()
                .mapToInt(Integer::intValue)
                .reduce((a, b) -> a * b)
                .orElse(0);
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(2000, 5, maxConstValue, 2);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.1.E:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test13A() {
        int numOfGenerations = 10;
        int maxConstValue = 10;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(0, 9), rand.nextInt(0, 9)));
        Integer targetValue = Math.max(inputValues.get(0), inputValues.get(1));
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue, 2);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.3.A:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test13B() {
        int numOfGenerations = 10;
        int maxConstValue = 1000;
        List<Integer> inputValues = new ArrayList<>(Arrays.asList(rand.nextInt(-9999, 9999), rand.nextInt(-9999, 9999)));
        Integer targetValue = Math.max(inputValues.get(0), inputValues.get(1));
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(2000, 5, maxConstValue, 2);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.3.B:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - targetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test14A() {
        int numOfGenerations = 10;
        int maxConstValue = 5;
        List<Integer> inputValues = new ArrayList<>();
        int targetValue = 0;
        int randInt;
        for(int i =0; i < 10; ++i){
            randInt = rand.nextInt(-100, 100);
            inputValues.add(randInt);
            targetValue += randInt;
        }
        targetValue /= 10;
        System.out.println(inputValues + " target: " + targetValue);
        List<GeneticAST> programs = initializePrograms(200, 5, maxConstValue, 10);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.4.A:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        int finalTargetValue = targetValue;
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - finalTargetValue) < 1.0) && output.size() == 1);
    }

    @Test
    void test14B() {
        int numOfGenerations = 10;
        int maxConstValue = 10;
        int numOfInputVars = rand.nextInt(1, 10);
        List<Integer> inputValues = new ArrayList<>();
        int targetValue = 0;
        int randInt;
        for(int i = 0; i < numOfInputVars; ++i){
            randInt = rand.nextInt(-100, 100);
            inputValues.add(randInt);
            targetValue += randInt;
        }
        targetValue /= numOfInputVars;
        System.out.println(inputValues + " target: " + targetValue + " numOfInputVars: " + numOfInputVars);
        List<GeneticAST> programs = initializePrograms(1000, 5, maxConstValue, numOfInputVars);
        Function12_13 fitnessFunction = new Function12_13();
        fitnessFunction.readInput(inputValues, targetValue);
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, fitnessFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), castListToDouble(inputValues));
        System.out.println(output);
        String content = "Output of 1.4.B:\n" + bestProgram + "\n" + output + "\n";
        writeToFile(filename, content);
        int finalTargetValue = targetValue;
        assertTrue(output.stream().anyMatch(value -> Math.abs(value - finalTargetValue) < 1.0) && output.size() == 1);
    }



}

