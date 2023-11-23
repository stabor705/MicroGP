package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
class If extends Node {
    private Node condition;
    private Node trueBlock;
    private Node falseBlock;

    @Override
    public Node generateChild() {
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
        stringBuilder.append(trueBlock.getText());
        if (falseBlock != null) {
            stringBuilder.append("else");
            stringBuilder.append(falseBlock);
        }
        return stringBuilder.toString();
    }
}
