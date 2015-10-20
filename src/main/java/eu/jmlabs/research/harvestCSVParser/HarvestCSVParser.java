package eu.jmlabs.research.harvestCSVParser;

import eu.jmlabs.research.harvestCSVParser.entity.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Mark0
 * Created: 19/10/15
 * Updated: 19/10/15
 */
public class HarvestCSVParser {

    private static Logger log = LoggerFactory.getLogger(HarvestCSVParser.class.getName());

    //CSV file header
    private static final String[] FILE_HEADER_MAPPING = {"Date", "Project", "Project Code",
            "Task", "Notes", "Hours", "First Name", "Last Name", "Department", "Employee?"};

    //define attributes
    private static final String DATE = "Date";
    private static final String PROJECT = "Project";
    private static final String PROJECT_CODE = "Project Code";
    private static final String TASK = "Task";
    private static final String NOTES = "Notes";
    private static final String HOURS = "Hours";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String DEPARTMENT = "Department";
    private static final String EMPLOYEE = "Employee?";

    public static void main(String[] args) {
        Map<String, HarvestUserEntity> users = new HashMap<String, HarvestUserEntity>();
        Map<String, HarvestTaskEntity> tasks = new HashMap<String, HarvestTaskEntity>();
        Map<String, HarvestDepartmentEntity> departments = new HashMap<String, HarvestDepartmentEntity>();
        Map<String, HarvestProjectEntity> projects = new HashMap<String, HarvestProjectEntity>();

        // read filename with
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CSV filename: ");
        String filename = scanner.nextLine();
        try {

            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

            CSVParser parser = new CSVParser(new FileReader(filename), csvFileFormat);

            //Get a list of CSV file records
            List<CSVRecord> csvRecords = parser.getRecords();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConn");
            EntityManager em = emf.createEntityManager();
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);

                // header: Date,Project,Project Code,Task,Notes,Hours,First Name,Last Name,Department,Employee?
                System.out.println(record.get(DATE) + ", " + record.get(PROJECT) + ", " + record.get(PROJECT_CODE) + ", " +
                record.get(TASK) + ", " + record.get(NOTES) + ", " + record.get(HOURS) + ", " + record.get(FIRST_NAME) + ", " +
                record.get(LAST_NAME) + ", " + record.get(DEPARTMENT) + ", " + record.get(EMPLOYEE));

                //check data about user
                HarvestUserEntity userEntity = null;
                String developer = record.get(FIRST_NAME).trim() + " " + record.get(LAST_NAME).trim();
                if(users.containsKey(developer)) {
                    userEntity = users.get(developer);
                } else {
                    em.getTransaction().begin();
                    userEntity = new HarvestUserEntity();
                    userEntity.setFirstName(record.get(FIRST_NAME).trim());
                    userEntity.setLastName(record.get(LAST_NAME).trim());
                    if(record.get(EMPLOYEE).trim().toLowerCase().equals("yes")) {
                        userEntity.setEmployee((byte) 1);
                    } else {
                        userEntity.setEmployee((byte) 0);
                    }

                    em.persist(userEntity);
                    em.getTransaction().commit();
                    users.put(developer, userEntity);
                }

                //check data about department
                HarvestDepartmentEntity departmentEntity = null;
                String department = record.get(DEPARTMENT).trim();
                if(departments.containsKey(department)) {
                    departmentEntity = departments.get(department);
                } else {
                    em.getTransaction().begin();
                    departmentEntity = new HarvestDepartmentEntity();
                    departmentEntity.setName(department);
                    em.persist(departmentEntity);
                    em.getTransaction().commit();
                    departments.put(department, departmentEntity);
                }

                //check data about Task
                HarvestTaskEntity taskEntity = null;
                String taskName = record.get(TASK).trim();
                if(tasks.containsKey(taskName)) {
                    taskEntity = tasks.get(taskName);
                } else {
                    em.getTransaction().begin();
                    taskEntity = new HarvestTaskEntity();
                    taskEntity.setName(taskName);
                    em.persist(taskEntity);
                    em.getTransaction().commit();
                    tasks.put(taskName, taskEntity);
                }

                //check data about Project
                HarvestProjectEntity projectEntity = null;
                String project = record.get(PROJECT).trim();
                if(projects.containsKey(project)) {
                    projectEntity = projects.get(project);
                } else {
                    em.getTransaction().begin();
                    projectEntity = new HarvestProjectEntity();
                    projectEntity.setName(project);
                    if(!record.get(PROJECT_CODE).trim().equals("")) {
                        projectEntity.setCode(record.get(PROJECT_CODE));
                    }
                    em.persist(projectEntity);
                    em.getTransaction().commit();
                    projects.put(project, projectEntity);
                }

                //store worklog
                em.getTransaction().begin();
                HarvestWorklogEntity worklog = new HarvestWorklogEntity();
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
                DateTime dt = formatter.parseDateTime(record.get(DATE));
                worklog.setCreated(new Timestamp(dt.getMillis()));
                worklog.setHours(Double.parseDouble(record.get(HOURS).replace(",", ".")));
                worklog.setNotes(record.get(NOTES));
                worklog.setHarvestDepartment(departmentEntity);
                worklog.setHarvestProject(projectEntity);
                worklog.setHarvestTask(taskEntity);
                worklog.setHarvestUser(userEntity);
                em.persist(worklog);
                em.getTransaction().commit();
            }
            em.close();
            emf.close();

            System.out.println("All records: " + csvRecords.size());
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist!");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
