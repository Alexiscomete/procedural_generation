package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

public class ValueOperationNode implements Node {
    private final Node node;
    private final ValueOperation operation;
    private final double value;

    public ValueOperationNode(Node node, ValueOperation operation, double value) {
        this.node = node;
        this.operation = operation;
        this.value = value;
    }

    @Override
    public double getValue(double x, double y) {
        return operation.apply(node.getValue(x, y), value);
    }

    @Override
    public double getWeight() {
        return node.getWeight();
    }
}
