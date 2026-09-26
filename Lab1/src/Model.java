/**
 * Хранит состояние игры и реализует алгоритм бинарного поиска.
 */
public class Model {
    /** Минимальное значение диапазона. */
    final int minValue;
    /** Максимальное значение диапазона. */
    final int maxValue;
    private int attempts;
    private int lower;
    private int upper;

    /**
     * @return количество сделанных попыток
     */
    int getAttempts() {
        return attempts;
    }

    /**
     * @param minValueArg нижняя граница диапазона
     * @param maxValueArg верхняя граница диапазона
     */
    Model(int minValueArg, int maxValueArg) {
        minValue = minValueArg;
        maxValue = maxValueArg;
        reset();
    }

    /**
     * Сужает диапазон поиска на основе ответа пользователя.
     *
     * @param mid      текущее предположение
     * @param isLarger true, если загаданное число больше mid
     * @return true, если диапазон ещё не исчерпан
     */
    boolean update(int mid, boolean isLarger) {
        if (isLarger) {
            lower = mid + 1;
        } else {
            upper = mid;
        }

        return lower < upper;
    }

    /**
     * Увеличивает счётчик попыток и возвращает середину текущего диапазона.
     *
     * @return следующее предполагаемое число
     */
    int nextGuess() {
        attempts++;
        return (lower + upper) / 2;
    }

    /**
     * Сбрасывает состояние игры к начальному.
     */
    void reset() {
        lower = minValue;
        upper = maxValue;
        attempts = 0;
    }
}
