package fraction;

public enum Denominator {
    ONE(1), TWO(2), THREE(3), FOUR(4);
    int value;
    public Denominator(int value) {
        this.value = value;
    }
}