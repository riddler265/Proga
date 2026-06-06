package nothing;

public class Nothing {
    private static Nothing nothing;

    private Nothing() {}

    public static Nothing getNothing() {
        if (nothing == null) {
            nothing = new Nothing();
        }
        return nothing;
    }
}