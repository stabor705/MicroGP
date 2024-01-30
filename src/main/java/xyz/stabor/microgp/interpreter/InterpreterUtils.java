package xyz.stabor.microgp.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;

import java.util.List;

public class InterpreterUtils {
    public static List<Double> runProgram(String program, List<Double> input) {
        CharStream stream = CharStreams.fromString(program);
        MicroGPLexer lexer = new MicroGPLexer(stream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(commonTokenStream);
        MicroGPParser.ProgramContext ctx = parser.program();
        Interpreter interpreter = new Interpreter(input);
        interpreter.visitProgram(ctx);
        return interpreter.readOutput();
    }
}
