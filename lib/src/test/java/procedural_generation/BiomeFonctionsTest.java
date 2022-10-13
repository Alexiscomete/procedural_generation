package procedural_generation;

import org.junit.jupiter.api.Test;
import procedural_generation.climat.ClimatMinMax;

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


}
