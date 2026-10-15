package Domain;

import Domain.Enums.Sex;

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
    public String getFullName() { return firstName + " " + middleName + " " + lastName; }
}
