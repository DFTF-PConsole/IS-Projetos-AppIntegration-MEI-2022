package uc.mei.is;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.sql.Timestamp;

import com.opencsv.CSVWriter;
import jakarta.xml.bind.*;

import static uc.mei.is.IdEstaTempos.*;
import static uc.mei.is.IdEstaTamanhos.*;


public class App {
    // ****** CONSTANTES ******
    public final static String pathGui = "C:\\Users\\guibs\\Documents\\GitHub\\IS-Assignment1\\simplejaxb\\Outputs\\";
    public final static String pathDario = "C:\\Users\\Asus\\OneDrive\\Documentos\\GitHub\\IS-Assignment1\\simplejaxb\\Outputs\\";
    public final static String extensaoXML = ".xml";
    public final static String extensaoXMLGzip = ".gz";
    public final static String extensaoProtobuf = ".proto";
    public final static long[] listaSeeds = {12131451, 510, 6711890, 10000, 987165, 11171, 431210, 4444144, 1213, 246180, 4516, 13579, 7819, 5431543, 2711727, 7514385, 9996166, 190121835, 1029138, 47116, 2111, 90110, 389146, 333133, 874911119};
    public final static String extensaoCSV = ".csv";
    public final static String estatisticaCSV = "estatisticas";

    // ****** ****** PARAMETROS ****** ******
    public static String utilizarPath = App.pathDario;
    public static boolean testarXML = true;
    public static boolean testarXMLGzip = true;
    public static boolean testarProtobuf = true;
    public static long seed = 111155588;
    public static int totalRepeticoes = 20;

    // se false utiliza apenas a seed da App.utilizarSeed | se true utiliza as seeds da lista App.listaSeeds
    public static boolean utilizarDiferentesSeeds = true;

    public static long[][] matrizGenSizesProfStudent = {
            {10, 10},
            {50, 50},
            {100, 100},
            {200, 200},
            {300, 300},
            {500, 500},
            {700, 700},
            {50, 10},
            {50, 100},
            {50, 200},
            {50, 300},
            {50, 500},
            {50, 1000},
            {10, 50},
            {100, 50},
            {200, 50},
            {300, 50},
            {500, 50},
            {1000, 50}
    };
    // ****** ****** ****** ****** ****** ******


    // ****** VARS GLOBAIS ******
    public static long[][][] estatisticasTempos;
    public static long[][][] estatisticasTamanhos;

