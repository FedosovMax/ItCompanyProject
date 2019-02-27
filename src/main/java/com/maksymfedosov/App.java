package com.maksymfedosov;

import com.maksymfedosov.dao.*;
import com.maksymfedosov.entity.Developer;
import com.maksymfedosov.entity.Language;
import com.maksymfedosov.entity.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    DeveloperDaoImpl developerDao;
    ProjectDaoImpl projectDao;
    LanguageDaoImpl languageDao;

    public App() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        developerDao = new DeveloperDaoImpl(entityManagerFactory);
        projectDao = new ProjectDaoImpl(entityManagerFactory);
        languageDao = new LanguageDaoImpl(entityManagerFactory);

        Scanner scanner = new Scanner(System.in);
        String currentCommand = null;

        do {

            setLanguageToDeveloper(1, 201);

            printMenu();
            currentCommand = scanner.nextLine();

            if (currentCommand.startsWith("salaries_sum")) {
                String[] commandParts = currentCommand.split(" ");
                int projectId = Integer.parseInt(commandParts[1]);
                showSalariesInProject(projectId);
            } else if (currentCommand.startsWith("list_devs_at_project")) {
                String[] commandParts = currentCommand.split(" ");
                int projectId = Integer.parseInt(commandParts[1]);
                showDevelopersFromProjectById(projectId);
            } else if (currentCommand.startsWith("list_devs_language")) {
                showJavaDevelopers();
            } else if (currentCommand.startsWith("list_devs_middle")) {
                showMiddleLevelDevelopers();
            } else if (currentCommand.startsWith("list_projects")) {
                showAllProjects();
            }
        } while (!currentCommand.equals("exit"));
        scanner.close();
    }

    public static void main(String[] args) throws SQLException {
        new App();
    }

    private void printMenu() {
        System.out.println("Insert the command and press Enter:\n" +
                "1. The sum of developer's salaries in the project (salaries_sum <project_id>)\n" +
                "2. List of developers in the particular project (list_devs_at_project <project_id>)\n" +
                "3. List of all java developers (list_devs_language)\n" +
                "4. List of all middle developers (list_devs_middle)\n" +
                "5. List of all projects (list_projects)\n" +
                "6. Выход (exit)\n");
    }

    private void showSalariesInProject(int projectId){
        Object sumOfProject = projectDao.getSalarySumInProjectById(projectId);
        Project salariesProject = projectDao.findById(projectId);
        System.out.println("The name of company: " + salariesProject.getProject_name());
        System.out.println("The id of company: " + salariesProject.getProject_id());
        System.out.println("The sum of salaries in this project is: " + (sumOfProject.toString()));
        System.out.println("\n");
    }

    private void showDevelopersFromProjectById(int projectId){
        List<Developer> developers = projectDao.getDevelopersListOfProjectById(projectId);
        System.out.println("Developers from project " + projectDao.findById(projectId).getProject_name() + " are: ");
        System.out.println("ID | NAME | MIDDLE_NAME | LAST_NAME | AGE | SALARY");
        for (Developer developer:developers) {
            System.out.println(developer.getId() + " | " + developer.getFirstName() + " | "
                    + developer.getMiddleName() + " | " + developer.getLastName() + " | "
                    + developer.getAge() + " | " + developer.getSalary());
        }
        System.out.println("\n");
    }

    private void showJavaDevelopers(){
        List<Developer> developers = developerDao.getAllDevelopersWhichLanguageIsJava(201); //201 is Java language id in the database
        System.out.println("The list of developers which use Java programming language");
        System.out.println("ID | NAME | MIDDLE_NAME | LAST_NAME | AGE | SALARY");
        for (Developer developer:developers) {
            System.out.println(developer.getId() + " | " + developer.getFirstName()
                    + " | " + developer.getMiddleName() + " | " + developer.getLastName() + " | "
                    + developer.getAge() + " | " + developer.getSalary());
        }
        System.out.println("\n");
    }

    private void showMiddleLevelDevelopers(){
        List<Developer> developers = developerDao.getAllDevelopersWithParticularLevel(102); //102 is Middle level id in the database
        System.out.println("Developers with middle level are: ");
        System.out.println("ID | NAME | MIDDLE_NAME | LAST_NAME | AGE | SALARY");
        for (Developer developer:developers) {
            System.out.println(developer.getId() + " | " + developer.getFirstName() + " | "
                    + developer.getMiddleName() + " | " + developer.getLastName() + " | "
                    + developer.getAge() + " | " + developer.getSalary());
        }
        System.out.println("\n");
    }

    private void showAllProjects(){
        List <Object[]> projects = projectDao.getAllProjectsWithDevelopersCount();
        System.out.println("List of projects with developers count");
        System.out.println("CREATION_DATE | PROJECT_NAME | DEVELOPERS_COUNT" );
        for (Object[] object:projects) {
            System.out.println(object[0] + " | " + object[1] + " | " + object[2]);
        }
        System.out.println("\n");
    }

    private void setLanguageToDeveloper(int developerId, int languageId) {
        Developer developer = developerDao.findById(developerId);
        developerDao.setLanguage(developer, languageId);
        System.out.println("The Developer with id: " + developerId + " has language with id: " + languageId);
    }


}
