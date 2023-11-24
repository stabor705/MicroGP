package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
class If extends GeneticNode {
    private GeneticNode condition;
    private Block trueBlock;
    private Block falseBlock;

    public If() {
        this.condition = Conditions.generateRandom();
        this.trueBlock = new Block();
        this.falseBlock = new Block();
    }

    @Override
    public GeneticNode generateChild() {
        Random rng = new Random();
        if (rng.nextBoolean()) {
            return trueBlock.generateChild();
        } else {
            return falseBlock.generateChild();
        }
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("if");
        stringBuilder.append(condition.getText());
        stringBuilder.append("\n");
        stringBuilder.append(trueBlock.getText());
        if (falseBlock != null) {
            stringBuilder.append("else\n");
            stringBuilder.append(falseBlock);
        }
        return stringBuilder.toString();
    }
}
