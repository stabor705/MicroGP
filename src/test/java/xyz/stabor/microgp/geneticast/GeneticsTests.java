package xyz.stabor.microgp.geneticast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.RepeatedTest;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneticsTests {

    @RepeatedTest(1000)
    void testMutatedProgramsAreSyntacticallyCorrect() {
        GeneticAST program = GeneticAST.generate(5, 5);
        GeneticAST mutated = program.mutate(new GenerationContext(5, 5, 5, 5));
        CharStream inputStream = CharStreams.fromString(mutated.toString());
        if(mutated.toString().contains("read")){
            System.out.println(mutated);
        }
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        parser.program();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @RepeatedTest(1000)
    void testCrossedProgramsAreSyntacticallyCorrect() {
        GeneticAST programA = GeneticAST.generate(5, 5);
        GeneticAST programB = GeneticAST.generate(5, 5);
        GeneticAST crossed = programA.crossover(programB);

        CharStream inputStream = CharStreams.fromString(crossed.toString());
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        parser.program();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
}
