package uc.mei.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

public class Generator {
    /**
     * sizeProf is how many professores there are.
     * sizeStudent is how many students a prof has.
     */
    private long sizeProf;
    private long sizeStudent;
    private Random random;
    private List<Professor> profs;
    private List<Student> students;
    private Faker fkr;

    public Generator(long sizeProf, long sizeStudent, long seed) {
        this.sizeProf = sizeProf;
        this.sizeStudent = sizeStudent;
        this.random = new Random(seed);
        profs = new ArrayList<>();
        students = new ArrayList<>();
        fkr = new Faker(Locale.ENGLISH, random);
    }

    public List<Professor> getProfs() {
        return this.profs;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void run(){
        String uuidProf;
        List<Student> studentsT;
        for (int i = 0; i < sizeProf; i++) {
            uuidProf = UUID.randomUUID().toString();
            studentsT = new ArrayList<>();
            for (int j = 0; j < sizeStudent; j++) {
                studentsT.add(new Student(
                        UUID.randomUUID().toString(),
                        generateName(),
                        generatePhoneNumber(),
                        generateAddr(),
                        generateRandomDate(1930, 2000),
                        uuidProf,
                        generateGender(),
                        generateRandomDate(2010, 2022)
                ));
            }
            profs.add(new Professor(
                    uuidProf,
                    generateName(),
                    generatePhoneNumber(),
                    generateAddr(),
                    generateRandomDate(1930, 2000),
                    studentsT));
            students.addAll(studentsT);
        }
    }

    public Character generateGender() {
        int rand = random.nextInt(3);
        switch (rand) {
            case 1:
                return 'm';
            case 2:
                return 'f';
            default:
                return 'o';
        }
    }

    public Date generateRandomDate(int startYear, int endYear) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return fkr.date().between(df.parse("01-01-"+startYear), df.parse("3-12-"+endYear));
        } catch (Exception e) {
            e.printStackTrace();
            return new Date(1);
        }
    }

    private String generatePhoneNumber() {

        char[] phone = {'9',generateDigit(),generateDigit(),
                generateDigit(),generateDigit(),generateDigit(),
                generateDigit(),generateDigit(),generateDigit()};
        return new String(phone);
    }

    private char generateDigit(){
        return Character.forDigit(random.nextInt(10),10);
    }

    private String generateName() {
        return fkr.name().fullName();
    }

    private String generateAddr() {
        Address addr = fkr.address();
        return addr.streetAddress() + " " + addr.buildingNumber() + ", " + addr.city() + ", " + addr.country();
    }
}