    public static void main(String[] args) {
        App.estatisticasTempos = new long[App.matrizGenSizesProfStudent.length][IdEstaTempos.values().length][App.totalRepeticoes];
        App.estatisticasTamanhos = new long[App.matrizGenSizesProfStudent.length][IdEstaTamanhos.values().length][App.totalRepeticoes];
        String sufixoCSV = new SimpleDateFormat("__yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        CSVWriter writerEsta = null;

        if (App.utilizarDiferentesSeeds && App.totalRepeticoes > App.listaSeeds.length) {
            System.out.println("ERRO: Potencial IndexOutOfBoundsException: repeticoes > listaSeeds.length");
            System.exit(1);
        }

        File dir = new File(App.utilizarPath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            // Print output para ficheiro Outputs/log.txt
            //System.setOut(new PrintStream(Files.newOutputStream(Paths.get(App.utilizarPath + "log" + sufixoCSV + ".txt"))));

            writerEsta = new CSVWriter(new FileWriter(App.utilizarPath + App.estatisticaCSV + sufixoCSV + App.extensaoCSV), ';');

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
            if (App.utilizarDiferentesSeeds) {
                App.seed = App.listaSeeds[idRepeticao];
            }
            System.out.println("\n\n*** REPETIÇÃO " + (idRepeticao + 1) + "/" + App.totalRepeticoes + " ***");
            runTeste(App.seed, idRepeticao);
        }

        System.out.println("\n\n*** ESTATISTICAS ***\n");
        System.out.println("Repetições: " + App.totalRepeticoes + " / Diferentes seeds entre repetições?: " + App.utilizarDiferentesSeeds + "\n");
        //writerEsta.writeNext(new String[]{"Total Repetições", "Diferentes seeds entre repetições?"});
        //writerEsta.writeNext(new String[]{String.valueOf(App.totalRepeticoes), String.valueOf(App.utilizarDiferentesSeeds)});
        //writerEsta.writeNext(new String[]{});
        System.out.printf("%-34s | %-23s | %-49s | %-23s | %-49s | %-23s | %-75s | %-49s\n", "NOME / CARACTERISTICAS", "GENERATOR", "XML: MARSHAL", "XML: UNMARSHAL", "XML GZIP: MARSHAL", "XML GZIP: UNMARSHAL", "PROTOBUF: MARSHAL", "PROTOBUF: UNMARSHAL");
        writerEsta.writeNext(new String[]{
                "NOME / CARACTERISTICAS",
                "",
                "",
                "GENERATOR",
                "",
                "XML: MARSHAL",
                "",
                "XML: SIZE",
                "",
                "XML: UNMARSHAL",
                "",
                "XML GZIP: MARSHAL",
                "",
                "XML GZIP: SIZE",
                "",
                "XML GZIP: UNMARSHAL",
                "",
                "PROTOBUF: MARSHAL",
                "",
                "PROTOBUF: INI DATA STRUCTURES",
                "",
                "PROTOBUF: SIZE",
                "",
                "PROTOBUF: UNMARSHAL",
                "",
                "PROTOBUF: INI DATA STRUCTURES",
                ""
        });

        System.out.printf("%-34s | %-23s | %-23s   %-23s | %-23s | %-23s   %-23s | %-23s | %-23s   %-23s   %-23s | %-23s   %-23s\n", "", "Tempo (ms)", "Tempo (ms)", "Tamanho (KB)", "Tempo (ms)", "Tempo (ms)", "Tamanho (KB)", "Tempo (ms)", "Tempo (ms)", "Tempo (ms)", "Tamanho (KB)", "Tempo (ms)", "Tempo (ms)");
        writerEsta.writeNext(new String[]{
                "",
                "",
                "",
                "Tempo (ms)",
                "",
                "Tempo (ms)",
                "",
                "Tamanho (KB)",
                "",
                "Tempo (ms)",
                "",
                "Tempo (ms)",
                "",
                "Tamanho (KB)",
                "",
                "Tempo (ms)",
                "",
                "Tempo (ms)",
                "",
                "Tempo (ms)",
                "",
                "Tamanho (KB)",
                "",
                "Tempo (ms)",
                "",
                "Tempo (ms)",
                ""
        });

        System.out.printf("%-7s   %-12s   %-9s | %-10s   %-10s | %-10s   %-10s   %-10s   %-10s | %-10s   %-10s | %-10s   %-10s   %-10s   %-10s | %-10s   %-10s | %-10s   %-10s   %-10s   %-10s   %-10s   %-10s | %-10s   %-10s   %-10s   %-10s\n", "Indice", "Professores", "Students", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio", "Média", "Desvio");
        writerEsta.writeNext(new String[]{
                "Indice",
                "Professores",
                "Students",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão",
                "Média",
                "Desvio-Padrão"
        });

        for (int idFich=0; idFich < App.matrizGenSizesProfStudent.length ; idFich++) {
            System.out.printf("%-7d   %-12d   %-9d", idFich, App.matrizGenSizesProfStudent[idFich][0], App.matrizGenSizesProfStudent[idFich][1]);
            double[] tempTemposMedia = new double[IdEstaTempos.values().length];
            double[] tempTemposDesvio = new double[IdEstaTempos.values().length];

            double[] tempTamanhoMedia = new double[IdEstaTamanhos.values().length];
            double[] tempTamanhoDesvio = new double[IdEstaTamanhos.values().length];

            for (IdEstaTempos idEstaTempos: IdEstaTempos.values()) {
                double mediaTempo=0, varianciaTempo=0;
                double desvioPadraoTempo;
                for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
                    mediaTempo += App.estatisticasTempos[idFich][idEstaTempos.indice][idRepeticao];
                }
                tempTemposMedia[idEstaTempos.indice] = mediaTempo = mediaTempo/App.totalRepeticoes;
                for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
                    varianciaTempo += Math.pow((App.estatisticasTempos[idFich][idEstaTempos.indice][idRepeticao] - mediaTempo), 2);
                }
                tempTemposDesvio[idEstaTempos.indice] = desvioPadraoTempo = Math.sqrt(varianciaTempo/App.totalRepeticoes);

                int idTamanhoTemp;
                if (idEstaTempos.indice == XML_MARSHAL.indice) {
                    idTamanhoTemp = XML_SIZE.indice;
                } else if (idEstaTempos.indice == XML_GZIP_MARSHAL.indice) {
                    idTamanhoTemp = XML_GZIP_SIZE.indice;
                } else if (idEstaTempos.indice == PROTOBUF_MAR_INI.indice) {
                    idTamanhoTemp = PROTOBUF_SIZE.indice;
                } else {
                    idTamanhoTemp = -1;
                }

                if (idTamanhoTemp != -1) {
                    double mediaTamanho=0, varianciaTamanho=0;
                    double desvioPadraoTamanho;

                    for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
                        mediaTamanho += App.estatisticasTamanhos[idFich][idTamanhoTemp][idRepeticao];
                    }
                    tempTamanhoMedia[idTamanhoTemp] = mediaTamanho = mediaTamanho/App.totalRepeticoes;
                    for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
                        varianciaTamanho += Math.pow((App.estatisticasTamanhos[idFich][idTamanhoTemp][idRepeticao] - mediaTamanho), 2);
                    }
                    tempTamanhoDesvio[idTamanhoTemp] = desvioPadraoTamanho = Math.sqrt(varianciaTamanho/App.totalRepeticoes);

                    System.out.printf(" | %-10.2f   %-10.2f   %-10.2f   %-10.2f", mediaTempo, desvioPadraoTempo, mediaTamanho, desvioPadraoTamanho);
                } else {
                    System.out.printf(" | %-10.2f   %-10.2f", mediaTempo, desvioPadraoTempo);
                }
            }
            System.out.println();
            writerEsta.writeNext(new String[]{
                    String.valueOf(idFich),
                    String.valueOf(App.matrizGenSizesProfStudent[idFich][0]),
                    String.valueOf(App.matrizGenSizesProfStudent[idFich][1]),
                    String.valueOf(tempTemposMedia[GENERATOR.indice]),
                    String.valueOf(tempTemposDesvio[GENERATOR.indice]),
                    String.valueOf(tempTemposMedia[XML_MARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[XML_MARSHAL.indice]),
                    String.valueOf(tempTamanhoMedia[XML_SIZE.indice]),
                    String.valueOf(tempTamanhoDesvio[XML_SIZE.indice]),
                    String.valueOf(tempTemposMedia[XML_UNMARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[XML_UNMARSHAL.indice]),
                    String.valueOf(tempTemposMedia[XML_GZIP_MARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[XML_GZIP_MARSHAL.indice]),
                    String.valueOf(tempTamanhoMedia[XML_GZIP_SIZE.indice]),
                    String.valueOf(tempTamanhoDesvio[XML_GZIP_SIZE.indice]),
                    String.valueOf(tempTemposMedia[XML_GZIP_UNMARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[XML_GZIP_UNMARSHAL.indice]),
                    String.valueOf(tempTemposMedia[PROTOBUF_MARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[PROTOBUF_MARSHAL.indice]),
                    String.valueOf(tempTemposMedia[PROTOBUF_MAR_INI.indice]),
                    String.valueOf(tempTemposDesvio[PROTOBUF_MAR_INI.indice]),
                    String.valueOf(tempTamanhoMedia[PROTOBUF_SIZE.indice]),
                    String.valueOf(tempTamanhoDesvio[PROTOBUF_SIZE.indice]),
                    String.valueOf(tempTemposMedia[PROTOBUF_UNMARSHAL.indice]),
                    String.valueOf(tempTemposDesvio[PROTOBUF_UNMARSHAL.indice]),
                    String.valueOf(tempTemposMedia[PROTOBUF_UNMAR_INI.indice]),
                    String.valueOf(tempTemposDesvio[PROTOBUF_UNMAR_INI.indice])
            });
        }

        try {
            writerEsta.close();
            for (int idFich=0; idFich < App.matrizGenSizesProfStudent.length ; idFich++) {
                CSVWriter writer = new CSVWriter(new FileWriter(App.utilizarPath + (idFich) + sufixoCSV + App.extensaoCSV), ';');
                //writer.writeNext(new String[]{"NOME/INDICE", "PROFESSORES", "STUDENTS"});
                //writer.writeNext(new String[]{String.valueOf(idFich), String.valueOf(App.matrizGenSizesProfStudent[idFich][0]), String.valueOf(App.matrizGenSizesProfStudent[idFich][1])});
                //writer.writeNext(new String[]{});
                writer.writeNext(new String[]{
                        "REPETIÇÃO",
                        "SEED",
                        "GENERATOR",
                        "XML: MARSHAL",
                        "XML: SIZE",
                        "XML: UNMARSHAL",
                        "XML GZIP: MARSHAL",
                        "XML GZIP: SIZE",
                        "XML GZIP: UNMARSHAL",
                        "PROTOBUF: MARSHAL",
                        "PROTOBUF: INI DATA STRUCTURES (MARSHAL)",
                        "PROTOBUF: SIZE",
                        "PROTOBUF: UNMARSHAL",
                        "PROTOBUF: INI DATA STRUCTURES (UNMARSHAL)"
                });

                writer.writeNext(new String[]{
                        "",
                        "",
                        "Tempo (ms)",
                        "Tempo (ms)",
                        "Tamanho (KB)",
                        "Tempo (ms)",
                        "Tempo (ms)",
                        "Tamanho (KB)",
                        "Tempo (ms)",
                        "Tempo (ms)",
                        "Tempo (ms)",
                        "Tamanho (KB)",
                        "Tempo (ms)",
                        "Tempo (ms)"
                });

                for (int idRepeticao = 0; idRepeticao < App.totalRepeticoes; idRepeticao++) {
                    writer.writeNext(new String[]{
                            String.valueOf(idRepeticao+1),
                            (App.utilizarDiferentesSeeds ? String.valueOf(App.listaSeeds[idRepeticao]) : String.valueOf(App.seed)),
                            String.valueOf(App.estatisticasTempos[idFich][GENERATOR.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][XML_MARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTamanhos[idFich][XML_SIZE.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][XML_UNMARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][XML_GZIP_MARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTamanhos[idFich][XML_GZIP_SIZE.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][XML_GZIP_UNMARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][PROTOBUF_MARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][PROTOBUF_MAR_INI.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTamanhos[idFich][PROTOBUF_SIZE.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][PROTOBUF_UNMARSHAL.indice][idRepeticao]),
                            String.valueOf(App.estatisticasTempos[idFich][PROTOBUF_UNMAR_INI.indice][idRepeticao])
                    });
                }
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void runTeste(long seed, int idRepeticao) {
        System.out.println("\n### GENERATOR ###");
        int fileIndex = 0;
        List<Generator> generatorList = new ArrayList<>();
        for(long[] sizesProfStudent: App.matrizGenSizesProfStudent) {
            generatorList.add(gen(fileIndex++, sizesProfStudent[0], sizesProfStudent[1], seed, idRepeticao));
        }

        List<ProfList> proflists = new ArrayList<>(0);

        try {
            if (App.testarXML) {
                System.out.println("\n### XML ###");
                runXML(generatorList, proflists, idRepeticao);
            }

            if (App.testarXMLGzip) {
                System.out.println("\n### XML GZIP ###");
                runXMLGzip(generatorList, proflists, idRepeticao);
            }

            if (App.testarProtobuf) {
                System.out.println("\n### PROTOBUF ###");
                runProtobuf(generatorList, proflists, idRepeticao);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Generator gen(int fileIndex, long sizeProf, long sizeStudent, long seed, int idRepeticao) {
        long start = System.currentTimeMillis();
        Generator gen = new Generator(sizeProf, sizeStudent, seed);
        gen.run();
        long end = System.currentTimeMillis();

        System.out.println("\t" + fileIndex + ". sizeProf=" + sizeProf + ", sizeStudent=" + sizeStudent + ", Seed=" + seed + ": Generating took " + (end - start) + "ms");
        App.estatisticasTempos[fileIndex][GENERATOR.indice][idRepeticao] = (end - start);

        return gen;
    }


    public static void runXML(List<Generator> gens, List<ProfList> proflists, int idRepeticao) throws JAXBException, IOException {
        int fileIndex = 0;
        proflists.clear();

        System.out.println("XML - Marshal:");
        for (Generator generator : gens) {
            proflists.add(marshalXML(generator, fileIndex++, idRepeticao));
        }

        System.out.println("\nXML - UnMarshal:");
        for (fileIndex = 0; fileIndex < App.matrizGenSizesProfStudent.length; fileIndex++) {
            System.out.println(proflists.get(fileIndex).equals(unMarshalXML(fileIndex, idRepeticao)));
        }
    }


    public static void runXMLGzip(List<Generator> gens, List<ProfList> proflists, int idRepeticao) throws JAXBException, IOException {
        int fileIndex = 0;
        proflists.clear();

        System.out.println("XML Gzip - Marshal:");
        for (Generator generator : gens) {
            proflists.add(marshalXMLGzip(generator, fileIndex++, idRepeticao));
        }

        System.out.println("\nXML Gzip - UnMarshal:");
        for (fileIndex = 0; fileIndex < App.matrizGenSizesProfStudent.length; fileIndex++) {
            System.out.println(proflists.get(fileIndex).equals(unMarshalXMLGzip(fileIndex, idRepeticao)));
        }
    }


    public static void runProtobuf(List<Generator> gens, List<ProfList> proflists, int idRepeticao) throws IOException {
        int fileIndex = 0;
        proflists.clear();

        System.out.println("Protobuf - Marshal:");
        for (Generator generator : gens) {
            proflists.add(marshalProtobuf(generator, fileIndex++, idRepeticao));
        }

        System.out.println("\nProtobuf - UnMarshal:");
        for (fileIndex = 0; fileIndex < App.matrizGenSizesProfStudent.length; fileIndex++) {
            System.out.println(proflists.get(fileIndex).equals(unMarshalProtobuf(fileIndex, idRepeticao)));
        }
    }


    public static jakarta.xml.bind.Marshaller rotinaJaxbMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ProfList.class, Professor.class, Student.class, Pessoa.class);
        jakarta.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return jaxbMarshaller;
    }


    public static Unmarshaller rotinaJaxbUnmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ProfList.class, Professor.class, Student.class, Pessoa.class);
        return jaxbContext.createUnmarshaller();
    }


    public static ProfList marshalXML(Generator gen, int fileIndex, int idRepeticao) throws JAXBException {
        ProfList obj = new ProfList(gen.getProfs());

        long start = System.currentTimeMillis();
        jakarta.xml.bind.Marshaller jaxbMarshaller = rotinaJaxbMarshaller();
        File file = new File(App.utilizarPath + fileIndex + App.extensaoXML);
        jaxbMarshaller.marshal(obj, file);
        long end = System.currentTimeMillis();

        System.out.println("\t" + fileIndex + App.extensaoXML + ": marshalling took " + (end - start) + "ms");
        App.estatisticasTempos[fileIndex][XML_MARSHAL.indice][idRepeticao] = (end - start);
        App.estatisticasTamanhos[fileIndex][XML_SIZE.indice][idRepeticao] = file.length()/1024;

        return obj;
    }


    public static ProfList marshalXMLGzip(Generator gen, int fileIndex, int idRepeticao) throws JAXBException, IOException {
        ProfList obj = new ProfList(gen.getProfs());

        long start = System.currentTimeMillis();
        jakarta.xml.bind.Marshaller jaxbMarshaller = rotinaJaxbMarshaller();
        File file = new File(App.utilizarPath + fileIndex + App.extensaoXMLGzip);
        GZIPOutputStream outputStream = new GZIPOutputStream(Files.newOutputStream(file.toPath()));
        jaxbMarshaller.marshal(obj, outputStream);
        outputStream.close();
        long end = System.currentTimeMillis();

        System.out.println("\t" + fileIndex + App.extensaoXMLGzip + ": Marshalling compressed took " + (end - start) + "ms");
        App.estatisticasTempos[fileIndex][XML_GZIP_MARSHAL.indice][idRepeticao] = (end - start);
        App.estatisticasTamanhos[fileIndex][XML_GZIP_SIZE.indice][idRepeticao] = file.length()/1024;

        return obj;
    }


    public static ProfList marshalProtobuf(Generator gen, int fileIndex, int idRepeticao) throws IOException {
        ProfList obj = new ProfList(gen.getProfs());

        long start = System.currentTimeMillis();
        ProtoProfList.Builder protoProfList = ProtoProfList.newBuilder();
        Timestamp timestampRegistrationDate;
        Timestamp timestampBday;
        ProtoStudent.Builder protoStudent;
        ProtoProfessor.Builder protoProfessor;

        for (Professor professor: obj.getprofessor()) {
            protoProfessor = ProtoProfessor.newBuilder();
            timestampBday = new Timestamp(professor.getBday().getTime());

            protoProfessor.setUuid(professor.getUuid());
            protoProfessor.setName(professor.getName());
            protoProfessor.setPhoneNum(professor.getPhoneNum());
            protoProfessor.setAddr(professor.getAddr());
            protoProfessor.setBday(com.google.protobuf.Timestamp.newBuilder().setSeconds(timestampBday.getTime()).setNanos(timestampBday.getNanos()).build());

            for(Student student : professor.getStudent()) {
                protoStudent = ProtoStudent.newBuilder();
                timestampBday = new Timestamp(student.getBday().getTime());
                timestampRegistrationDate = new Timestamp(student.getRegistrationDate().getTime());

                protoStudent.setAddr(student.getAddr());
                protoStudent.setBday(com.google.protobuf.Timestamp.newBuilder().setSeconds(timestampBday.getTime()).setNanos(timestampBday.getNanos()).build());
                protoStudent.setGender(student.getGender().toString());
                protoStudent.setName(student.getName());
                protoStudent.setPhoneNum(student.getPhoneNum());
                protoStudent.setUuid(student.getUuid());
                protoStudent.setProfuuid(student.getProfuuid());
                protoStudent.setRegistrationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(timestampRegistrationDate.getTime()).setNanos(timestampRegistrationDate.getNanos()));

                protoProfessor.addStudent(protoStudent.build());
            }
            protoProfList.addProfessor(protoProfessor.build());
        }
        long end = System.currentTimeMillis();
        long tempoInicializarEstruturas = (end - start);

        start = System.currentTimeMillis();
        File file = new File(App.utilizarPath + fileIndex + App.extensaoProtobuf);
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        protoProfList.build().writeTo(outputStream);
        outputStream.close();
        end = System.currentTimeMillis();
        long tempoMarshal = (end - start);

        System.out.println("\t" + fileIndex + App.extensaoProtobuf + ": Marshalling took " + (tempoMarshal) + "ms & Inicializar estruturas de dados demorou " + (tempoInicializarEstruturas) + "ms");
        App.estatisticasTempos[fileIndex][PROTOBUF_MARSHAL.indice][idRepeticao] = tempoMarshal;
        App.estatisticasTempos[fileIndex][PROTOBUF_MAR_INI.indice][idRepeticao] = tempoInicializarEstruturas;
        App.estatisticasTamanhos[fileIndex][PROTOBUF_SIZE.indice][idRepeticao] = file.length()/1024;

        return obj;
    }


    public static ProfList unMarshalXML(int fileIndex, int idRepeticao) throws JAXBException, IOException {

        long start = System.currentTimeMillis();
        Unmarshaller jaxbUnmarshaller = rotinaJaxbUnmarshaller();
        InputStream inputStream = Files.newInputStream(Paths.get(App.utilizarPath + fileIndex + App.extensaoXML));
        ProfList obj = (ProfList) jaxbUnmarshaller.unmarshal(inputStream);
        inputStream.close();
        long end = System.currentTimeMillis();

        System.out.print("\t" + fileIndex + App.extensaoXML + ": Unmarshalling took " + (end - start) + "ms | Match: ");
        App.estatisticasTempos[fileIndex][XML_UNMARSHAL.indice][idRepeticao] = (end - start);

        return obj;
    }

    public static ProfList unMarshalXMLGzip(int fileIndex, int idRepeticao) throws JAXBException, IOException {

        long start = System.currentTimeMillis();
        Unmarshaller jaxbUnmarshaller = rotinaJaxbUnmarshaller();
        GZIPInputStream inputStream = new GZIPInputStream(Files.newInputStream(Paths.get(App.utilizarPath + fileIndex + App.extensaoXMLGzip)));
        ProfList obj = (ProfList) jaxbUnmarshaller.unmarshal(inputStream);
        inputStream.close();
        long end = System.currentTimeMillis();

        System.out.print("\t" + fileIndex + App.extensaoXMLGzip + ": Unmarshalling compressed took " + (end - start) + "ms | Match: ");
        App.estatisticasTempos[fileIndex][XML_GZIP_UNMARSHAL.indice][idRepeticao] = (end - start);

        return obj;
    }

    public static ProfList unMarshalProtobuf(int fileIndex, int idRepeticao) throws IOException {

        long start = System.currentTimeMillis();
        File file = new File(App.utilizarPath + fileIndex + App.extensaoProtobuf);
        InputStream inputStream = Files.newInputStream(file.toPath());
        ProtoProfList protoProfList = ProtoProfList.parseFrom(inputStream);
        inputStream.close();
        long end = System.currentTimeMillis();
        long tempoUnMarshal = (end - start);

        start = System.currentTimeMillis();
        ProfList obj;
        ArrayList <Student> students;
        Student student;
        Professor professor;
        ArrayList <Professor> professores = new ArrayList<>();
        Timestamp timestampBday, timestampRegist;

        for (ProtoProfessor protoProfessor : protoProfList.getProfessorList()) {
            students = new ArrayList<>();
            for (ProtoStudent protoStudent: protoProfessor.getStudentList()) {
                timestampBday = new Timestamp(protoStudent.getBday().getSeconds());
                timestampBday.setNanos(protoStudent.getBday().getNanos());
                timestampRegist = new Timestamp(protoStudent.getRegistrationDate().getSeconds());
                timestampRegist.setNanos(protoStudent.getRegistrationDate().getNanos());
                student = new Student(
                        protoStudent.getUuid(),
                        protoStudent.getName(),
                        protoStudent.getPhoneNum(),
                        protoStudent.getAddr(),
                        new Date(timestampBday.getTime()),
                        protoStudent.getProfuuid(),
                        protoStudent.getGender().charAt(0),
                        new Date(timestampRegist.getTime())
                );
                students.add(student);
            }
            timestampBday = new Timestamp(protoProfessor.getBday().getSeconds());
            timestampBday.setNanos(protoProfessor.getBday().getNanos());
            professor = new Professor(
                    protoProfessor.getUuid(),
                    protoProfessor.getName(),
                    protoProfessor.getPhoneNum(),
                    protoProfessor.getAddr(),
                    new Date(timestampBday.getTime()),
                    students
            );
            professores.add(professor);
        }
        obj = new ProfList(professores);
        end = System.currentTimeMillis();
        long tempoInicializarEstruturas = (end - start);

        System.out.print("\t" + fileIndex + App.extensaoProtobuf + ": Unmarshalling took " + (tempoUnMarshal) + "ms & Inicializar estruturas dados demorou " + (tempoInicializarEstruturas) + "ms | Match: ");
        App.estatisticasTempos[fileIndex][PROTOBUF_UNMARSHAL.indice][idRepeticao] = tempoUnMarshal;
        App.estatisticasTempos[fileIndex][PROTOBUF_UNMAR_INI.indice][idRepeticao] = tempoInicializarEstruturas;

        return obj;
    }
}
