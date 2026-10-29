package Domain;

import Domain.Enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Professor extends Person {
    private static final int MAX_GRADES_PER_DISCIPLINE = 10;
    private static final long ONE_WEEK_MS = 7L * 24 * 60 * 60 * 1000;

    private final List<Discipline> disciplines = new ArrayList<>();

    public Professor(String firstName, String middleName, String lastName, Date birthDate, Sex sex) {
        super(firstName, middleName, lastName, birthDate, sex);
    }

    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
    }

    public List<Discipline> getDisciplines() {
        return Collections.unmodifiableList(disciplines);
    }

    public boolean gradeStudent(Student student, Discipline discipline, int value, Date date) {
        if (!disciplines.contains(discipline)) {
            return false;
        }

        int gradesCount = 0;
        for (Grade g : student.getGrades()) {
            if (g.getDiscipline() == discipline && g.getProfessor() == this) {
                gradesCount++;
                long diffMs = Math.abs(date.getTime() - g.getDate().getTime());
                if (diffMs < ONE_WEEK_MS) {
                    return false;
                }
            }
        }

        if (gradesCount >= MAX_GRADES_PER_DISCIPLINE) {
            return false;
        }

        student.addGrade(new Grade(value, date, student, discipline, this));
        return true;
    }

    @Override
    public String toString() {
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < disciplines.size(); i++) {
            if (i > 0) names.append(", ");
            names.append(disciplines.get(i).getName());
        }
        String subjects = disciplines.isEmpty() ? "—" : names.toString();
        return String.format("Преподаватель: %s, предмет: %s", super.toString(), subjects);
    }
}
