package xyz.stabor.microgp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;
import xyz.stabor.microgp.geneticast.variables.Program;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.interpreter.Interpreter;
import xyz.stabor.microgp.interpreter.InterpreterUtils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Double> output = InterpreterUtils.runProgram("$1 = 1/$1*$1*7;read $1;print 5/2/$1-4*$0/$1*$1+$2/$1+2/2;", List.of());
        System.out.println(output);

    }

    static void serializeAndDeserialize() {
        String programName = "example.microgp";
        String serializedName = "exampleast.bin";
        GeneticNode program = Program.generate(new GenerationContext(5, 5, 5, 5));
        try {
            FileWriter fileWriter = new FileWriter(programName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(program.toString());
            bufferedWriter.close();
            fileWriter.close();

            FileOutputStream fileOutputStream = new FileOutputStream(serializedName);
            byte[] serialized = SerializationUtils.serialize(program);
            System.out.println("Serialized: " + serialized);
            fileOutputStream.write(serialized);

            FileInputStream fileInputStream = new FileInputStream(serializedName);
            byte[] input = new byte[serialized.length];
            fileInputStream.read(input);
            Program deserialized = SerializationUtils.deserialize(input);
            System.out.println("Deserialzied" + deserialized);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
