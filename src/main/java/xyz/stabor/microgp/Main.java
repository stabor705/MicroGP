package xyz.stabor.microgp;

import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.geneticast.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) {
        GeneticNode program = Block.generate(new GenerationContext(5));
        CharStream inputStream = CharStreams.fromString(program.toString());
        MicroGPLexer lexer = new MicroGPLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(tokenStream);
        ParseTree parseTree = parser.program();
        System.out.println(program);
    }
}
