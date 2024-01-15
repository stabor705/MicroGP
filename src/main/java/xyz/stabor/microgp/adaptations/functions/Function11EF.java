package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class Function11EF implements AdaptationInterface {

    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, List.of());

        if (output.size() == 1 && Math.abs(output.get(0) - targetValue) < 0.5) {
            return 1.0;
        }
        else {
            if(!output.isEmpty())
            {
                return 1.0 / (1.0 + Math.abs(output.get(0) - targetValue));
            }
            else{
                return 0;
            }
        }
    }

    public void initializeTargetValue(double _target){
        targetValue = _target;
    }
    double targetValue;

}
