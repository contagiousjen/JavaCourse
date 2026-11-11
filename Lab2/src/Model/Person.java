package Model;

import Model.Enums.Sex;

import java.util.Calendar;
import java.util.Date;

public abstract class Person
{
    private String firstName;
    private String middleName;
    private String lastName;
    private final Date birthDate;
    private final Sex sex;

    public Person(
            String firstName,
            String middleName,
            String lastName,
            Date birthDate,
            Sex sex)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return lastName + " " + firstName + " " + middleName; }
    public Sex getSex() { return sex; }

    public int getAge() {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) age--;
        return age;
    }

    protected String sexLabel() {
        return sex == Sex.MALE ? "мужской" : "женский";
    }

    @Override
    public String toString() {
        return String.format("%s, %d лет, пол: %s", getFullName(), getAge(), sexLabel());
    }
}
