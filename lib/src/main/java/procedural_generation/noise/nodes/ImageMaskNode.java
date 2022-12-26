package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMaskNode implements Node {
    private final BufferedImage image;
    private final double weight;
    private final Color colorMax;
    private final Color colorMin;

    public ImageMaskNode(BufferedImage image, double weight, Color colorMax, Color colorMin) {
        this.image = image;
        this.weight = weight;
        this.colorMax = colorMax;
        this.colorMin = colorMin;
    }

    @Override
    public double getValue(double x, double y) {
        // case 1 : out of bounds
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return 0.5;
        }
        // case 2 : in bounds but not on a pixel (double value -> interpolation needed)
        if (x % 1 != 0 || y % 1 != 0) {
            // the pixels around the point
            int x1 = (int) Math.floor(x);
            int x2 = (int) Math.ceil(x);
            int y1 = (int) Math.floor(y);
            int y2 = (int) Math.ceil(y);
            // the weight of the pixels
            double x1w = x2 - x;
            double x2w = x - x1;
            double y1w = y2 - y;
            double y2w = y - y1;
            // the weighted value of the pixels
            double x1y1w = getValue(x1, y1) * x1w * y1w;
            double x1y2w = getValue(x1, y2) * x1w * y2w;
            double x2y1w = getValue(x2, y1) * x2w * y1w;
            double x2y2w = getValue(x2, y2) * x2w * y2w;
            // the weighted sum of the pixels
            return (x1y1w + x1y2w + x2y1w + x2y2w) / (x1w * y1w + x1w * y2w + x2w * y1w + x2w * y2w);
        }
        // case 3 : in bounds and on a pixel : 0 if near colorMin, 1 if near colorMax, 0.5 if in between
        Color color = new Color(image.getRGB((int) x, (int) y));
        // distance to colorMin
        double dMin = Math.sqrt(Math.pow(color.getRed() - colorMin.getRed(), 2) + Math.pow(color.getGreen() - colorMin.getGreen(), 2) + Math.pow(color.getBlue() - colorMin.getBlue(), 2));
        // distance to colorMax
        double dMax = Math.sqrt(Math.pow(color.getRed() - colorMax.getRed(), 2) + Math.pow(color.getGreen() - colorMax.getGreen(), 2) + Math.pow(color.getBlue() - colorMax.getBlue(), 2));
        // case 1 : color is near colorMin
        if (dMin * 1.1 < dMax) {
            return 0;
        }
        // case 2 : color is near colorMax
        if (dMax * 1.1 < dMin) {
            return 1;
        }
        // case 3 : color is in between
        return 0.5;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
