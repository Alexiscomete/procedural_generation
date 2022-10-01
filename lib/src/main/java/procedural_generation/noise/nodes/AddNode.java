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
        // normalize the value to be between 0 and 1 : add the weights of all the nodes and divide by it
        double weight = 0;
        for (Node node : nodes) {
            weight += node.getWeight();
        }
        if (weight == 0) {
            return 0;
        }
        return value / weight;
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
