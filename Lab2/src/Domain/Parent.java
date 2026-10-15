package Domain;

import Domain.Enums.ParentStatus;
import Domain.Enums.Sex;

import java.util.Date;

public class Parent extends Person
{
    public Student[] students;
    public ParentStatus status;

    public Parent(
            String firstName,
            String middleName,
            String lastName,
            Date birthDate,
            Sex sex,
            Student[] students,
            ParentStatus status) {
        super(firstName, middleName, lastName, birthDate, sex);
        this.students = students;
        this.status = status;
    }
}
