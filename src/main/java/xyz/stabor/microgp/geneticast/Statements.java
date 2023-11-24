package xyz.stabor.microgp.geneticast;

import java.util.List;

public class Statements {
    private static NodeTypeUnion statements = new NodeTypeUnion(List.of(
            Assignment.class, If.class, While.class, Print.class, Read.class, Block.class
    ));

    public static GeneticNode generateRandom() {
        return statements.generateRandom();
    }
}
