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
            value += node.getValue(x, y) * node.getWeight();
        }
        double weight = getWeight();
        if (weight != 0) {
            value /= weight;
        } else {
            value = 0;
        }
        return value;
    }

    @Override
    public double getWeight() {
        // weight is the average of the weights of the nodes
        double weight = 0;
        for (Node node : nodes) {
            weight += node.getWeight();
        }
        return weight / nodes.length;
    }
}
