package Service;

import Domain.Discipline;
import Domain.Enums.PerformanceCategory;
import Domain.Grade;
import Domain.Institute;
import Domain.Professor;
import Domain.Student;
import Domain.StudyGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PerformanceReportService {

    public static final class ReportRow {
        private final Student student;
        private final StudyGroup studyGroup;
        private final double average;
        private final PerformanceCategory category;
        private final int gradeCount;

        ReportRow(Student student, double average, int gradeCount) {
            this.student = student;
            this.studyGroup = student.getStudyGroup();
            this.average = average;
            this.category = PerformanceCategory.fromAverage(average);
            this.gradeCount = gradeCount;
        }

        public Student getStudent() {
            return student;
        }

        public StudyGroup getStudyGroup() {
            return studyGroup;
        }

        public double getAverage() {
            return average;
        }

        public PerformanceCategory getCategory() {
            return category;
        }

        public int getGradeCount() {
            return gradeCount;
        }
    }

    public List<ReportRow> buildReport(
            List<Student> students,
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {

        List<ReportRow> rows = new ArrayList<>();
        for (Student student : students) {
            List<Grade> matched = filterGrades(student, disciplineCourseYear, discipline, institute, professor);
            if (matched.isEmpty()) {
                continue;
            }
            double sum = 0;
            for (Grade g : matched) {
                sum += g.getValue();
            }
            rows.add(new ReportRow(student, sum / matched.size(), matched.size()));
        }
        rows.sort(Comparator
                .comparing((ReportRow r) -> r.getStudyGroup().getCourseYear())
                .thenComparing(r -> r.getStudyGroup().getGroupNumber())
                .thenComparing(r -> r.getStudent().getLastName()));
        return rows;
    }

    public String formatReport(
            List<ReportRow> rows,
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {

        StringBuilder sb = new StringBuilder();
        sb.append("=== Отчёт по успеваемости ===\n");
        sb.append("Фильтры: ");
        sb.append("курс дисциплины=").append(disciplineCourseYear == null ? "все" : disciplineCourseYear);
        sb.append(", дисциплина=").append(discipline == null ? "все" : discipline.getName());
        sb.append(", институт=").append(institute == null ? "все" : institute.getName());
        sb.append(", преподаватель=").append(professor == null ? "все" : professor.getFullName());
        sb.append("\n");
        sb.append(String.format("Записей: %d%n%n", rows.size()));

        long excellent = rows.stream().filter(r -> r.getCategory() == PerformanceCategory.EXCELLENT).count();
        long good = rows.stream().filter(r -> r.getCategory() == PerformanceCategory.GOOD).count();
        long satisfactory = rows.stream().filter(r -> r.getCategory() == PerformanceCategory.SATISFACTORY).count();
        long failing = rows.stream().filter(r -> r.getCategory() == PerformanceCategory.FAILING).count();

        sb.append(String.format(
                "Сводка: отличников %d, хорошистов %d, троечников %d, не успевающих %d%n%n",
                excellent, good, satisfactory, failing));

        int limit = Math.min(rows.size(), 25);
        for (int i = 0; i < limit; i++) {
            ReportRow row = rows.get(i);
            sb.append(String.format(
                    "%-35s | %s | ср. %.2f | %s | оценок: %d%n",
                    row.getStudent().getFullName(),
                    row.getStudyGroup(),
                    row.getAverage(),
                    row.getCategory().getLabel(),
                    row.getGradeCount()));
        }
        if (rows.size() > limit) {
            sb.append(String.format("... ещё %d записей%n", rows.size() - limit));
        }
        return sb.toString();
    }

    private static List<Grade> filterGrades(
            Student student,
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {

        List<Grade> result = new ArrayList<>();
        for (Grade grade : student.getGrades()) {
            if (disciplineCourseYear != null && grade.getDiscipline().getCourseYear() != disciplineCourseYear) {
                continue;
            }
            if (discipline != null && !Objects.equals(grade.getDiscipline(), discipline)) {
                continue;
            }
            if (institute != null && grade.getProfessor().getInstitute() != institute) {
                continue;
            }
            if (professor != null && grade.getProfessor() != professor) {
                continue;
            }
            result.add(grade);
        }
        return result;
    }
}
