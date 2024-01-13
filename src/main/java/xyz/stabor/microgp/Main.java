package xyz.stabor.microgp;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        GeneticNode program = Program.generate(new GenerationContext(5, 5, 5));
        System.out.println(program);
    }

    static void serializeAndDeserialize() {
        String programName = "example.microgp";
        String serializedName = "exampleast.bin";

        GeneticNode program = Program.generate(new GenerationContext(5, 5, 5));

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
