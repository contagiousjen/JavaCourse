package Domain;

import Domain.Enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private final List<Grade> grades = new ArrayList<>();
    private Parent parent;

    public Student(String firstName, String middleName, String lastName, Date birthDate, Sex sex) {
        super(firstName, middleName, lastName, birthDate, sex);
    }

    void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return Collections.unmodifiableList(grades);
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
}
