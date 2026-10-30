package Domain;

public class StudyGroup {
    private final int courseYear;
    private final int groupNumber;

    public StudyGroup(int courseYear, int groupNumber) {
        if (courseYear < 1 || courseYear > 6) {
            throw new IllegalArgumentException("Курс должен быть от 1 до 6");
        }
        if (groupNumber < 1 || groupNumber > 3) {
            throw new IllegalArgumentException("Номер группы должен быть от 1 до 3");
        }
        this.courseYear = courseYear;
        this.groupNumber = groupNumber;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public String getCode() {
        return courseYear + "-" + groupNumber;
    }

    @Override
    public String toString() {
        return String.format("%d-й курс, группа %d", courseYear, groupNumber);
    }
}
