import Domain.Discipline;
import Domain.Enums.Sex;
import Domain.Grade;
import Domain.Professor;
import Domain.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    private Main() {}

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        // 5 преподавателей, каждый ведёт одну дисциплину
        Professor[] professors = {
            new Professor("Алексей",  "Иванович",   "Смирнов",   date(1975, 3, 12), Sex.MALE),
            new Professor("Мария",    "Петровна",   "Козлова",   date(1980, 7, 25), Sex.FEMALE),
            new Professor("Игорь",    "Сергеевич",  "Новиков",   date(1970, 11, 5), Sex.MALE),
            new Professor("Елена",    "Дмитриевна", "Орлова",    date(1985, 1, 18), Sex.FEMALE),
            new Professor("Андрей",   "Николаевич", "Волков",    date(1978, 9, 30), Sex.MALE)
        };

        Discipline[] disciplines = {
            new Discipline("Математика",        4),
            new Discipline("Физика",            3),
            new Discipline("Информатика",       5),
            new Discipline("История",           2),
            new Discipline("Английский язык",   3)
        };

        for (int i = 0; i < professors.length; i++) {
            professors[i].addDiscipline(disciplines[i]);
        }

        // 10 студентов
        Student[] students = {
            new Student("Дмитрий",   "Алексеевич", "Петров",    date(2003, 2, 14), Sex.MALE),
            new Student("Анна",      "Викторовна", "Сидорова",  date(2003, 5, 22), Sex.FEMALE),
            new Student("Михаил",    "Олегович",   "Федоров",   date(2002, 8, 3),  Sex.MALE),
            new Student("Ольга",     "Андреевна",  "Морозова",  date(2003, 11, 17),Sex.FEMALE),
            new Student("Сергей",    "Павлович",   "Волков",    date(2002, 4, 9),  Sex.MALE),
            new Student("Татьяна",   "Юрьевна",    "Лебедева",  date(2003, 1, 28), Sex.FEMALE),
            new Student("Илья",      "Романович",  "Соколов",   date(2002, 6, 15), Sex.MALE),
            new Student("Наталья",   "Игоревна",   "Козлова",   date(2003, 9, 11), Sex.FEMALE),
            new Student("Павел",     "Максимович", "Попов",     date(2002, 12, 7), Sex.MALE),
            new Student("Валерия",   "Сергеевна",  "Захарова",  date(2003, 3, 19), Sex.FEMALE)
        };

        // Каждый преподаватель выставляет по 10 оценок каждому студенту с шагом в 7 дней
        int[] gradeValues = {5, 4, 5, 3, 4, 5, 4, 3, 5, 4};

        System.out.println("=== Выставление оценок ===\n");

        for (Professor prof : professors) {
            Discipline disc = prof.getDisciplines().get(0);
            for (Student student : students) {
                Calendar cal = Calendar.getInstance();
                cal.set(2024, Calendar.SEPTEMBER, 2);

                for (int week = 0; week < 10; week++) {
                    Date gradeDate = cal.getTime();
                    boolean success = prof.gradeStudent(student, disc, gradeValues[week], gradeDate);
                    if (!success) {
                        System.out.printf("ОШИБКА: %s не смог выставить оценку %s по %s%n",
                            prof.getFullName(), student.getLastName(), disc.getName());
                    }
                    cal.add(Calendar.DAY_OF_MONTH, 7);
                }
            }
        }

        // Вывод итоговых оценок по студентам
        System.out.println("=== Итоговые оценки студентов ===\n");
        for (Student student : students) {
            System.out.printf("Студент: %s%n", student.getFullName());
            for (Grade grade : student.getGrades()) {
                System.out.printf("  %-22s | оценка: %d | дата: %s | преп.: %s %s%n",
                    grade.getDiscipline().getName(),
                    grade.getValue(),
                    sdf.format(grade.getDate()),
                    grade.getProfessor().getFirstName(),
                    grade.getProfessor().getLastName());
            }
            System.out.println();
        }

        // Демонстрация ограничений
        System.out.println("=== Демонстрация ограничений ===\n");

        Professor prof0 = professors[0];
        Discipline disc0 = disciplines[0];
        Student student0 = students[0];

        // Попытка выставить оценку раньше чем через неделю
        Date tooSoon = date(2024, Calendar.SEPTEMBER + 1, 2); // та же дата что и первая оценка
        boolean result = prof0.gradeStudent(student0, disc0, 5, tooSoon);
        System.out.printf("Попытка выставить оценку в ту же дату: %s%n",
            result ? "УСПЕХ (неожиданно)" : "ОТКЛОНЕНО (ожидаемо — слишком рано)");

        // Попытка выставить 11-ю оценку (уже выставлено 10)
        Calendar cal11 = Calendar.getInstance();
        cal11.set(2024, Calendar.SEPTEMBER, 2);
        cal11.add(Calendar.DAY_OF_MONTH, 77); // 11 недель
        result = prof0.gradeStudent(student0, disc0, 5, cal11.getTime());
        System.out.printf("Попытка выставить 11-ю оценку: %s%n",
            result ? "УСПЕХ (неожиданно)" : "ОТКЛОНЕНО (ожидаемо — превышен лимит)");

        // Попытка выставить оценку по чужой дисциплине
        result = prof0.gradeStudent(student0, disciplines[1], 5, cal11.getTime());
        System.out.printf("Попытка выставить оценку по чужой дисциплине: %s%n",
            result ? "УСПЕХ (неожиданно)" : "ОТКЛОНЕНО (ожидаемо — чужая дисциплина)");
    }

    private static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
