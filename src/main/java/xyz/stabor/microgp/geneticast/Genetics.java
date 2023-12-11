package xyz.stabor.microgp.geneticast;

import xyz.stabor.microgp.geneticast.unions.Conditions;
import xyz.stabor.microgp.geneticast.unions.Factors;
import xyz.stabor.microgp.geneticast.unions.NodeTypeUnion;
import xyz.stabor.microgp.geneticast.unions.Statements;
import xyz.stabor.microgp.geneticast.variables.Assignment;
import xyz.stabor.microgp.geneticast.variables.Block;
import xyz.stabor.microgp.geneticast.variables.If;
import xyz.stabor.microgp.geneticast.variables.While;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Genetics {
    private static final List<NodeTypeUnion> unions = List.of(
            Conditions.getInstance() /*,Factors.getInstance()*/, Statements.getInstance()
    );

    public static GeneticNode generateReplacingNode(GeneticNode node, GenerationContext ctx) {
        List<Class<?>> replacingClasses = findReplacingClasses(node);
        NodeTypeUnion nodeTypeUnion = new NodeTypeUnion(replacingClasses);
        return nodeTypeUnion.generate(ctx);
    }

    public static boolean canBeCrossed(GeneticNode nodeA, GeneticNode nodeB) {
        List<Class<?>> replacingClasses = findReplacingClasses(nodeA);
        return replacingClasses.contains(nodeB.getClass()) && nodeA != nodeB;
    }

    private static List<Class<?>> findReplacingClasses(GeneticNode node) {
        Class<?> nodeTypeClass = node.getClass();
        if (nodeTypeShouldNotBeReplaced(node)) {
            return List.of(nodeTypeClass);
        }
        return new ArrayList<>(unions.stream()
                .filter(union -> union.getNodeTypeClasses().contains(nodeTypeClass))
                .findFirst()
                .map(NodeTypeUnion::getNodeTypeClasses)
                .orElse(List.of(nodeTypeClass)));
    }

    private static boolean nodeTypeShouldNotBeReplaced(GeneticNode node) {
        return Stream.of(
                isBlockUnderIfOrWhile(node),
                isLValue(node)
        ).anyMatch(Boolean::booleanValue);
    }

    private static boolean isBlockUnderIfOrWhile(GeneticNode node) {
        if (node.parent == null)
            return false;
        Class<?> parentType = node.parent.getClass();
        return (parentType == While.class || parentType == If.class) && node.getClass() == Block.class;
    }

    private static boolean isLValue(GeneticNode node) {
        if (node.parent == null)
            return false;
        return node.parent.getClass() == Assignment.class && node.getClass() == Factors.Identifier.class;
    }
}
