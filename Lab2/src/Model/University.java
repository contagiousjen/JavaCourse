package Model;

import Model.Enums.PerformanceCategory;
import Model.Helper.UniversityDataHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Состояние университета и операции над ним: выборки, отчёты, проверки ограничений.
 */
public class University {
    private final UniversityDataHelper.UniversityData data;
    private final PerformanceReportService reports = new PerformanceReportService();

    public University() {
        data = new UniversityDataHelper().create();
    }

    public int getStudentCount() {
        return data.students.size();
    }

    public int getProfessorCount() {
        return data.professors.size();
    }

    public int getDisciplineCount() {
        return data.disciplines.size();
    }

    public int getInstituteCount() {
        return data.institutes.size();
    }

    public Discipline getSampleDiscipline() {
        return data.disciplines.get(0);
    }

    public Institute getSampleInstitute() {
        return data.institutes.get(0);
    }

    public Professor getSampleProfessor() {
        return data.professors.get(0);
    }

    public List<GroupSize> getGroupSizes() {
        List<GroupSize> sizes = new ArrayList<>();
        for (int course = 1; course <= UniversityDataHelper.COURSES; course++) {
            for (int group = 1; group <= UniversityDataHelper.GROUPS_PER_COURSE; group++) {
                int count = 0;
                for (Student student : data.students) {
                    if (student.getStudyGroup().getCourseYear() == course
                            && student.getStudyGroup().getGroupNumber() == group) {
                        count++;
                    }
                }
                sizes.add(new GroupSize(course, group, count));
            }
        }
        return sizes;
    }

    public List<CourseSize> getDisciplineCountsByCourse() {
        List<CourseSize> sizes = new ArrayList<>();
        for (int course = 1; course <= UniversityDataHelper.COURSES; course++) {
            int count = 0;
            for (Discipline discipline : data.disciplines) {
                if (discipline.getCourseYear() == course) {
                    count++;
                }
            }
            sizes.add(new CourseSize(course, count));
        }
        return sizes;
    }

    public List<ReportRow> buildReport(
            Integer disciplineCourseYear,
            Discipline discipline,
            Institute institute,
            Professor professor) {
        return reports.buildReport(data.students, disciplineCourseYear, discipline, institute, professor);
    }

    public Student findStudentOnCourse(int courseYear) {
        for (Student student : data.students) {
            if (student.getCourseYear() == courseYear) {
                return student;
            }
        }
        return null;
    }

    public List<CoursePerformance> history(Student student) {
        List<CoursePerformance> history = new ArrayList<>();
        for (int year = 1; year <= student.getCourseYear(); year++) {
            history.add(new CoursePerformance(
                    year,
                    student.getAverageGradeForCourse(year),
                    countGradesForCourse(student, year)));
        }
        return history;
    }

    public void updateParentStatuses() {
        for (Parent parent : data.parents) {
            parent.updateStatusAndPremium();
        }
    }

    public List<Professor> getProfessors() {
        return data.professors;
    }

    public List<Student> studentsOfEachTier() {
        List<Student> sample = new ArrayList<>();
        for (PerformanceCategory category : PerformanceCategory.values()) {
            for (Student student : data.students) {
                if (student.getPerformanceTier() == category) {
                    sample.add(student);
                    break;
                }
            }
        }
        return sample;
    }

    public GradeCheck sampleStudentTooSoon() {
        Professor professor = data.professors.get(0);
        Discipline discipline = professor.getDisciplines().get(0);
        Student student = data.students.get(0);
        boolean accepted = professor.gradeStudent(student, discipline, 5, date(2024, 9, 2));
        return new GradeCheck(student, discipline, accepted);
    }

    public GradeCheck sampleStudentEleventhGrade() {
        Professor professor = data.professors.get(0);
        Discipline discipline = professor.getDisciplines().get(0);
        Student student = data.students.get(0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 2, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 77);
        boolean accepted = professor.gradeStudent(student, discipline, 5, calendar.getTime());
        return new GradeCheck(student, discipline, accepted);
    }

    public GradeCheck sampleStudentForeignDiscipline() {
        Professor professor = data.professors.get(0);
        Discipline own = professor.getDisciplines().get(0);
        Student student = data.students.get(0);
        Discipline foreign = own;
        for (Discipline discipline : data.disciplines) {
            if (!professor.getDisciplines().contains(discipline)) {
                foreign = discipline;
                break;
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 2, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 77);
        boolean accepted = professor.gradeStudent(student, foreign, 5, calendar.getTime());
        return new GradeCheck(student, foreign, accepted);
    }

    public Grade firstGrade(Student student) {
        return student.getGrades().get(0);
    }

    private static int countGradesForCourse(Student student, int courseYear) {
        int count = 0;
        for (Grade grade : student.getGrades()) {
            if (grade.getDiscipline().getCourseYear() == courseYear) {
                count++;
            }
        }
        return count;
    }

    private static Date date(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static final class GroupSize {
        public final int courseYear;
        public final int groupNumber;
        public final int studentCount;

        GroupSize(int courseYear, int groupNumber, int studentCount) {
            this.courseYear = courseYear;
            this.groupNumber = groupNumber;
            this.studentCount = studentCount;
        }
    }

    public static final class CourseSize {
        public final int courseYear;
        public final int count;

        CourseSize(int courseYear, int count) {
            this.courseYear = courseYear;
            this.count = count;
        }
    }

    public static final class CoursePerformance {
        public final int courseYear;
        public final double average;
        public final int gradeCount;

        CoursePerformance(int courseYear, double average, int gradeCount) {
            this.courseYear = courseYear;
            this.average = average;
            this.gradeCount = gradeCount;
        }
    }

    public static final class GradeCheck {
        public final Student student;
        public final Discipline discipline;
        public final boolean accepted;

        GradeCheck(Student student, Discipline discipline, boolean accepted) {
            this.student = student;
            this.discipline = discipline;
            this.accepted = accepted;
        }
    }
}
