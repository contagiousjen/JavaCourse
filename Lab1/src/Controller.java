import java.util.Scanner;

public class Controller
{
    static Scanner sn = new Scanner(System.in);

    private static boolean getYesOrNoInput()
    {
        while (true)
        {
            String input = sn.nextLine();
            switch (input)
            {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    View.errorIncorrectYesOrNoInput();
            }
        }
    }

    static void dialogLoop(Model model)
    {
        View.sayHello(model.minValue, model.maxValue);
        boolean isGameOver = false;
        while (!isGameOver)
        {
            int guess = model.nextGuess();
            View.makeAGuess(guess);
            if (getYesOrNoInput())
            {
                View.winText(model.getAttempts());
                View.promptANewGame();
                if (getYesOrNoInput())
                {
                    model.reset();
                }
                else
                {
                    isGameOver = true;
                }
            }
            else
            {
                View.askForBound(guess);
                if (!model.update(guess, getYesOrNoInput()))
                {
                    View.errorNumberNotFound(model.minValue, model.maxValue);
                    View.promptANewGame();
                    if (getYesOrNoInput())
                    {
                        model.reset();
                    }
                    else
                    {
                        isGameOver = true;
                    }
                }
            }
        }

        View.sayGoodbye();
    }
}