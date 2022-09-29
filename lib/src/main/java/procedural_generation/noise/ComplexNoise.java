package procedural_generation.noise;

public class ComplexNoise {
    private final Node node;

    public ComplexNoise(Node node) {
        this.node = node;
    }

    public double getValue(double x, double y) {
        return node.getValue(x, y);
    }
}
