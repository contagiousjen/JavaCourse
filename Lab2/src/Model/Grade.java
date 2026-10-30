package Model;

import java.util.Date;

public class Grade {
    private final int value;
    private final Date date;
    private final Student student;
    private final Discipline discipline;
    private final Professor professor;

    public Grade(int value, Date date, Student student, Discipline discipline, Professor professor) {
        this.value = value;
        this.date = date;
        this.student = student;
        this.discipline = discipline;
        this.professor = professor;
    }

    public int getValue() { return value; }
    public Date getDate() { return date; }
    public Student getStudent() { return student; }
    public Discipline getDiscipline() { return discipline; }
    public Professor getProfessor() { return professor; }
}
