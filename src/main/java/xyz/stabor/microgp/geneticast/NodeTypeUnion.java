package xyz.stabor.microgp.geneticast;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Random;

public class NodeTypeUnion {
    private List<Class<?>> nodeTypeClasses;
    private Random rng = new Random();

    public NodeTypeUnion(List<Class<?>> nodeTypeClasses) {
        for (Class<?> nodeTypeClass : nodeTypeClasses) {
            assert nodeTypeClass.isAssignableFrom(Node.class);
            try {
                nodeTypeClass.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Every Node subclass has to have empty constructor: " + e);
            }
        }

        this.nodeTypeClasses = nodeTypeClasses;
    }

    public Node generateRandom() {
        Random rng = new Random();
        int roll = rng.nextInt(nodeTypeClasses.size());
        Class<?> nodeTypeClass = nodeTypeClasses.get(roll);
        try {
            Constructor<?> nodeTypeClassConstructor = nodeTypeClass.getConstructor();
            return (Node)nodeTypeClassConstructor.newInstance();
        } catch (Exception e) {
            System.err.println("Could not instantiate random node type: " + e);
        }
        return null;
    }
}
