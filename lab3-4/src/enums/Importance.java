package enums;

public enum Importance {
    COMPELLING(1.0),
    CRITICAL(0.75),
    SIGNIFICANT(0.5),
    OPTIONAL(0.25),
    NEGLIGIBLE(-0.1);

    private final double importance;

    Importance(double importance) {
        this.importance = importance;
    }

    public double getImportance() {
        return this.importance;
    }
}