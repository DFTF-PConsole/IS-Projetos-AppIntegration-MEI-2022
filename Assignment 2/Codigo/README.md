# main(String[] args)

```shell
01_names.txt 02_count.txt 03_countActive.txt 04_namesCompleted.txt 05_dataLastYear.txt 06_avgStd.txt 07_avgStdFinished.txt 08_eldestStudent.txt 09_avgProfPerStudent.txt 10_studentPerProf.txt 11_completeDataStudent.txt
```

<br>

<br>

# TAREFAS

Legenda: ❌ - Por Fazer | ⏳ - A Fazer | ✅ - Feito | ❓ - Não Sei

<br>

## Servidor - Requisitos:

### - ✅ Spring Boot Server (12,5%)
- ✅ The server is a legacy application with basic functionality that students must not try
  to enrich, to simplify client-side queries. Server services are, therefore, limited to
  simple CRUD operations;
- ✅ PostgreSQL / Docker / Tabelas / SQL;


### - ✅ Reactor on the Server Side (7,5%)

### - ✅ Logging (5,0%)

<br>

## Cliente - Requisitos:

### - 1 ✅ Names and birthdates of all students (5,0%)

### - 2 ✅ Total number of students (5,0%)

### - 3 ✅ Total number of students that are active (i.e., that have less than 180 credits) (5,0%)

### - 4 ✅ Total number of courses completed for all students, knowing that each course is worth 6 credits. (5,0%)

### - 5 ✅ Data of students that are in the last year of their graduation (i.e., whose credits are at least 120 and less than 180). This list should be sorted, such that students closer to completion should come first. These data do not need to include the professors (5,0%)

### - 6 ✅ Average and standard deviations of all student grades (5,0%)

### - 7 ✅ Average and standard deviations of students who have finished their graduation (with 180 credits) (5,0%)

### - 8 ✅ The name of the eldest student (5,0%)

### - 9 ✅ Average number of professors per student. Note that some professors may not have students and vice-versa (5,0%)

### - 10 ✅ Name and number of students per professor, sorted in descending order (5,0%)

### - 11 ✅ Complete data of all students, by adding the names of their professors (5,0%)

<br>

## Outros:

### - ✅ Emulate network delays

### - ✅ Tolerate network failures
- At least one of the client-side queries should be able to tolerate network failures, by
  retrying up to three times to reconnect, before giving up. Students may create a
  special service on the server and a special query on the client, outside any time
  control, to emulate this case.

### - ✅ Attention to performance, clarity and other details (10,0%)

### - ✅ Report (10,0%)

<br>

