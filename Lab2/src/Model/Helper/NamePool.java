package Model.Helper;

import Model.Enums.Sex;

import java.util.Random;

public class NamePool {
    private static final String[] MALE_FIRST = {
            "Александр", "Алексей", "Андрей", "Антон", "Артём",
            "Борис", "Вадим", "Виктор", "Владимир", "Глеб",
            "Денис", "Дмитрий", "Егор", "Иван", "Игорь",
            "Кирилл", "Константин", "Максим", "Михаил", "Никита",
            "Олег", "Павел", "Пётр", "Роман", "Сергей",
            "Тимур", "Фёдор", "Юрий", "Ярослав", "Станислав"
    };

    private static final String[] FEMALE_FIRST = {
            "Алина", "Анастасия", "Анна", "Валерия", "Вера",
            "Дарья", "Екатерина", "Елена", "Ирина", "Ксения",
            "Мария", "Наталья", "Ольга", "Полина", "Светлана",
            "София", "Татьяна", "Юлия", "Яна", "Алёна",
            "Виктория", "Диана", "Евгения", "Кристина", "Людмила",
            "Маргарита", "Нина", "Оксана", "Тамара", "Ульяна"
    };

    private static final String[] MALE_PATRONYMIC = {
            "Александрович", "Алексеевич", "Андреевич", "Борисович", "Васильевич",
            "Викторович", "Владимирович", "Геннадьевич", "Дмитриевич", "Евгеньевич",
            "Иванович", "Игоревич", "Кириллович", "Максимович", "Михайлович",
            "Николаевич", "Олегович", "Павлович", "Петрович", "Романович",
            "Сергеевич", "Степанович", "Фёдорович", "Юрьевич", "Ярославович"
    };

    private static final String[] FEMALE_PATRONYMIC = {
            "Александровна", "Алексеевна", "Андреевна", "Борисовна", "Васильевна",
            "Викторовна", "Владимировна", "Геннадьевна", "Дмитриевна", "Евгеньевна",
            "Ивановна", "Игоревна", "Кирилловна", "Максимовна", "Михайловна",
            "Николаевна", "Олеговна", "Павловна", "Петровна", "Романовна",
            "Сергеевна", "Степановна", "Фёдоровна", "Юрьевна", "Ярославовна"
    };

    private static final String[] LAST_MALE = {
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Попов", "Соколов", "Лебедев", "Козлов", "Новиков",
            "Морозов", "Волков", "Алексеев", "Зайцев", "Павлов",
            "Семёнов", "Голубев", "Виноградов", "Богданов", "Воробьёв",
            "Фёдоров", "Михайлов", "Белов", "Тарасов", "Беляев",
            "Комаров", "Орлов", "Киселёв", "Макаров", "Андреев",
            "Ковалёв", "Ильин", "Гусев", "Титов", "Кузьмин",
            "Кудрявцев", "Баранов", "Куликов", "Никитин", "Степанов"
    };

    private static final String[] LAST_FEMALE = {
            "Иванова", "Петрова", "Сидорова", "Смирнова", "Кузнецова",
            "Попова", "Соколова", "Лебедева", "Козлова", "Новикова",
            "Морозова", "Волкова", "Алексеева", "Зайцева", "Павлова",
            "Семёнова", "Голубева", "Виноградова", "Богданова", "Воробьёва",
            "Фёдорова", "Михайлова", "Белова", "Тарасова", "Беляева",
            "Комарова", "Орлова", "Киселёва", "Макарова", "Андреева",
            "Ковалёва", "Ильина", "Гусева", "Титова", "Кузьмина",
            "Кудрявцева", "Баранова", "Куликова", "Никитина", "Степанова"
    };

    private final Random random;

    public NamePool(Random random) {
        this.random = random;
    }

    public PersonName randomPerson(Sex sex) {
        return new PersonName(
                pick(sex == Sex.MALE ? MALE_FIRST : FEMALE_FIRST),
                pick(sex == Sex.MALE ? MALE_PATRONYMIC : FEMALE_PATRONYMIC),
                pick(sex == Sex.MALE ? LAST_MALE : LAST_FEMALE),
                sex);
    }

    public PersonName randomPersonWithSurname(Sex sex, int surnameIndex) {
        String last = sex == Sex.MALE ? LAST_MALE[surnameIndex] : LAST_FEMALE[surnameIndex];
        return new PersonName(
                pick(sex == Sex.MALE ? MALE_FIRST : FEMALE_FIRST),
                pick(sex == Sex.MALE ? MALE_PATRONYMIC : FEMALE_PATRONYMIC),
                last,
                sex);
    }

    public int randomSurnameIndex() {
        return random.nextInt(LAST_MALE.length);
    }

    public Sex randomSex() {
        return random.nextBoolean() ? Sex.MALE : Sex.FEMALE;
    }

    private String pick(String[] pool) {
        return pool[random.nextInt(pool.length)];
    }

    public static final class PersonName {
        public final String firstName;
        public final String middleName;
        public final String lastName;
        public final Sex sex;

        PersonName(String firstName, String middleName, String lastName, Sex sex) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.sex = sex;
        }
    }
}
