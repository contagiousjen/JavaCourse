public class View
{
    private static void print(String output, Object... args)
    {
        System.out.printf(output, args);
        System.out.println();
    }

    private static void printYesOrNoQuestion(String output, Object... args)
    {
        print(String.format(output, args) + " (y/n)");
    }

    static void sayHello(int minValue, int maxValue)
    {
        print("Здравствуйте, давайте поиграем? Загадайте число от %d до %d.", minValue, maxValue);
    }

    static void makeAGuess(int guess)
    {
        printYesOrNoQuestion("Вы загадали число %d?", guess);
    }

    static void askForBound(int guess)
    {
        printYesOrNoQuestion("Загаданное число больше %d?", guess);
    }

    static void winText(int attempts)
    {
        print("Ура! Я угадал ваше число. Количество попыток - %d", attempts);
    }

    static void promptANewGame()
    {
        printYesOrNoQuestion("Хотите загадать новое число?");
    }

    static void sayGoodbye()
    {
        print("Спасибо за игру, до свидания!");
    }

    static void errorIncorrectConsoleArgs()
    {
        print("Ошибка: некорректные аргументы командной строки. Пожалуйста, введите два целых числа.");
    }

    static void errorIncorrectYesOrNoInput()
    {
        print("Некорректный ввод. Пожалуйста, введите 'y' (если 'да') или 'n' (если 'нет').");
    }

    static void errorNumberNotFound(int minValue, int maxValue)
    {
        print("Что-то я запутался - кажется, загаданное вами число находится вне диапазона от %d до %d.", minValue, maxValue);
    }
}