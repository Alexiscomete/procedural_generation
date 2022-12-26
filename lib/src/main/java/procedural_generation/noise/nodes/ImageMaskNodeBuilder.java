package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;
import procedural_generation.noise.NodeBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMaskNodeBuilder implements NodeBuilder {
    private final BufferedImage image;
    private final double weight;
    private final Color colorMax;
    private final Color colorMin;

    public ImageMaskNodeBuilder(BufferedImage image, double weight, Color colorMax, Color colorMin) {
        this.image = image;
        this.weight = weight;
        this.colorMax = colorMax;
        this.colorMin = colorMin;
    }

    @Override
    public Node build(long seed) {
        return new ImageMaskNode(image, weight, colorMax, colorMin);
    }
}
