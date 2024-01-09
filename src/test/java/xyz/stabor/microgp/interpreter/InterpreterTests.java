package xyz.stabor.microgp.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTests {
    private MicroGPParser getParser(String program) {
        CharStream stream = CharStreams.fromString(program);
        MicroGPLexer lexer = new MicroGPLexer(stream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(commonTokenStream);
        return parser;
    }

    private List<Double> interpret(String program, List<Double> input) {
        MicroGPParser parser = getParser(program);
        MicroGPParser.ProgramContext ctx = parser.program();
        Interpreter interpreter = new Interpreter(input);
        interpreter.visitProgram(ctx);
        return interpreter.readOutput();
    }

    private List<Double> interpret(String program) {
        return interpret(program, List.of());
    }

    private Double evaluate(String expression) {
        MicroGPParser parser = getParser(expression);
        MicroGPParser.ExpressionContext ctx = parser.expression();
        Interpreter interpreter = new Interpreter();
        return interpreter.visitExpression(ctx);
    }

    private Double evalCondition(String condition) {
        MicroGPParser parser = getParser(condition);
        MicroGPParser.ConditionContext ctx = parser.condition();
        Interpreter interpreter = new Interpreter();
        return interpreter.visitCondition(ctx);
    }

    @Test
    void expressionsAreEvaluatedCorrectly() {
        assertEquals(3.0, evaluate("1 + 2"));
        assertEquals(1.119, evaluate("9.123 - 8.004"), 0.0001);
        assertEquals(2, evaluate("4 / 2"));
        assertEquals(32, evaluate("16 * 2"));
    }

    @Test
    void conditionsAreEvaluatedCorrectly() {
        assertEquals(1.0, evalCondition("1 == 1"));
        assertEquals(0.0, evalCondition("2 == 1"));
        assertEquals(1.0, evalCondition("2 > 1"));
        assertEquals(0.0, evalCondition("2 < 1"));
        assertEquals(1.0, evalCondition("2 >= 2"));
        assertEquals(0.0, evalCondition("2 <= 1"));
        assertEquals(1.0, evalCondition("2 == 2 && 3 == 3"));
        assertEquals(0.0, evalCondition("2 == 0 || 1 == 3"));
        assertEquals(0.0, evalCondition("!(2 == 2)"));
        assertEquals(1.0, evalCondition("2 + 2 == 4"));
    }

    @Test
    void printStatementWorksCorrectly() {
        assertEquals(List.of(123.0), interpret("print 123;"));
        assertEquals(List.of(4.0), interpret("print 2 + 2;"));
    }

    @Test
    void readStatementWorksCorrectly() {
        assertEquals(List.of(123.0), interpret("read a; print a;", List.of(123.0)));
    }

    @Test
    void ifStatementWorksCorrectly() {
        assertEquals(List.of(1.0), interpret("if 2 + 2 == 4 { print 1.0; }"));
        assertEquals(List.of(), interpret("if 2 + 2 == 5 { print 1.0; }"));
        assertEquals(List.of(1.0), interpret("if 2 + 2 == 5 { print 0.0; } else { print 1.0; }"));
        assertEquals(List.of(0.0), interpret("if 2 + 2 == 4 { print 0.0; } else { print 1.0; }"));
    }

    @Test
    void whileStatementWorksCorrectly() {
        assertEquals(List.of(1.0, 2.0, 3.0, 4.0, 5.0), interpret("i = 1; while i <= 5 { print i; i = i + 1; }"));
    }

    @Test
    void ifStatementsAreRunCorrectly() {
        String program = "if (true) { print 123 }";
    }

}
