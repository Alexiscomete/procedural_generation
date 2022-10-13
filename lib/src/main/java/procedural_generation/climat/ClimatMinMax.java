package procedural_generation.climat;

public class ClimatMinMax {
    private final double min;
    private final double max;
    private final double acceptDistance;

    public ClimatMinMax(double min, double max, double acceptDistance) {
        this.min = min * 255;
        this.max = max * 255;
        this.acceptDistance = acceptDistance * 255;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getRange() {
        return max - min;
    }

    public boolean isInRange(double value) {
        value *= 255;
        return value >= min && value <= max;
    }

    public double distanceRange(double value) {
        value *= 255;
        if (value < min) {
            double distance = min - value;
            if (distance > acceptDistance) {
                return 1;
            }
            return distance / acceptDistance;
        } else if (value > max) {
            double distance = value - max;
            if (distance > acceptDistance) {
                return 1;
            }
            return distance / acceptDistance;
        } else {
            return 0;
        }
    }
}
