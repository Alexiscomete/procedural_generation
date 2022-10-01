package procedural_generation.noise.nodes;

public enum ValueOperation {
    POWER {
        @Override
        public double apply(double a, double b) {
            return Math.pow(a, b);
        }
    },
    POWER_START_NEGATIVE {
        @Override
        public double apply(double a, double b) {
            return (Math.pow(a * 2 - 1, b) + 1) / 2;
        }
    };

    public abstract double apply(double a, double b);
}
