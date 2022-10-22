package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.biomes.SimpleBiome;
import procedural_generation.climat.ClimatMinMax;
import procedural_generation.climat.ClimatRule;
import procedural_generation.climat.ClimatRuleNoise;
import procedural_generation.climat.ClimatRuleType;
import procedural_generation.noise.ComplexNoise;
import procedural_generation.noise.NoiseMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class BiomeFonctionsTest {

    @Test
    public void testMinMaxClimat() {
        ClimatMinMax climatMinMax = new ClimatMinMax(0.2, 0.8, 0.1);
        assert climatMinMax.isInRange(0.2);
        assert climatMinMax.isInRange(0.8);
        assert climatMinMax.isInRange(0.5);
        assert !climatMinMax.isInRange(0.1);
        assert !climatMinMax.isInRange(0.9);
        assert climatMinMax.distanceRange(0.1) == 1;
        assert climatMinMax.distanceRange(0.9) == 1;
        assert climatMinMax.distanceRange(0.2) == 0;
        assert climatMinMax.distanceRange(0.8) == 0;
        assert climatMinMax.distanceRange(0.3) == 0;
        assert climatMinMax.distanceRange(0.7) == 0;
        assert climatMinMax.distanceRange(0.15) == 0.5;
        assert climatMinMax.distanceRange(0.85) == 0.5;
    }

    @Test
    public void singleBiome() {
        ArrayList<ClimatRule> climatRules = new ArrayList<>();
        ComplexNoise complexNoise = new ComplexNoise(new NoiseMap(2, 1));
        climatRules.add(new ClimatRuleNoise(complexNoise, "plaine", ClimatRuleType.OTHER));
        HashMap<ClimatRule, ClimatMinMax> climatMinMaxHashMap = new HashMap<>();
        for (ClimatRule climatRule : climatRules) {
            climatMinMaxHashMap.put(climatRule, new ClimatMinMax(0.7, 0.8, 0.01));
        }
        SimpleBiome simpleBiome = new SimpleBiome(0.6, "plaine", climatMinMaxHashMap, 1);

        // generate a 2d array of noise
        double[][] noise = new double[6000][2000];
        for (int x = 0; x < noise.length; x++) {
            for (int y = 0; y < noise[x].length; y++) {
                HashMap<ClimatRule, Double> climatPourcent = new HashMap<>();
                for (ClimatRule climatRule : climatRules) {
                    climatPourcent.put(climatRule, climatRule.getRuleValue(x, y, 0.0));
                }
                noise[x][y] = simpleBiome.pourcent(x, y, 0.0, climatPourcent, complexNoise);
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
            javax.imageio.ImageIO.write(noiseImage, "png", new java.io.File("biome.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
