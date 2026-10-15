package Domain;

public class Discipline {
    private final String name;
    private final int credits;

    public Discipline(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }
}

