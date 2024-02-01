package xyz.stabor.microgp.interpreter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import xyz.stabor.microgp.MicroGPBaseVisitor;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;

import java.util.*;

public class Interpreter extends MicroGPBaseVisitor<Double> {
    private List<Double> output = new ArrayList<>();
    private Queue<Double> input = new LinkedList<>();
    private Map<String, Double> symbols = new HashMap<>();
    private int maxTime = 100;
    private int timer = 0;

    public Interpreter() {}
    public Interpreter(List<Double> input) {
        this.input = new LinkedList<>(input);
    }

    public Interpreter(List<Double> input, int maxTime) {
        this.input = new LinkedList<>(input);
        this.maxTime = maxTime;
    }

    private static MicroGPParser getParser(String program) {
        CharStream stream = CharStreams.fromString(program);
        MicroGPLexer lexer = new MicroGPLexer(stream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(commonTokenStream);
        return parser;
    }

    public static List<Double> interpret(String program, List<Double> input) {
        StringBuilder newProgram = new StringBuilder();
        for(int i = 0; i < input.size(); ++i){
            newProgram.append("read $").append(i).append("; ");
        }
        newProgram.append(program);
        MicroGPParser parser = getParser(newProgram.toString());
        MicroGPParser.ProgramContext ctx = parser.program();
        Interpreter interpreter = new Interpreter(input);
        interpreter.visitProgram(ctx);
        return interpreter.readOutput();
    }

    @Override
    public Double visitAssignment(MicroGPParser.AssignmentContext ctx) {
        timer++;
        Double rvalue = visitExpression(ctx.expression());
        symbols.put(ctx.ID().getText(), rvalue);
        return rvalue;
    }

    @Override
    public Double visitExpression(MicroGPParser.ExpressionContext ctx) {
        double result = visitTerm(ctx.term(0));
        for (int i = 1; i < ctx.term().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            Double operand = visitTerm(ctx.term(i));
            if (operator.equals("+")) {
                result += operand;
            } else {
                result -= operand;
            }
        }
        return result;
    }

    @Override
    public Double visitTerm(MicroGPParser.TermContext ctx) {
        double result = visitFactor(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            Double operand  = visitFactor(ctx.factor(i));
            if (operator.equals("*")) {
                result *= operand;
            } else {
                if (operand == 0) {
                    result = 0;
                } else {
                    result /= operand;
                }
            }
        }
        return result;
    }

    @Override
    public Double visitFactor(MicroGPParser.FactorContext ctx) {
        if (ctx.NUMBER() != null) {
            return Double.parseDouble(ctx.NUMBER().getText());
        }
        if (ctx.TRUE() != null) {
            return 1.0;
        }

        if (ctx.FALSE() != null) {
            return 0.0;
        }

        if (ctx.ID() != null) {
            symbols.putIfAbsent(ctx.ID().getText(), 0.0);
            return symbols.get(ctx.ID().getText());
        }

        return visitExpression(ctx.expression());
    }

    @Override
    public Double visitCondition(MicroGPParser.ConditionContext ctx) {
        if (ctx.condition(0) != null) {
            if (ctx.NOT() != null) {
                return visitCondition(ctx.condition(0)) == 1.0 ? 0.0 : 1.0;
            }
            if (ctx.LPAREN() != null) {
                return visitCondition(ctx.condition(0));
            }
            Double c1 = visitCondition(ctx.condition(0));
            Double c2 = visitCondition(ctx.condition(1));
            if (ctx.AND() != null) {
                return (c1 == 1.0 && c2 == 1.0) ? 1.0 : 0.0;
            }
            return (c1 == 1.0 || c2 == 1.0) ? 1.0 : 0.0;
        }

        Double e1 = visitExpression(ctx.expression(0));
        Double e2 = visitExpression(ctx.expression(1));
        if (ctx.EQUAL() != null) {
            return Objects.equals(e1, e2) ? 1.0 : 0.0;
        }
        if (ctx.NOTEQUAL() != null) {
            return !Objects.equals(e1, e2) ? 1.0 : 0.0;
        }
        if (ctx.GT() != null) {
            return e1 > e2 ? 1.0 : 0.0;
        }
        if (ctx.LT() != null) {
            return e1 < e2 ? 1.0 : 0.0;
        }
        if (ctx.GTE() != null) {
            return e1 >= e2 ? 1.0 : 0.0;
        }
        return e1 <= e2 ? 1.0 : 0.0;
    }

    @Override
    public Double visitReadStatement(MicroGPParser.ReadStatementContext ctx) {
        timer++;
        Double data = input.poll();
        if (data == null) {
            symbols.put(ctx.ID().getText(), 0.0);
        } else {
            symbols.put(ctx.ID().getText(), data);
        }
        return symbols.get(ctx.ID().getText());
    }

    @Override
    public Double visitPrintStatement(MicroGPParser.PrintStatementContext ctx) {
        timer++;
        Double result = visitExpression(ctx.expression());
        if(result != 0.1){
            output.add(result);
        }
        return result;
    }

    @Override
    public Double visitIfStatement(MicroGPParser.IfStatementContext ctx) {
        Double cond = visitCondition(ctx.condition());
        if (cond == 1.0) {
            return visitBlock(ctx.block(0));
        }
        if (ctx.ELSE() != null) {
            return visitBlock(ctx.block(1));
        }
        return 0.0;
    }

    @Override
    public Double visitWhileLoop(MicroGPParser.WhileLoopContext ctx) {
        while (visitCondition(ctx.condition()) == 1.0) {
            timer++;
            visitBlock(ctx.block());
            if (timer > maxTime) {
                break;
            }
        }
        return 0.0;
    }

    @Override
    public Double visitProgram(MicroGPParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            child.accept(this);
            if (timer > maxTime) {
                break;
            }
        }
        return 0.0;
    }

    @Override
    public Double visitBlock(MicroGPParser.BlockContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            child.accept(this);
            if (timer > maxTime) {
                break;
            }
        }
        return 0.0;
    }

    public List<Double> readOutput() {
        List<Double> out = new ArrayList<>(output);
        return out;
    }

}