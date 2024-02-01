package xyz.stabor.microgp.geneticast.unions;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class NodeTypeUnion {
    private List<Class<?>> nodeTypeClasses;
    protected static Random rng = new Random();

    public List<Class<?>> getNodeTypeClasses() {
        return nodeTypeClasses;
    }

    public NodeTypeUnion(List<Class<?>> nodeTypeClasses) {
        for (Class<?> nodeTypeClass : nodeTypeClasses) {
            //assert nodeTypeClass.isAssignableFrom(GeneticNode.class);

            ensureGenerateMethodPresent(nodeTypeClass);
            getMinHeight(nodeTypeClass);
        }
        this.nodeTypeClasses = nodeTypeClasses;
    }

    static private void ensureGenerateMethodPresent(Class<?> nodeTypeClass) {
        try {
            Method generateMethod = nodeTypeClass.getMethod("generate", GenerationContext.class);
            assert Modifier.isStatic(generateMethod.getModifiers());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Every GeneticNode has to have generate method: " + e);
        }
    }

    static private int getMinHeight(Class<?> nodeTypeClass) {
        try {
            Field minHeightField = nodeTypeClass.getField("minHeight");
            return minHeightField.getInt(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("GeneticNode " + nodeTypeClass.getName() + " has to have minHeight field" + e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("minHeight field of " + nodeTypeClass.getName() + "has to be static final" + e);
        }
    }

    public GeneticNode generate(GenerationContext ctx) {
        Random rng = new Random();
        List<Class<?>> permissibleNodeTypeClasses = nodeTypeClasses.stream()
                .filter(nodeTypeClass -> getMinHeight(nodeTypeClass) <= ctx.remainingHeight()).toList();
        if(!permissibleNodeTypeClasses.isEmpty()){
            int roll = rng.nextInt(permissibleNodeTypeClasses.size());
            Class<?> nodeTypeClass = permissibleNodeTypeClasses.get(roll);
            try {
                Method generationMethod = nodeTypeClass.getMethod("generate", GenerationContext.class);
                return (GeneticNode)generationMethod.invoke(null, ctx);
            } catch (Exception e) {
                System.err.println("Could not instantiate random node for type" + nodeTypeClass.getName() + ": " + e);
            }
        }
        return null;
    }
}
