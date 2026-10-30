import Domain.Discipline;
import Domain.Enums.ParentStatus;
import Domain.Grade;
import Domain.Institute;
import Domain.Parent;
import Domain.Professor;
import Domain.Student;
import Service.PerformanceReportService;
import Service.UniversityDataFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    private Main() {}

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        PerformanceReportService reportService = new PerformanceReportService();

        UniversityDataFactory factory = new UniversityDataFactory();
        UniversityDataFactory.UniversityData data = factory.create();

        System.out.printf(
                "Создано: студентов %d, преподавателей %d, дисциплин %d, институтов %d%n%n",
                data.students.size(),
                data.professors.size(),
                data.disciplines.size(),
                data.institutes.size());

        printGroupDistribution(data.students);
        printDisciplineDistribution(data.disciplines);

        System.out.println("=== Примеры отчётов по успеваемости ===\n");

        Discipline sampleDiscipline = data.disciplines.get(0);
        Institute sampleInstitute = data.institutes.get(0);
        Professor sampleProfessor = data.professors.get(0);

        printReport(reportService, data.students, null, null, null, null);
        printReport(reportService, data.students, 3, null, null, null);
        printReport(reportService, data.students, null, sampleDiscipline, null, null);
        printReport(reportService, data.students, null, null, sampleInstitute, null);
        printReport(reportService, data.students, null, null, null, sampleProfessor);

        Student sixthCourse = findStudentOnCourse(data.students, 6);
        if (sixthCourse != null) {
            System.out.println("=== История успеваемости (студент 6-го курса) ===\n");
            System.out.printf("%s (%s), текущий курс %d%n%n",
                    sixthCourse.getFullName(),
                    sixthCourse.getStudyGroup(),
                    sixthCourse.getCourseYear());
            for (int year = 1; year <= sixthCourse.getCourseYear(); year++) {
                System.out.printf(
                        "  %d-й курс: средний балл %.2f (%d оценок)%n",
                        year,
                        sixthCourse.getAverageGradeForCourse(year),
                        countGradesForCourse(sixthCourse, year));
            }
            System.out.println();
        }

        for (Parent parent : data.parents) {
            parent.updateStatusAndPremium();
        }

        System.out.println("=== Статусы родителей (первые 5) ===\n");
        for (int i = 0; i < Math.min(5, data.students.size()); i++) {
            Student student = data.students.get(i);
            Parent parent = student.getParent();
            System.out.printf(
                    "Студент: %-25s %s | ср. %.2f | родитель: %s | %s | премия: %d руб.%n",
                    student.getFullName(),
                    student.getStudyGroup(),
                    student.getAverageGrade(),
                    parent.getFullName(),
                    statusLabel(parent.getStatus()),
                    parent.getPremium());
        }
        System.out.println();

        System.out.println("=== Демонстрация ограничений ===\n");
        Professor prof0 = data.professors.get(0);
        Discipline disc0 = prof0.getDisciplines().get(0);
        Student student0 = data.students.get(0);

        Date tooSoon = date(2024, Calendar.SEPTEMBER + 1, 2);
        boolean result = prof0.gradeStudent(student0, disc0, 5, tooSoon);
        System.out.printf("Повторная оценка в ту же неделю: %s%n",
                result ? "УСПЕХ" : "ОТКЛОНЕНО (ожидаемо)");

        Calendar cal11 = Calendar.getInstance();
        cal11.set(2024, Calendar.SEPTEMBER, 2);
        cal11.add(Calendar.DAY_OF_MONTH, 77);
        result = prof0.gradeStudent(student0, disc0, 5, cal11.getTime());
        System.out.printf("11-я оценка по дисциплине: %s%n",
                result ? "УСПЕХ" : "ОТКЛОНЕНО (ожидаемо)");

        Discipline foreign = data.disciplines.stream()
                .filter(d -> !prof0.getDisciplines().contains(d))
                .findFirst()
                .orElse(disc0);
        result = prof0.gradeStudent(student0, foreign, 5, cal11.getTime());
        System.out.printf("Оценка по чужой дисциплине: %s%n",
                result ? "УСПЕХ" : "ОТКЛОНЕНО (ожидаемо)");

        System.out.printf("%nПример оценки: %s | %s | %s%n",
                student0.getFullName(),
                disc0.getName(),
                sdf.format(student0.getGrades().get(0).getDate()));
    }

    private static void printGroupDistribution(List<Student> students) {
        System.out.println("=== Студенты по курсам и группам ===");
        for (int course = 1; course <= UniversityDataFactory.COURSES; course++) {
            for (int group = 1; group <= UniversityDataFactory.GROUPS_PER_COURSE; group++) {
                final int c = course;
                final int g = group;
                long count = students.stream()
                        .filter(s -> s.getStudyGroup().getCourseYear() == c
                                && s.getStudyGroup().getGroupNumber() == g)
                        .count();
                System.out.printf("  %d-й курс, группа %d: %d студентов%n", course, group, count);
            }
        }
        System.out.println();
    }

    private static void printDisciplineDistribution(List<Discipline> disciplines) {
        System.out.println("=== Дисциплины по курсам (по 5 на курс, всего 30) ===");
        for (int course = 1; course <= UniversityDataFactory.COURSES; course++) {
            final int c = course;
            long count = disciplines.stream().filter(d -> d.getCourseYear() == c).count();
            System.out.printf("  %d-й курс: %d дисциплин%n", course, count);
        }
        System.out.println();
    }

    private static void printReport(
            PerformanceReportService service,
            List<Student> students,
            Integer courseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {
        List<PerformanceReportService.ReportRow> rows =
                service.buildReport(students, courseYear, discipline, institute, professor);
        System.out.println(service.formatReport(rows, courseYear, discipline, institute, professor));
    }

    private static Student findStudentOnCourse(List<Student> students, int course) {
        for (Student s : students) {
            if (s.getCourseYear() == course) {
                return s;
            }
        }
        return null;
    }

    private static int countGradesForCourse(Student student, int courseYear) {
        int count = 0;
        for (Grade g : student.getGrades()) {
            if (g.getDiscipline().getCourseYear() == courseYear) {
                count++;
            }
        }
        return count;
    }

    private static String statusLabel(ParentStatus status) {
        if (status == null) return "—";
        switch (status) {
            case Angry:   return "хмурый";
            case Pleased: return "удовлетворённый";
            case Happy:   return "радостный";
            default:      return status.name();
        }
    }

    private static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
