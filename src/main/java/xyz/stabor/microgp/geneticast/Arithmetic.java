package xyz.stabor.microgp.geneticast;

import java.util.ArrayList;
import java.util.List;

public abstract class Arithmetic<T extends GeneticNode> extends GeneticNode {
    private List<Character> operations = new ArrayList<>();
    protected Arithmetic(List<GeneticNode> children) {
        super(children);
    }

    abstract protected char[] getAllowedOperationTypes();

    private final char[] allowedOperationTypes = getAllowedOperationTypes();

    @Override
    public String getTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%s");
        for (int i = 1; i < children.size(); i++) {
            stringBuilder.append(operations.get(i - 1));
            stringBuilder.append("%s");
        }
        return stringBuilder.toString();
    }
}
