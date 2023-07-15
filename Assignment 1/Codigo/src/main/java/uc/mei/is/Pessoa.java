package uc.mei.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType()
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Pessoa {

    @XmlAttribute
    String uuid;

    String name;
    @XmlElement(name="PhoneNumber")
    String phoneNum;
    @XmlElement(name="Adress")
    String addr;
    @XmlElement(name="Birthday")
    Date bday;

    public Pessoa() {
    }

    public Pessoa(String uuid, String name, String phoneNum, String addr, Date bday) {
        this.uuid = uuid;
        this.name = name;
        this.phoneNum = phoneNum;
        this.addr = addr;
        this.bday = bday;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getBday() {
        return this.bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public Pessoa name(String name) {
        setName(name);
        return this;
    }

    public Pessoa phoneNum(String phoneNum) {
        setPhoneNum(phoneNum);
        return this;
    }

    public Pessoa addr(String addr) {
        setAddr(addr);
        return this;
    }

    public Pessoa bday(Date bday) {
        setBday(bday);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pessoa)) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(name, pessoa.name) && Objects.equals(phoneNum, pessoa.phoneNum)
                && Objects.equals(addr, pessoa.addr) && Objects.equals(bday, pessoa.bday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNum, addr, bday);
    }


    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "{" +
                " uuid='" + getUuid() + "'" +
                ", name='" + getName() + "'" +
                ", phoneNum='" + getPhoneNum() + "'" +
                ", addr='" + getAddr() + "'" +
                ", bday='" + df.format(getBday()) + "'" +
                "}";
    }
}
