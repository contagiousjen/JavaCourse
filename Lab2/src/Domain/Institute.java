package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Institute {
    private final String name;
    private final List<Professor> professors = new ArrayList<>();

    public Institute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void registerProfessor(Professor professor) {
        professors.add(professor);
    }

    public List<Professor> getProfessors() {
        return Collections.unmodifiableList(professors);
    }

    @Override
    public String toString() {
        return name;
    }
}
