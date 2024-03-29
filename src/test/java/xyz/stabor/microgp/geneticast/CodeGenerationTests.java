package xyz.stabor.microgp.geneticast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.RepeatedTest;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.geneticast.variables.Program;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeGenerationTests {

    @RepeatedTest(1000)
    void testGeneratedProgramsAreMostProbablySyntacticallyCorrect() {
        GeneticNode program = Program.generate(new GenerationContext(10, 5, 5, 5));
        CharStream inputStream = CharStreams.fromString(program.toString());
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        parser.program();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
}
