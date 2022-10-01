package procedural_generation.noise.nodes;

public enum Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE;

    public double apply(double a, double b) {
        return switch (this) {
            case ADD -> a + b;
            case SUBTRACT -> a - b;
            case MULTIPLY -> a * b;
            case DIVIDE -> a / b;
        };
    }

    public long apply(long a, long b) {
        return switch (this) {
            case ADD -> a + b;
            case SUBTRACT -> a - b;
            case MULTIPLY -> a * b;
            case DIVIDE -> a / b;
        };
    }
}
