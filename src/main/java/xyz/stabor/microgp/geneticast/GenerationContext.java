package xyz.stabor.microgp.geneticast;

public record GenerationContext(int maxDepth, int maxWidth, int maxVars, int currentDepth, int maxConstValue) {
    public GenerationContext(int maxDepth, int maxWidth, int maxVars, int maxConstVal) {
        this(maxDepth, maxWidth, maxVars, 0, maxConstVal);
    }

    public int remainingHeight() {
        return maxDepth - currentDepth;
    }

    public GenerationContext deeper() {
        return new GenerationContext(maxDepth, maxWidth, maxVars, currentDepth + 1, maxConstValue);
    }
}
