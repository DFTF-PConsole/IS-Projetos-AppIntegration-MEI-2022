package uc.mei.is;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Professor",namespace = "dei.uc.mei.is-proff")
// @XmlType(propOrder = { "student" })
@XmlAccessorType(XmlAccessType.FIELD)
class Professor extends Pessoa {

    @XmlElementWrapper(name="StudentList")
    List<Student> student;

    public Professor() {
    }

    public Professor(String uuid, String name, String phoneNum, String addr, Date bday, List<Student> student) {
        super(uuid, name, phoneNum, addr, bday);
        this.student = student;
    }

    public List<Student> getStudent() {
        return this.student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public Professor student(List<Student> student) {
        setStudent(student);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Professor)) {
            return false;
        }
        Professor professor = (Professor) o;
        return Objects.equals(student, professor.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),student);
    }

    @Override
    public String toString() {
        return "{" +
                " professor='" + super.toString() + "'," +
                " student='" + getStudent() + "'" +
                "}";
    }
}
