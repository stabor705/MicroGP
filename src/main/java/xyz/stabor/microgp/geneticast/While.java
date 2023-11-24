package xyz.stabor.microgp.geneticast;

import java.util.Random;

public class While extends GeneticNode {
    private GeneticNode condition;
    private Block block;
    public While() {
        this.condition = Conditions.generateRandom();
        this.block = new Block();
    }

    @Override
    public GeneticNode generateChild() {
        Random rng = new Random();
        if (rng.nextBoolean()) {
            return condition.generateChild();
        } else {
            return block.generateChild();
        }
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("while ");
        stringBuilder.append(condition.getText());
        stringBuilder.append(block.getText());
        return stringBuilder.toString();
    }
}
