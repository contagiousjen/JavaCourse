package Service;

import Domain.Discipline;
import Domain.Enums.PerformanceCategory;
import Domain.Institute;
import Domain.Parent;
import Domain.Professor;
import Domain.Student;
import Domain.StudyGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UniversityDataFactory {
    public static final int COURSES = 6;
    public static final int GROUPS_PER_COURSE = 3;
    public static final int STUDENTS_PER_GROUP = 10;
    public static final int INSTITUTE_COUNT = 3;
    public static final int PROFESSORS_PER_INSTITUTE = 5;
    public static final int DISCIPLINES_PER_PROFESSOR = 2;
    public static final int DISCIPLINES_PER_COURSE = 5;

    private static final String[] INSTITUTE_NAMES = {
            "Институт информационных технологий",
            "Институт экономики и управления",
            "Институт естественных наук"
    };

    private static final String[] DISCIPLINE_STEMS = {
            "Математика", "Физика", "Программирование", "Базы данных", "Алгоритмы",
            "Экономика", "Менеджмент", "Статистика", "Маркетинг", "Бухучёт",
            "Химия", "Биология", "Экология", "География", "История",
            "Философия", "Право", "Иностранный язык", "Физкультура", "Культура",
            "Сети", "ОС", "Машинное обучение", "Анализ данных", "Проектирование",
            "Финансы", "Логистика", "Микробиология", "Астрофизика", "Политология"
    };

    private final Random random = new Random(42);
    private final NamePool names = new NamePool(random);

    public static class UniversityData {
        public final List<Institute> institutes;
        public final List<Professor> professors;
        public final List<Discipline> disciplines;
        public final List<Student> students;
        public final List<Parent> parents;

        UniversityData(
                List<Institute> institutes,
                List<Professor> professors,
                List<Discipline> disciplines,
                List<Student> students,
                List<Parent> parents) {
            this.institutes = institutes;
            this.professors = professors;
            this.disciplines = disciplines;
            this.students = students;
            this.parents = parents;
        }
    }

    public UniversityData create() {
        List<Institute> institutes = createInstitutes();
        List<Professor> professors = createProfessors(institutes);
        List<Discipline> disciplines = createDisciplines(professors);
        List<Student> students = new ArrayList<>();
        List<Parent> parents = new ArrayList<>();
        createStudentsWithParents(students, parents);
        assignGrades(students, disciplines);
        return new UniversityData(institutes, professors, disciplines, students, parents);
    }

    private List<Institute> createInstitutes() {
        List<Institute> list = new ArrayList<>();
        for (String name : INSTITUTE_NAMES) {
            list.add(new Institute(name));
        }
        return list;
    }

    private List<Professor> createProfessors(List<Institute> institutes) {
        List<Professor> list = new ArrayList<>();
        int idx = 0;
        for (Institute institute : institutes) {
            for (int i = 0; i < PROFESSORS_PER_INSTITUTE; i++) {
                NamePool.PersonName name = names.randomPerson(names.randomSex());
                Professor p = new Professor(
                        name.firstName, name.middleName, name.lastName,
                        date(1968 + idx, 3 + (idx % 9), 5 + (idx % 20)),
                        name.sex,
                        institute);
                institute.registerProfessor(p);
                list.add(p);
                idx++;
            }
        }
        return list;
    }

    private List<Discipline> createDisciplines(List<Professor> professors) {
        List<Discipline> list = new ArrayList<>();
        int nameIdx = 0;
        int profIdx = 0;
        for (int course = 1; course <= COURSES; course++) {
            for (int d = 0; d < DISCIPLINES_PER_COURSE; d++) {
                Professor professor = professors.get(profIdx / DISCIPLINES_PER_PROFESSOR);
                String name = DISCIPLINE_STEMS[nameIdx] + " (" + course + " курс)";
                Discipline discipline = new Discipline(name, 3 + (course % 3), course, professor);
                professor.addDiscipline(discipline);
                list.add(discipline);
                nameIdx++;
                profIdx++;
            }
        }
        return list;
    }

    private void createStudentsWithParents(List<Student> students, List<Parent> parents) {
        int serial = 1;
        for (int course = 1; course <= COURSES; course++) {
            for (int group = 1; group <= GROUPS_PER_COURSE; group++) {
                StudyGroup studyGroup = new StudyGroup(course, group);
                PerformanceCategory[] tiers = tierPlanForGroup();
                for (int i = 0; i < STUDENTS_PER_GROUP; i++) {
                    int surnameIndex = names.randomSurnameIndex();
                    NamePool.PersonName studentName = names.randomPersonWithSurname(names.randomSex(), surnameIndex);
                    Student student = new Student(
                            studentName.firstName,
                            studentName.middleName,
                            studentName.lastName,
                            date(2000 + (6 - course), 1 + (serial % 12), 1 + (serial % 25)),
                            studentName.sex,
                            course,
                            studyGroup);
                    student.setPerformanceTier(tiers[i]);
                    students.add(student);

                    NamePool.PersonName parentName = names.randomPersonWithSurname(names.randomSex(), surnameIndex);
                    Parent parent = new Parent(
                            parentName.firstName,
                            parentName.middleName,
                            parentName.lastName,
                            date(1975 + (serial % 10), 5, 10),
                            parentName.sex,
                            new Student[]{student});
                    student.setParent(parent);
                    parents.add(parent);
                    serial++;
                }
            }
        }
    }

    private PerformanceCategory[] tierPlanForGroup() {
        int excellent = randomRange(1, 2);
        int failing = randomRange(0, 1);
        int good = randomRange(2, 4);
        int satisfactory = STUDENTS_PER_GROUP - excellent - failing - good;
        while (satisfactory < 2 || satisfactory > 4) {
            if (satisfactory < 2 && good > 2) {
                good--;
                satisfactory++;
            } else if (satisfactory > 4 && good < 4) {
                good++;
                satisfactory--;
            } else if (satisfactory < 2 && excellent > 1) {
                excellent--;
                satisfactory++;
            } else if (satisfactory > 4 && failing < 1) {
                failing++;
                satisfactory--;
            } else {
                break;
            }
        }
        List<PerformanceCategory> tiers = new ArrayList<>();
        addTier(tiers, PerformanceCategory.EXCELLENT, excellent);
        addTier(tiers, PerformanceCategory.GOOD, good);
        addTier(tiers, PerformanceCategory.SATISFACTORY, satisfactory);
        addTier(tiers, PerformanceCategory.FAILING, failing);
        Collections.shuffle(tiers, random);
        return tiers.toArray(new PerformanceCategory[0]);
    }

    private static void addTier(List<PerformanceCategory> tiers, PerformanceCategory cat, int count) {
        for (int i = 0; i < count; i++) {
            tiers.add(cat);
        }
    }

    private void assignGrades(List<Student> students, List<Discipline> allDisciplines) {
        for (Student student : students) {
            int course = student.getCourseYear();
            for (int year = 1; year <= course; year++) {
                List<Discipline> courseDisciplines = disciplinesForCourse(allDisciplines, year);
                int[] pattern = gradePattern(student.getPerformanceTier());
                Calendar cal = academicStart(student.getCourseYear(), year);
                for (Discipline discipline : courseDisciplines) {
                    Professor professor = discipline.getProfessor();
                    for (int week = 0; week < 10; week++) {
                        professor.gradeStudent(student, discipline, pattern[week], cal.getTime());
                        cal.add(Calendar.DAY_OF_MONTH, 7);
                    }
                }
            }
        }
    }

    private static List<Discipline> disciplinesForCourse(List<Discipline> all, int courseYear) {
        List<Discipline> list = new ArrayList<>();
        for (Discipline d : all) {
            if (d.getCourseYear() == courseYear) {
                list.add(d);
            }
        }
        return list;
    }

    private static Calendar academicStart(int studentCourse, int disciplineCourse) {
        Calendar cal = Calendar.getInstance();
        int yearsAgo = studentCourse - disciplineCourse;
        cal.set(2024 - yearsAgo, Calendar.SEPTEMBER, 2, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    private static int[] gradePattern(PerformanceCategory tier) {
        switch (tier) {
            case EXCELLENT:
                return new int[]{5, 5, 5, 5, 5, 5, 5, 4, 5, 5};
            case GOOD:
                return new int[]{4, 4, 5, 4, 4, 4, 4, 4, 5, 4};
            case SATISFACTORY:
                return new int[]{3, 3, 4, 3, 3, 3, 4, 3, 3, 3};
            case FAILING:
                return new int[]{2, 2, 3, 2, 2, 2, 2, 3, 2, 2};
            default:
                return new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        }
    }

    private int randomRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    private static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
