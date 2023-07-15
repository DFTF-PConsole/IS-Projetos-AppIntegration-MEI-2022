package uc.mei.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType()
@XmlAccessorType(XmlAccessType.FIELD)
public class Student extends Pessoa {

    String profuuid;

    Character gender;
    Date registrationDate;

    public Student() {
    }

    public Student(String uuid, String name, String phoneNum, String addr, Date bday, String profuuid,
                   Character gender, Date registrationDate) {
        super(uuid, name, phoneNum, addr, bday);
        this.profuuid = profuuid;
        this.gender = gender;
        this.registrationDate = registrationDate;
    }

    public String getProfuuid() {
        return this.profuuid;
    }

    public void setProfuuid(String profuuid) {
        this.profuuid = profuuid;
    }

    public Character getGender() {
        return this.gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Student profuuid(String profuuid) {
        setProfuuid(profuuid);
        return this;
    }

    public Student gender(Character gender) {
        setGender(gender);
        return this;
    }

    public Student registrationDate(Date registrationDate) {
        setRegistrationDate(registrationDate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(profuuid, student.profuuid)
                && gender == student.gender && Objects.equals(registrationDate, student.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profuuid, gender, registrationDate);
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "{" +
                " Class='" + "Student" + "'" +
                " profuuid='" + getProfuuid() + "'" +
                ", gender='" + getGender() + "'" +
                ", registrationDate='" + df.format(getRegistrationDate()) + "'" +
                "}";
    }
}
