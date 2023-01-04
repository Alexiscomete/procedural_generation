package procedural_generation.noise.nodes;

import procedural_generation.noise.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMaskNode implements Node {
    private final BufferedImage image;
    private final double weight;
    private final Color colorMax;
    private final Color colorMin;
    private final int neighbors;

    public ImageMaskNode(BufferedImage image, double weight, Color colorMax, Color colorMin, int neighbors) {
        this.image = image;
        this.weight = weight;
        this.colorMax = colorMax;
        this.colorMin = colorMin;
        this.neighbors = neighbors;
    }

    public double getNeighborsValue(int distance, double x, double y) {
        x = Math.round(x);
        y = Math.round(y);
        if (distance <= 0) {
            return 0;
        }
        double value = 0;
        // get the average value of the neighbors for the given distance. The value is between 0 and 1. The pixels inside the square are excluded.
        // first row
        for (int i = 0; i < distance * 2 + 1; i++) {
            value += getSimpleValue(x - distance + i, y - distance);
        }
        // first column
        for (int i = 1; i < distance * 2; i++) {
            value += getSimpleValue(x - distance, y - distance + i);
        }
        // last column
        for (int i = 1; i < distance * 2; i++) {
            value += getSimpleValue(x + distance, y - distance + i);
        }
        // last row
        for (int i = 0; i < distance * 2 + 1; i++) {
            value += getSimpleValue(x - distance + i, y + distance);
        }
        return value / (distance * 8);
    }

    public double getSimpleValue(double x, double y) {
        // case 1 : out of bounds
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return 0.5;
        }
        // case 2 : in bounds but not on a pixel (double value -> interpolation needed)
        if (x % 1 != 0) {
            if (y % 1 != 0) {
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
                double x1y1w = getSimpleValue(x1, y1) * x1w * y1w;
                double x1y2w = getSimpleValue(x1, y2) * x1w * y2w;
                double x2y1w = getSimpleValue(x2, y1) * x2w * y1w;
                double x2y2w = getSimpleValue(x2, y2) * x2w * y2w;
                // the weighted sum of the pixels
                return (x1y1w + x1y2w + x2y1w + x2y2w) / (x1w * y1w + x1w * y2w + x2w * y1w + x2w * y2w);
            } else {
                // the pixels around the point
                int x1 = (int) Math.floor(x);
                int x2 = (int) Math.ceil(x);
                int y1 = (int) y;
                // the weight of the pixels
                double x1w = x2 - x;
                double x2w = x - x1;
                // the weighted value of the pixels
                double x1y1w = getSimpleValue(x1, y1) * x1w;
                double x2y1w = getSimpleValue(x2, y1) * x2w;
                // the weighted sum of the pixels
                return (x1y1w + x2y1w) / (x1w + x2w);
            }
        } else if (y % 1 != 0) {
            // the pixels around the point
            int x1 = (int) x;
            int y1 = (int) Math.floor(y);
            int y2 = (int) Math.ceil(y);
            // the weight of the pixels
            double y1w = y2 - y;
            double y2w = y - y1;
            // the weighted value of the pixels
            double x1y1w = getSimpleValue(x1, y1) * y1w;
            double x1y2w = getSimpleValue(x1, y2) * y2w;
            // the weighted sum of the pixels
            return (x1y1w + x1y2w) / (y1w + y2w);
        }
        // case 3 : in bounds and on a pixel : 0 if near colorMin, 1 if near colorMax, 0.5 if in between
        Color color = new Color(image.getRGB((int) x, (int) y));
        // distance to colorMin
        double dMin = Math.sqrt(Math.pow(color.getRed() - colorMin.getRed(), 2) + Math.pow(color.getGreen() - colorMin.getGreen(), 2) + Math.pow(color.getBlue() - colorMin.getBlue(), 2));
        // distance to colorMax
        double dMax = Math.sqrt(Math.pow(color.getRed() - colorMax.getRed(), 2) + Math.pow(color.getGreen() - colorMax.getGreen(), 2) + Math.pow(color.getBlue() - colorMax.getBlue(), 2));
        // case 3.1 : color is near colorMin
        if (dMin * 1.1 < dMax) {
            return 0.15;
        }
        // case 3.2 : color is near colorMax
        if (dMax * 1.1 < dMin) {
            return 0.85;
        }
        // case 3.3 : color is in between
        return 0.5;
    }

    @Override
    public double getValue(double x, double y) {
        double value = getSimpleValue(x, y);
        double divideBy = 1;
        for (int i = 1; i <= neighbors; i++) {
            value += getNeighborsValue(i, x, y) / i;
            divideBy += (1.0 / i);
        }
        return value / divideBy;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
