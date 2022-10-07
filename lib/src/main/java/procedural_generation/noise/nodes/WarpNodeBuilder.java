package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

public class WarpNodeBuilder implements NodeBuilder {
    private final NodeBuilder node;
    private final double xVector1;
    private final double yVector1;
    private final double xVector2;
    private final double yVector2;
    private final double xCoefficient;
    private final double yCoefficient;

    public WarpNodeBuilder(NodeBuilder node, double xVector1, double yVector1, double xVector2, double yVector2, double xCoefficient, double yCoefficient) {
        this.node = node;
        this.xVector1 = xVector1;
        this.yVector1 = yVector1;
        this.xVector2 = xVector2;
        this.yVector2 = yVector2;
        this.xCoefficient = xCoefficient;
        this.yCoefficient = yCoefficient;
    }

    @Override
    public Node build(long seed) {
        return new WarpNode(node.build(seed), xVector1, yVector1, xVector2, yVector2, xCoefficient, yCoefficient);
    }
}
