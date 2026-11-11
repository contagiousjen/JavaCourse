package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

class PerformanceReportService {

    List<ReportRow> buildReport(
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
            for (Grade grade : matched) {
                sum += grade.getValue();
            }
            rows.add(new ReportRow(student, sum / matched.size(), matched.size()));
        }
        rows.sort(Comparator
                .comparing((ReportRow row) -> row.getStudyGroup().getCourseYear())
                .thenComparing(row -> row.getStudyGroup().getGroupNumber())
                .thenComparing(row -> row.getStudent().getLastName()));
        return rows;
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
