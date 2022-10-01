/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.noise.*;
import procedural_generation.noise.nodes.AddNodeBuilder;
import procedural_generation.noise.nodes.ChangeLocationNodeBuilder;
import procedural_generation.noise.nodes.ChangeSeedNodeBuilder;
import procedural_generation.noise.nodes.Operation;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    void someLibraryMethodReturnsTrue() {
        NoiseMap n = new NoiseMap(0, 5);
        // verify if > 0 and < 1
        for (int i = 0; i < 10000; i++) {
            double d = n.getValue(i, 0);
            assertTrue(d >= 0 && d <= 1);
        }
    }

    @Test
    void createNode() {
        ComplexNoiseBuilder complexNoiseBuilder = new ComplexNoiseBuilder(
                new AddNodeBuilder(
                        new ChangeSeedNodeBuilder(
                                Operation.ADD,
                                1,
                                new NoiseMapBuilder(1)
                        ),
                        new NoiseMapBuilder(1),
                        new ChangeLocationNodeBuilder(
                                new ChangeSeedNodeBuilder(
                                        Operation.ADD,
                                        1,
                                        new NoiseMapBuilder(5)
                                ),
                                Operation.DIVIDE,
                                Operation.DIVIDE,
                                10,
                                10
                        ),
                        new ChangeLocationNodeBuilder(
                                new ChangeSeedNodeBuilder(
                                        Operation.ADD,
                                        1,
                                        new NoiseMapBuilder(1)
                                ),
                                Operation.DIVIDE,
                                Operation.DIVIDE,
                                100,
                                100
                        )
                )
        );
        ComplexNoise complexNoise = complexNoiseBuilder.build(10);

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
                System.out.println(noise[x][y]);
                int color = (int) (noise[x][y] * 255);
                noiseImage.setRGB(x, y, (new Color(color, color, 255 - color)).getRGB());
            }
        }

        // save the image
        try {
            javax.imageio.ImageIO.write(noiseImage, "png", new java.io.File("noise.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
