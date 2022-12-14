package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

public class ChangeSeedNodeBuilder implements NodeBuilder {
    private final Operation operation;
    private final NodeBuilder nodeBuilder;
    private final long value;

    public ChangeSeedNodeBuilder(Operation operation, long value, NodeBuilder nodeBuilder) {
        this.operation = operation;
        this.nodeBuilder = nodeBuilder;
        this.value = value;
    }

    @Override
    public Node build(long seed) {
        return nodeBuilder.build(operation.apply(seed, value));
    }
}
