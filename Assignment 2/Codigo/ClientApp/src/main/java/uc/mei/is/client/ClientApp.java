package uc.mei.is.client;

import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientApp {
    public static final ConsumerService cr = new ConsumerService(WebClient.create("http://localhost:8080/api/"));

    public static void main(String[] args) {
        try {
            FileOutputStream[] files;

            if (args.length == 12) {
                files = new FileOutputStream[]{
                        new FileOutputStream(args[0]),
                        new FileOutputStream(args[1]),
                        new FileOutputStream(args[2]),
                        new FileOutputStream(args[3]),
                        new FileOutputStream(args[4]),
                        new FileOutputStream(args[5]),
                        new FileOutputStream(args[6]),
                        new FileOutputStream(args[7]),
                        new FileOutputStream(args[8]),
                        new FileOutputStream(args[9]),
                        new FileOutputStream(args[10]),
                        new FileOutputStream(args[11])
                };

            } else {
                System.out.println("NÚMERO DE ARGUMENTOS INVÁLIDOS: NOMES DEFINIDOS POR DEFAULT");

                files = new FileOutputStream[]{
                        new FileOutputStream("01_names.txt"),
                        new FileOutputStream("02_count.txt"),
                        new FileOutputStream("03_countActive.txt"),
                        new FileOutputStream("04_namesCompleted.txt"),
                        new FileOutputStream("05_dataLastYear.txt"),
                        new FileOutputStream("06_avgStd.txt"),
                        new FileOutputStream("07_avgStdFinished.txt"),
                        new FileOutputStream("08_eldestStudent.txt"),
                        new FileOutputStream("09_avgProfPerStudent.txt"),
                        new FileOutputStream("10_studentPerProf.txt"),
                        new FileOutputStream("11_completeDataStudent.txt"),
                        new FileOutputStream("00_specialQuery.txt")
                };
            }

            long start = System.currentTimeMillis();
            cr.requirements(files);
            long end = System.currentTimeMillis();

            cr.specialQueryToTolerateNetworkFailures(files[11]);

            System.out.println("TEMPO DE EXECUÇÃO (exceto a special query): " + (end - start) + " ms");

            for (FileOutputStream os: files) {
                os.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
