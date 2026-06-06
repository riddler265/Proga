package enums;

public enum Hope {
    TOTAL(1.0),
    MEDIUM(0.5),
    NONEEXIST(0.0);

    private final double hope;

    Hope(double hope) {
        this.hope = hope;
    }

    public double getHope() {
        return this.hope;
    }
}