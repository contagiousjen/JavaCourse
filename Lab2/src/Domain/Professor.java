package Domain;

import Domain.Enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Professor extends Person {
    private static final int MAX_GRADES_PER_DISCIPLINE = 10;
    private static final int MAX_DISCIPLINES = 2;
    private static final long ONE_WEEK_MS = 7L * 24 * 60 * 60 * 1000;

    private final List<Discipline> disciplines = new ArrayList<>();
    private final Institute institute;

    public Professor(
            String firstName,
            String middleName,
            String lastName,
            Date birthDate,
            Sex sex,
            Institute institute) {
        super(firstName, middleName, lastName, birthDate, sex);
        this.institute = institute;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void addDiscipline(Discipline discipline) {
        if (disciplines.size() >= MAX_DISCIPLINES) {
            throw new IllegalStateException("Преподаватель может вести не более " + MAX_DISCIPLINES + " дисциплин");
        }
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
        Date lastGradeDate = null;
        for (Grade g : student.getGrades()) {
            if (g.getDiscipline() == discipline && g.getProfessor() == this) {
                gradesCount++;
                if (lastGradeDate == null || g.getDate().after(lastGradeDate)) {
                    lastGradeDate = g.getDate();
                }
            }
        }

        if (gradesCount >= MAX_GRADES_PER_DISCIPLINE) {
            return false;
        }

        if (lastGradeDate != null) {
            long diffMs = date.getTime() - lastGradeDate.getTime();
            if (diffMs < ONE_WEEK_MS) {
                return false;
            }
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
        return String.format(
                "Преподаватель: %s, %s, предметы: %s",
                super.toString(), institute.getName(), subjects);
    }
}
