package xyz.stabor.microgp.adaptations.functions;

import org.junit.jupiter.api.RepeatedTest;
import xyz.stabor.microgp.adaptations.AdaptationInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstFunctionTest {

    @RepeatedTest(1)
    void testFirstFunctionCalculateFitness(){
        AdaptationInterface firstFunction = new FirstFunction();
        assertEquals(1.0, firstFunction.calculateFitnessForProgram("$1=1; $2=2; print $1; print $2;"));
        assertEquals(1.0, firstFunction.calculateFitnessForProgram("$1=1; $2=2; $3=3; print $1; print $2; print $3;"));

    }
}
