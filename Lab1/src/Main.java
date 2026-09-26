public class Main
{
    void main(String[] args)
    {
        int minValue;
        int maxValue;
        if (args.length == 0)
        {
            minValue = 1;
            maxValue = 100;
        }
        else if (args.length == 2)
        {
            try
            {
                minValue = Integer.parseInt(args[0]);
                maxValue = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
                View.errorIncorrectConsoleArgs();
                return;
            }
        }
        else
        {
            View.errorIncorrectConsoleArgs();
            return;
        }

        Model model = new Model(minValue, maxValue);
        Controller.dialogLoop(model);
    }
}
