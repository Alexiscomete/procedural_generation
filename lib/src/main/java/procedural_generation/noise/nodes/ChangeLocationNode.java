package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

public class ChangeLocationNode implements Node {
    private final Node node;
    private final Operation operationX, operationY;
    private final double valueX, valueY;

    public ChangeLocationNode(Node node, Operation operationX, Operation operationY, double valueX, double valueY) {
        this.node = node;
        this.operationX = operationX;
        this.operationY = operationY;
        this.valueX = valueX;
        this.valueY = valueY;
    }

    @Override
    public double getValue(double x, double y) {
        double newX = operationX.apply(x, valueX);
        double newY = operationY.apply(y, valueY);
        return node.getValue(newX, newY);
    }
}
