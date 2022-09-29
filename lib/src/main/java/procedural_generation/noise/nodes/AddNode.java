package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

public class AddNode implements Node {
    // nodes to add
    private final Node[] nodes;

    public AddNode(Node... nodes) {
        this.nodes = nodes;
    }

    @Override
    public double getValue(double x, double y) {
        double value = 0;
        for (Node node : nodes) {
            value += node.getValue(x, y);
        }
        // divide by number of nodes to get average
        return value / nodes.length;
    }
}
