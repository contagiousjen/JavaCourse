package Model;

import Model.Enums.PerformanceCategory;

public class ReportRow {
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
