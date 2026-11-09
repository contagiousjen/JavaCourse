package Model;

import Model.Enums.ParentStatus;
import Model.Enums.Sex;

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
        double avg = childrenSessionAverage();

        if (avg >= 4.6 && avg <= 5.0) {
            status = ParentStatus.Happy;
            premium = 10000;
        } else if (avg >= 4.0 && avg <= 4.5) {
            status = ParentStatus.Pleased;
            premium = 5000;
        } else if (avg >= 3.0 && avg <= 3.9) {
            status = ParentStatus.Angry;
            premium = 0;
        } else if (avg > 4.5 && avg < 4.6) {
            status = ParentStatus.Happy;
            premium = 10000;
        } else {
            status = ParentStatus.Angry;
            premium = 0;
        }
    }

    private double childrenSessionAverage() {
        if (students == null || students.length == 0) {
            return 0.0;
        }
        double sum = 0;
        for (Student s : students) {
            sum += s.getSessionAverage();
        }
        return sum / students.length;
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
        String children = names.isEmpty() ? "—" : names.toString();
        return String.format(
                "Родитель: %s, дети: %s, средний балл за сессию: %.2f, выплачено премиальных: %d руб.",
                super.toString(), children, childrenSessionAverage(), premium);
    }
}
