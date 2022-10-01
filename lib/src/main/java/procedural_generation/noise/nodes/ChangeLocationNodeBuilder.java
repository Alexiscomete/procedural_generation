package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

public class ChangeLocationNodeBuilder implements NodeBuilder {
    private final NodeBuilder nodeBuilder;
    private final Operation operationX, operationY;
    private final double valueX, valueY;

    public ChangeLocationNodeBuilder(NodeBuilder nodeBuilder, Operation operationX, Operation operationY, double valueX, double valueY) {
        this.nodeBuilder = nodeBuilder;
        this.operationX = operationX;
        this.operationY = operationY;
        this.valueX = valueX;
        this.valueY = valueY;
    }

    @Override
    public Node build(long seed) {
        return new ChangeLocationNode(nodeBuilder.build(seed), operationX, operationY, valueX, valueY);
    }
}
