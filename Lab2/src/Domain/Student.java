package Domain;

import Domain.Enums.PerformanceCategory;
import Domain.Enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private final List<Grade> grades = new ArrayList<>();
    private Parent parent;
    private final int courseYear;
    private final StudyGroup studyGroup;
    private PerformanceCategory performanceTier;

    public Student(
            String firstName,
            String middleName,
            String lastName,
            Date birthDate,
            Sex sex,
            int courseYear,
            StudyGroup studyGroup) {
        super(firstName, middleName, lastName, birthDate, sex);
        this.courseYear = courseYear;
        this.studyGroup = studyGroup;
    }

    void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return Collections.unmodifiableList(grades);
    }

    public int getCourseYear() {
        return courseYear;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setPerformanceTier(PerformanceCategory performanceTier) {
        this.performanceTier = performanceTier;
    }

    public PerformanceCategory getPerformanceTier() {
        return performanceTier;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0;
        for (Grade g : grades) sum += g.getValue();
        return sum / grades.size();
    }

    public double getAverageGradeForCourse(int disciplineCourseYear) {
        double sum = 0;
        int count = 0;
        for (Grade g : grades) {
            if (g.getDiscipline().getCourseYear() == disciplineCourseYear) {
                sum += g.getValue();
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public int getPremium() {
        return parent == null ? 0 : parent.getPremium();
    }

    @Override
    public String toString() {
        return String.format(
                "Студент: %s, %s, средний балл: %.2f, премия: %d руб.",
                super.toString(), studyGroup, getAverageGrade(), getPremium());
    }
}
