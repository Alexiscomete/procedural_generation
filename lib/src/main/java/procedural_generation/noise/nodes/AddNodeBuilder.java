package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

public class AddNodeBuilder implements NodeBuilder {
    // nodes to give to the AddNode
    private final NodeBuilder[] nodes;

    public AddNodeBuilder(NodeBuilder... nodes) {
        this.nodes = nodes;
    }

    @Override
    public AddNode build(long seed) {
        Node[] nodes = new Node[this.nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = this.nodes[i].build(seed);
        }
        return new AddNode(nodes);
    }
}
