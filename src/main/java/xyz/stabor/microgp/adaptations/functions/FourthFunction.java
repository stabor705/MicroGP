package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class FourthFunction implements AdaptationInterface {
    @Override
    public double calculateFitnessForProgram(String program){
        List<Double> output = Interpreter.interpret(program, List.of());
        if (output.isEmpty()) return 0;

        double firstValueFitness = (output.get(0) == 1.0) ? 1.0 : 0; // Ensures first value is 1
        double otherValuesFitness = output.stream()
                .skip(1) // Skips the first value
                .mapToDouble(value -> 1.0 / (1.0 + Math.abs(value - 1.0)))
                .average() // Average fitness for other values
                .orElse(0);

        return (firstValueFitness + otherValuesFitness) ; // Averages the fitness of the first value and other values
    }



}
