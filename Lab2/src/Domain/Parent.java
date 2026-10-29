package Domain;

import Domain.Enums.ParentStatus;
import Domain.Enums.Sex;

import java.util.Date;

public class Parent extends Person
{
    private final Student[] students;
    private ParentStatus status;
    private int premium;

    public Parent(
            String firstName,
            String middleName,
            String lastName,
            Date birthDate,
            Sex sex,
            Student[] students) {
        super(firstName, middleName, lastName, birthDate, sex);
        this.students = students;
    }

    public ParentStatus getStatus() {
        return status;
    }

    public int getPremium() {
        return premium;
    }

    public void updateStatusAndPremium() {
        if (students == null || students.length == 0) {
            status = null;
            premium = 0;
            return;
        }
        double avg = 0;
        for (Student s : students) avg += s.getAverageGrade();
        avg /= students.length;

        if (avg >= 3.0 && avg < 4.0) {
            status = ParentStatus.Angry;
            premium = 0;
        } else if (avg >= 4.0 && avg <= 4.5) {
            status = ParentStatus.Pleased;
            premium = 5000;
        } else if (avg > 4.5 && avg <= 5.0) {
            status = ParentStatus.Happy;
            premium = 10000;
        } else {
            status = null;
            premium = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder names = new StringBuilder();
        if (students != null) {
            for (int i = 0; i < students.length; i++) {
                if (i > 0) names.append(", ");
                names.append(students[i].getFullName());
            }
        }
        String children = names.length() == 0 ? "—" : names.toString();
        return String.format("Родитель: %s, дети: %s, выплачено премиальных: %d руб.",
                super.toString(), children, premium);
    }
}
