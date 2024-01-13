package xyz.stabor.microgp.adaptations.functions;

import org.junit.jupiter.api.RepeatedTest;
import xyz.stabor.microgp.adaptations.AdaptationInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondFunctionTest {

    @RepeatedTest(1)
    void testFirstFunctionCalculateFitness(){
        AdaptationInterface secondFunction = new SecondFunction();
        double targetValue = 789;
        assertEquals(1.0, secondFunction.calculateFitnessForProgram("$1=1; $2=789; print $1; print $2;"));
        assertEquals(1.0, secondFunction.calculateFitnessForProgram("$1=789; $2=2; $3=789; print $1; print $2; print $3;"));

    }
}
