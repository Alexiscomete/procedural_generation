package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

public class WarpNode implements Node {
    private final Node node;
    private final double xVector1;
    private final double yVector1;
    private final double xVector2;
    private final double yVector2;
    private final double xCoefficient;
    private final double yCoefficient;

    public WarpNode(Node node, double xVector1, double yVector1, double xVector2, double yVector2, double xCoefficient, double yCoefficient) {
        this.node = node;
        this.xVector1 = xVector1;
        this.yVector1 = yVector1;
        this.xVector2 = xVector2;
        this.yVector2 = yVector2;
        this.xCoefficient = xCoefficient;
        this.yCoefficient = yCoefficient;
    }

    @Override
    public double getValue(double x, double y) {
        return node.getValue(
                x + xCoefficient * (node.getValue(x + xVector1, y + yVector1) * 2 - 1),
                y + yCoefficient * (node.getValue(x + xVector2, y + yVector2) * 2 - 1)
        );
    }

    @Override
    public double getWeight() {
        return 0;
    }
}
