import Model.University;

public class Main {
    private Main() {}

    public static void main(String[] args) {
        University university = new University();
        View view = new View();
        Controller controller = new Controller(university, view);
        controller.run();
    }
}
