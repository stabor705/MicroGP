package xyz.stabor.microgp.geneticast;

public record GenerationContext(int maxDepth, int maxWidth, int maxVars, int currentDepth) {
    public GenerationContext(int maxDepth, int maxWidth, int maxVars) {
        this(maxDepth, maxWidth, maxVars, 0);
    }

    public int remainingHeight() {
        return maxDepth - currentDepth;
    }

    public GenerationContext deeper() {
        return new GenerationContext(maxDepth, maxWidth, maxVars, currentDepth + 1);
    }
}
