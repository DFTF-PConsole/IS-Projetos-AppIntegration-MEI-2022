package uc.mei.is;

import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "is.mei.dei.uc.pt")
public class ProfList {
    List<Professor> professor;

    public ProfList() {
    }

    public ProfList(List<Professor> professor) {
        this.professor = professor;
    }

    public List<Professor> getprofessor() {
        return this.professor;
    }

    public void setprofessor(List<Professor> professor) {
        this.professor = professor;
    }

    public ProfList professor(List<Professor> professor) {
        setprofessor(professor);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProfList)) {
            return false;
        }
        ProfList profList = (ProfList) o;
        return Objects.equals(professor, profList.professor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(professor);
    }

    @Override
    public String toString() {
        return "{" +
                " professor='" + getprofessor() + "'" +
                "}";
    }

}
