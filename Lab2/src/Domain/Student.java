package Domain;

import Domain.Enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private final List<Grade> grades = new ArrayList<>();

    public Student(String firstName, String middleName, String lastName, Date birthDate, Sex sex) {
        super(firstName, middleName, lastName, birthDate, sex);
    }

    void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return Collections.unmodifiableList(grades);
    }
}
