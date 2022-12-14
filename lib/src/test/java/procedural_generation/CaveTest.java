package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.noise.ComplexNoise;
import procedural_generation.noise.ComplexNoiseBuilder;
import procedural_generation.noise.NoiseMapBuilder;
import procedural_generation.noise.nodes.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaveTest {
    ComplexNoiseBuilder complexNoiseBuilder = new ComplexNoiseBuilder(
            new ValueOperationNodeBuilder(
                    new ValueOperationNodeBuilder(
                            new AddNodeBuilder(
                                    new NoiseMapBuilder(2),
                                    new ChangeSeedNodeBuilder(
                                            Operation.ADD,
                                            1,
                                            new NoiseMapBuilder(2)
                                    ),
                                    new ChangeLocationNodeBuilder(
                                            new ChangeSeedNodeBuilder(
                                                    Operation.ADD,
                                                    5,
                                                    new NoiseMapBuilder(1)
                                            ),
                                            Operation.MULTIPLY,
                                            Operation.MULTIPLY,
                                            10,
                                            10
                                    ),
                                    new ChangeLocationNodeBuilder(
                                            new ChangeSeedNodeBuilder(
                                                    Operation.ADD,
                                                    6,
                                                    new NoiseMapBuilder(0.5)
                                            ),
                                            Operation.MULTIPLY,
                                            Operation.MULTIPLY,
                                            100,
                                            100
                                    )
                            ),
                            ValueOperation.ABS,
                            0.5
                    ),
                    ValueOperation.REMOVE_POURCENT,
                    0.4
            )
    );
    ComplexNoise complexNoise = complexNoiseBuilder.build(110);

    @Test
    void someLibraryMethodReturnsTrue() {
        // verify if > 0 and < 1
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 2000; j++) {
                double d = complexNoise.getValue(i, 0);
                assertTrue(d >= 0 && d <= 1);
            }
        }
    }

    @Test
    void verifHight() {
        assertTrue(existMax(0.1));
        assertTrue(existMax(0.2));
        assertTrue(existMax(0.3));
        assertTrue(existMax(0.4));
        assertTrue(existMax(0.5));
        assertTrue(existMax(0.6));
        assertTrue(existMax(0.7));
        assertTrue(existMax(0.8));
        assertTrue(existMax(0.9));
    }

    boolean existMax(double value) {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (complexNoise.getValue(i, j) >= value) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    void createNode() {

        // generate a 2d array of noise
        double[][] noise = new double[3000][2000];
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
                int blue = 255 - color;
                int green = 255 - color;
                int red = 255 - color;
                if (color > 137) {
                    green = 0;
                    red = 0;
                    blue = 0;
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
            javax.imageio.ImageIO.write(noiseImage, "png", new java.io.File("cave.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
