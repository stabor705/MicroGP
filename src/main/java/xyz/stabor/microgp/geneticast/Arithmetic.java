package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Arithmetic<T extends Node> extends Node {
    abstract protected char[] getAllowedOperationTypes();
    abstract protected T getGenerated();

    @AllArgsConstructor
    private class Operation {
        private char operationType;
        private T right;
    }
    private final List<Operation> operations = new ArrayList<>();
    private final T left;
    private final char[] allowedOperationTypes = getAllowedOperationTypes();

    public Arithmetic(T left) {
        this.left = left;
    }

    @Override
    public Node generateChild() {
        Random rng = new Random();
        if (rng.nextBoolean()) {
            return appendRandomOperation();
        } else {
            return appendRandomOperation();
            //return expandRandomOperation();
        }
    }

    private Node appendRandomOperation() {
        Random rng = new Random();
        char operationType = allowedOperationTypes[rng.nextInt(allowedOperationTypes.length)];
        T operationRhs = getGenerated();
        operations.add(new Operation(operationType, operationRhs));
        return operationRhs;
    }

    private Node expandRandomOperation() {
        Random rng = new Random();
        int nodeIdx = rng.nextInt(1 + operations.size());
        if (nodeIdx == 0) {
            return left.generateChild();
        } else {
            return operations.get(nodeIdx - 1).right.generateChild();
        }
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(left.getText());
        for (Operation operation : operations) {
            stringBuilder.append(operation.operationType);
            stringBuilder.append(operation.right.getText());
        }
        return stringBuilder.toString();
    }
}
