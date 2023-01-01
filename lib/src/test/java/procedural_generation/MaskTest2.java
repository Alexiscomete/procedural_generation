package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.noise.ComplexNoise;
import procedural_generation.noise.ComplexNoiseBuilder;
import procedural_generation.noise.NoiseMapBuilder;
import procedural_generation.noise.nodes.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MaskTest2 {
    @Test
    void execute() {
        genImage("mask2");
    }

    public BufferedImage getImage() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("DIBIMAP.png")));
            if (image == null) {
                throw new IOException("Image not found");
            }
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void genImage(String title) {
        ComplexNoise complexNoise = new ComplexNoiseBuilder(
                new AddNodeBuilder(
                        new ValueOperationNodeBuilder(
                                new ValueOperationNodeBuilder(
                                        new AddNodeBuilder(
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(Operation.ADD, 4,
                                                                new NoiseMapBuilder(4.0)), Operation.DIVIDE, Operation.DIVIDE, 1.5, 1.5),
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(Operation.ADD, 3,
                                                                new NoiseMapBuilder(4.0)), Operation.DIVIDE, Operation.DIVIDE, 1.3, 1.3),
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(Operation.ADD, 2,
                                                                new NoiseMapBuilder(1.0)), Operation.DIVIDE, Operation.DIVIDE, 1.3, 1.3),
                                                new NoiseMapBuilder(0.5),
                                                new ChangeSeedNodeBuilder(Operation.ADD, 1,
                                                        new NoiseMapBuilder(0.5)),
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(Operation.ADD, 5,
                                                                new NoiseMapBuilder(0.3)), Operation.MULTIPLY, Operation.MULTIPLY, 10.0, 10.0),
                                                new ChangeLocationNodeBuilder(
                                                        new ChangeSeedNodeBuilder(Operation.ADD, 6,
                                                                new NoiseMapBuilder(0.3)), Operation.MULTIPLY, Operation.MULTIPLY, 100.0, 100.0),
                                                new ChangeLocationNodeBuilder(new ChangeSeedNodeBuilder(Operation.ADD, 7,
                                                        new NoiseMapBuilder(0.2)), Operation.MULTIPLY, Operation.MULTIPLY, 1000.0, 1000.0)), ValueOperation.POWER_SYMMETRICAL, 2.0), ValueOperation.REMOVE_POURCENT, 0.35),
                        new ImageMaskNodeBuilder(getImage(), 3.0,
                                new Color(0x704A40),
                                new Color(0x4D759D), 6)
                ))
                .build(60);


        // generate a 2d array of noise
        double[][] noise = new double[550][300];
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
            ImageIO.write(noiseImage, "png", new java.io.File(title + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
