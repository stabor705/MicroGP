package xyz.stabor.microgp.geneticast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.RepeatedTest;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.geneticast.variables.Program;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneticsTests {

    @RepeatedTest(100)
    void testMutatedProgramsAreSyntacticallyCorrect() {
        Program program = Program.generate(new GenerationContext(5, 10, 10, 1000));
        GeneticAST ast = new GeneticAST(program);
        ast.mutate(new GenerationContext(5, 10, 10, 1000));
        CharStream inputStream = CharStreams.fromString(ast.toString());
        if(ast.toString().contains("+")){
            System.out.println(ast);
        }
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        parser.program();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @RepeatedTest(100)
    void testCrossedProgramsAreSyntacticallyCorrect() {
        Program programA = Program.generate(new GenerationContext(5, 10, 10, 1000));
        Program programB = Program.generate(new GenerationContext(5, 10, 10, 1000));
        GeneticAST astA = new GeneticAST(programA);
        GeneticAST astB = new GeneticAST(programB);
        astA.crossover(astB, new GenerationContext(5, 10, 10, 1000));

        CharStream inputStream = CharStreams.fromString(astA.toString());
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        parser.program();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
}
