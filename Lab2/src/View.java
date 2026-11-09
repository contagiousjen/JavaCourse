import Model.Discipline;
import Model.Enums.ParentStatus;
import Model.Grade;
import Model.Institute;
import Model.Professor;
import Model.ReportRow;
import Model.Student;
import Model.University;

import java.text.SimpleDateFormat;
import java.util.List;

public class View {
    private static final int REPORT_ROW_LIMIT = 25;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void showCreatedCounts(int students, int professors, int disciplines, int institutes) {
        System.out.printf(
                "Создано: студентов %d, преподавателей %d, дисциплин %d, институтов %d%n%n",
                students, professors, disciplines, institutes);
    }

    public void showGroupSizes(List<University.GroupSize> sizes) {
        System.out.println("=== Студенты по курсам и группам ===");
        for (University.GroupSize size : sizes) {
            System.out.printf(
                    "  %d-й курс, группа %d: %d студентов%n",
                    size.courseYear, size.groupNumber, size.studentCount);
        }
        System.out.println();
    }

    public void showDisciplineCounts(List<University.CourseSize> sizes) {
        System.out.println("=== Дисциплины по курсам (по 5 на курс, всего 30) ===");
        for (University.CourseSize size : sizes) {
            System.out.printf("  %d-й курс: %d дисциплин%n", size.courseYear, size.count);
        }
        System.out.println();
    }

    public void showReportsHeader() {
        System.out.println("=== Примеры отчётов по успеваемости ===\n");
    }

    public void showReport(
            List<ReportRow> rows,
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {
        System.out.println("=== Отчёт по успеваемости ===");
        System.out.printf(
                "Фильтры: курс дисциплины=%s, дисциплина=%s, институт=%s, преподаватель=%s%n",
                disciplineCourseYear == null ? "все" : disciplineCourseYear,
                discipline == null ? "все" : discipline.getName(),
                institute == null ? "все" : institute.getName(),
                professor == null ? "все" : professor.getFullName());
        System.out.printf("Записей: %d%n%n", rows.size());

        long excellent = 0;
        long good = 0;
        long satisfactory = 0;
        long failing = 0;
        for (ReportRow row : rows) {
            switch (row.getCategory()) {
                case EXCELLENT:
                    excellent++;
                    break;
                case GOOD:
                    good++;
                    break;
                case SATISFACTORY:
                    satisfactory++;
                    break;
                case FAILING:
                    failing++;
                    break;
                default:
                    break;
            }
        }
        System.out.printf(
                "Сводка: отличников %d, хорошистов %d, троечников %d, не успевающих %d%n%n",
                excellent, good, satisfactory, failing);

        int limit = Math.min(rows.size(), REPORT_ROW_LIMIT);
        for (int i = 0; i < limit; i++) {
            ReportRow row = rows.get(i);
            System.out.printf(
                    "%-35s | %s | ср. %.2f | %s | оценок: %d%n",
                    row.getStudent().getFullName(),
                    row.getStudyGroup(),
                    row.getAverage(),
                    row.getCategory().getLabel(),
                    row.getGradeCount());
        }
        if (rows.size() > limit) {
            System.out.printf("... ещё %d записей%n", rows.size() - limit);
        }
        System.out.println();
    }

    public void showStudentHistory(Student student, List<University.CoursePerformance> history) {
        System.out.println("=== История успеваемости (студент 6-го курса) ===\n");
        System.out.printf("%s (%s), текущий курс %d%n%n",
                student.getFullName(), student.getStudyGroup(), student.getCourseYear());
        for (University.CoursePerformance year : history) {
            System.out.printf(
                    "  %d-й курс: средний балл %.2f (%d оценок)%n",
                    year.courseYear, year.average, year.gradeCount);
        }
        System.out.println();
    }

    public void showParentStatuses(List<Student> students) {
        System.out.println("=== Статусы родителей ===\n");
        for (Student student : students) {
            System.out.printf(
                    "Студент: %-25s %s | ср. %.2f | родитель: %s | %s | премия: %d руб.%n",
                    student.getFullName(),
                    student.getStudyGroup(),
                    student.getSessionAverage(),
                    student.getParent().getFullName(),
                    statusLabel(student.getParent().getStatus()),
                    student.getParent().getPremium());
        }
        System.out.println();
    }

    public void showDescriptions(
            List<Discipline> disciplines,
            List<Professor> professors,
            List<Student> students) {
        System.out.println("=== Описания (toString) ===\n");
        System.out.println("-- Предметы --");
        int disciplineSample = Math.min(disciplines.size(), 3);
        for (int i = 0; i < disciplineSample; i++) {
            System.out.println(disciplines.get(i));
        }
        System.out.println("\n-- Преподаватели --");
        for (Professor professor : professors) {
            System.out.println(professor);
        }
        System.out.println("\n-- Студенты --");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("\n-- Родители --");
        for (Student student : students) {
            System.out.println(student.getParent());
        }
        System.out.println();
    }

    public void showConstraintsHeader() {
        System.out.println("=== Демонстрация ограничений ===\n");
    }

    public void showGradeCheck(String description, boolean accepted) {
        System.out.printf("%s: %s%n", description, accepted ? "УСПЕХ" : "ОТКЛОНЕНО (ожидаемо)");
    }

    public void showSampleGrade(Grade grade) {
        System.out.printf("%nПример оценки: %s | %s | %d | %s%n",
                grade.getStudent().getFullName(),
                grade.getDiscipline().getName(),
                grade.getValue(),
                dateFormat.format(grade.getDate()));
    }

    private static String statusLabel(ParentStatus status) {
        if (status == null) {
            return "—";
        }
        switch (status) {
            case Angry:
                return "хмурый";
            case Pleased:
                return "удовлетворённый";
            case Happy:
                return "радостный";
            default:
                return status.name();
        }
    }
}
