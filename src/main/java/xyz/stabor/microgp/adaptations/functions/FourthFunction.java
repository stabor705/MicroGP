package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class FourthFunction implements AdaptationInterface {
    @Override
    public double calculateFitnessForProgram(String program){
        List<Double> output = Interpreter.interpret(program, List.of());
        if (output.isEmpty()) return 0;

        double firstValueFitness = (output.get(0) == 1.0) ? 1.0 : 0;
        double otherValuesFitness = output.stream()
                .skip(1)
                .mapToDouble(value -> 1.0 / (1.0 + Math.abs(value - 1.0)))
                .average()
                .orElse(0);

        return (firstValueFitness + otherValuesFitness);
    }



}
