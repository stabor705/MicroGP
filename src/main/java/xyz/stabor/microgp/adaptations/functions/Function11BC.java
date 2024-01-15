package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class Function11BC implements AdaptationInterface {
    @Override
    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, List.of());
        return output.stream()
                .mapToDouble(value -> Math.abs(value - targetValue) < 1.0 ? 1.0 : 1.0 / (1.0 + Math.abs(value - targetValue)))
                .max()
                .orElse(0);
    }

    public void initializeTargetValue(double _target){
        targetValue = _target;
    }
    double targetValue;
}
