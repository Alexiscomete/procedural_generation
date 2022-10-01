package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

public class ValueOperationNodeBuilder implements NodeBuilder {
    private final NodeBuilder node;
    private final ValueOperation operation;
    private final double value;

    public ValueOperationNodeBuilder(NodeBuilder node, ValueOperation operation, double value) {
        this.node = node;
        this.operation = operation;
        this.value = value;
    }

    @Override
    public Node build(long seed) {
        return new ValueOperationNode(node.build(seed), operation, value);
    }
}
