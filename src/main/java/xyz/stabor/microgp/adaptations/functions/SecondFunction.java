package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class SecondFunction implements AdaptationInterface {
    @Override
    public double calculateFitnessForProgram(String program){
        List<Double> output = Interpreter.interpret(program, List.of());
        return output.stream()
                .mapToDouble(value -> 1.0 / (1.0 + Math.abs(value - 789.0)))
                .max()
                .orElse(0);
    }

}
