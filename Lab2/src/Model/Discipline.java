package Model;

public class Discipline {
    private final String name;
    private final int credits;
    private final int courseYear;
    private final Professor professor;

    public Discipline(String name, int credits, int courseYear, Professor professor) {
        this.name = name;
        this.credits = credits;
        this.courseYear = courseYear;
        this.professor = professor;
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }
    public int getCourseYear() { return courseYear; }
    public Professor getProfessor() { return professor; }

    @Override
    public String toString() {
        return String.format(
                "Предмет: %s, %d-й курс, %d з.е., преподаватель: %s",
                name, courseYear, credits, professor.getFullName());
    }
}
