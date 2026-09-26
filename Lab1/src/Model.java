public class Model {
    final int minValue;
    final int maxValue;
    private int attempts;
    private int lower;
    private int upper;

    int getAttempts() {
        return attempts;
    }

    Model(int minValueArg, int maxValueArg) {
        minValue = minValueArg;
        maxValue = maxValueArg;
        reset();
    }

    boolean update(int mid, boolean isLarger) {
        if (isLarger) {
            lower = mid + 1;
        } else {
            upper = mid;
        }

        return lower < upper;
    }

    int nextGuess() {
        attempts++;
        return (lower + upper) / 2;
    }

    void reset() {
        lower = minValue;
        upper = maxValue;
        attempts = 0;
    }
}
