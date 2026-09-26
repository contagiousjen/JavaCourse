/**
 * Точка входа в программу. Разбирает аргументы командной строки и запускает игру.
 */
public class Main {
    private Main() {}

    /**
     * @param args два целых числа — нижняя и верхняя границы диапазона.
     *             Если аргументы не переданы, используется диапазон 1–100.
     */
    void main(String[] args) {
        int minValue;
        int maxValue;
        if (args.length == 0) {
            minValue = 1;
            maxValue = 100;
        } else if (args.length == 2) {
            try {
                minValue = Integer.parseInt(args[0]);
                maxValue = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                View.errorIncorrectConsoleArgs();
                return;
            }
        } else {
            View.errorIncorrectConsoleArgs();
            return;
        }

        Model model = new Model(minValue, maxValue);
        Controller.dialogLoop(model);
    }
}
