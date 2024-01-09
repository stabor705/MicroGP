package xyz.stabor.microgp;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.geneticast.*;
import xyz.stabor.microgp.geneticast.variables.Program;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        String program = "asd = 1 + false;";
        CharStream stream = CharStreams.fromString(program);
        MicroGPLexer lexer = new MicroGPLexer(stream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(commonTokenStream);
        MicroGPParser.ProgramContext ctx = parser.program();
        Interpreter interpreter = new Interpreter();
        System.out.println(interpreter.visitProgram(ctx));
    }

    static void serializeAndDeserialize() {
        String programName = "example.microgp";
        String serializedName = "exampleast.bin";

        GeneticNode program = Program.generate(new GenerationContext(5));

        try {
            FileWriter fileWriter = new FileWriter(programName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(program.toString());
            bufferedWriter.close();
            fileWriter.close();

            FileOutputStream fileOutputStream = new FileOutputStream(serializedName);
            byte[] serialized = SerializationUtils.serialize(program);
            fileOutputStream.write(serialized);

            FileInputStream fileInputStream = new FileInputStream(serializedName);
            byte[] input = new byte[serialized.length];
            fileInputStream.read(input);
            Program deserialized = SerializationUtils.deserialize(input);
            System.out.println(deserialized);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
