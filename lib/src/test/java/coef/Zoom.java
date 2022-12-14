package coef;

import procedural_generation.noise.ComplexNoise;
import procedural_generation.noise.ComplexNoiseBuilder;
import procedural_generation.noise.NoiseMapBuilder;
import procedural_generation.noise.nodes.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zoom {
    private double zoom;
    private final int change;

    public Zoom(double zoom, int change) {
        this.zoom = zoom;
        this.change = change;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void genImage(String title) {
        ComplexNoise complexNoise = new ComplexNoiseBuilder(
                        new ValueOperationNodeBuilder(
                                new ValueOperationNodeBuilder(
                                        new AddNodeBuilder(
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(
                                                                Operation.ADD,
                                                                change,
                                                                new NoiseMapBuilder(1.0)
                                                        ),
                                                        Operation.DIVIDE,
                                                        Operation.DIVIDE,
                                                        zoom,
                                                        zoom
                                                )
                                        ),
                                        ValueOperation.POWER_SYMMETRICAL,
                                        2.0
                                ),
                                ValueOperation.REMOVE_POURCENT,
                                0.4
                        )
                ).build(60);

        // generate a 2d array of noise
        double[][] noise = new double[1000][1000];
        for (int x = 0; x < noise.length; x++) {
            for (int y = 0; y < noise[x].length; y++) {
                noise[x][y] = complexNoise.getValue(x, y);
            }
        }

        // generate an image of the noise (gray scale)
        BufferedImage noiseImage = new BufferedImage(noise.length, noise[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < noise.length; x++) {
            for (int y = 0; y < noise[x].length; y++) {
                int color = (int) (noise[x][y] * 255);
                int blue = 0;
                int green = 0;
                int red = 0;
                if (color > 127) {
                    green = 255 - color;
                    if (color > 191) {
                        red = 255 - color;
                        if (color > 223) {
                            blue = color;
                            green = color;
                        }
                    }
                } else {
                    blue = color;
                }
                try {
                    noiseImage.setRGB(x, y, (new Color(red, green, blue)).getRGB());
                } catch (Exception e) {
                    System.out.println("x: " + x + " y: " + y + " color: " + color);
                }
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(noiseImage, "png", new java.io.File(title + ".png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
