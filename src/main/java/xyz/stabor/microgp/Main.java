package xyz.stabor.microgp;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.geneticast.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
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
