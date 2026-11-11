import Model.Discipline;
import Model.Institute;
import Model.Professor;
import Model.ReportRow;
import Model.Student;
import Model.University;

import java.util.List;

public class Controller {
    private final University university;
    private final View view;

    public Controller(University university, View view) {
        this.university = university;
        this.view = view;
    }

    public void run() {
        view.showCreatedCounts(
                university.getStudentCount(),
                university.getProfessorCount(),
                university.getDisciplineCount(),
                university.getInstituteCount());
        view.showGroupSizes(university.getGroupSizes());
        view.showDisciplineCounts(university.getDisciplineCountsByCourse());

        view.showReportsHeader();
        Discipline discipline = university.getSampleDiscipline();
        Institute institute = university.getSampleInstitute();
        Professor professor = university.getSampleProfessor();
        showReport(null, null, null, null);
        showReport(3, null, null, null);
        showReport(null, discipline, null, null);
        showReport(null, null, institute, null);
        showReport(null, null, null, professor);

        Student sixthCourse = university.findStudentOnCourse(6);
        if (sixthCourse != null) {
            view.showStudentHistory(sixthCourse, university.history(sixthCourse));
        }

        university.updateParentStatuses();
        List<Student> sample = university.studentsOfEachTier();
        view.showParentStatuses(sample);
        view.showDescriptions(university.getDisciplines(), university.getProfessors(), sample);

        view.showConstraintsHeader();
        University.GradeCheck tooSoon = university.sampleStudentTooSoon();
        view.showGradeCheck("Повторная оценка в ту же неделю", tooSoon.accepted);
        University.GradeCheck eleventh = university.sampleStudentEleventhGrade();
        view.showGradeCheck("11-я оценка по дисциплине", eleventh.accepted);
        University.GradeCheck foreign = university.sampleStudentForeignDiscipline();
        view.showGradeCheck("Оценка по чужой дисциплине", foreign.accepted);
        view.showSampleGrade(university.firstGrade(tooSoon.student));
    }

    private void showReport(
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {
        List<ReportRow> rows =
                university.buildReport(disciplineCourseYear, discipline, institute, professor);
        view.showReport(rows, disciplineCourseYear, discipline, institute, professor);
    }
}
