/**
 * Отвечает за вывод всех сообщений пользователю.
 */
public class View {
    private View() {}

    private static void print(String output, Object... args) {
        System.out.printf(output, args);
        System.out.println();
    }

    private static void printYesOrNoQuestion(String output, Object... args) {
        print(String.format(output, args) + " (y/n)");
    }

    /**
     * Выводит приветствие и диапазон чисел.
     *
     * @param minValue нижняя граница диапазона
     * @param maxValue верхняя граница диапазона
     */
    static void sayHello(int minValue, int maxValue) {
        print("Здравствуйте, давайте поиграем? Загадайте число от %d до %d.", minValue, maxValue);
    }

    /**
     * Спрашивает, является ли guess загаданным числом.
     *
     * @param guess предполагаемое число
     */
    static void makeAGuess(int guess) {
        printYesOrNoQuestion("Вы загадали число %d?", guess);
    }

    /**
     * Спрашивает, больше ли загаданное число, чем guess.
     *
     * @param guess текущее предположение
     */
    static void askForBound(int guess) {
        printYesOrNoQuestion("Загаданное число больше %d?", guess);
    }

    /**
     * Сообщает о победе и выводит количество попыток.
     *
     * @param attempts количество потраченных попыток
     */
    static void winText(int attempts) {
        print("Ура! Я угадал ваше число. Количество попыток - %d", attempts);
    }

    /**
     * Предлагает сыграть ещё раз.
     */
    static void promptANewGame() {
        printYesOrNoQuestion("Хотите загадать новое число?");
    }

    /**
     * Выводит прощальное сообщение.
     */
    static void sayGoodbye() {
        print("Спасибо за игру, до свидания!");
    }

    /**
     * Сообщает об ошибке в аргументах командной строки.
     */
    static void errorIncorrectConsoleArgs() {
        print("Ошибка: некорректные аргументы командной строки. Пожалуйста, введите два целых числа.");
    }

    /**
     * Сообщает о некорректном вводе (не y/n).
     */
    static void errorIncorrectYesOrNoInput() {
        print("Некорректный ввод. Пожалуйста, введите 'y' (если 'да') или 'n' (если 'нет').");
    }

    /**
     * Сообщает, что загаданное число вышло за пределы диапазона.
     *
     * @param minValue нижняя граница диапазона
     * @param maxValue верхняя граница диапазона
     */
    static void errorNumberNotFound(int minValue, int maxValue) {
        print("Что-то я запутался - кажется, загаданное вами число находится вне диапазона от %d до %d.", minValue, maxValue);
    }
}
