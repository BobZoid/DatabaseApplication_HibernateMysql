package gameapp;


import javax.persistence.*;
import javax.swing.*;
import java.util.Scanner;

public class Menu {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bajs");
    Scanner scan = new Scanner(System.in);
    Management manage = new Management();

    public void mainMenu() {
        Object[] options = {"View", "Add", "Edit", "Delete", "Exit"};
        int n = JOptionPane.showOptionDialog(null, "What do you want to do today?",
                "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (n) {
            case 0:
                showData();
                break;
            case 1:
                addData();
                break;
            case 2:
                editData();
                break;
            case 3: deleteData();
                break;
            case 4: System.exit(0);
            default: System.out.println("Incorrect input");
        }
    }


    private void deleteData() {
        Object[] options = {"Delete Game", "Delete Release", "Delete Developer", "Return to main menu"};
        int n = JOptionPane.showOptionDialog(null, "What do you want to delete?",
                "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (n) {
            case 0:
                manage.deleteGame();
                break;
            case 2:
                manage.deleteDev();
                break;
            case 1:
                manage.deleteRelease();
                break;
            case 4:
                return;
        }
    }

    private void editData() {
        Object[] options = {"Edit Game", "Edit Release", "Edit Developer", "Connect Game to Developer",
                "Connect Release to Game", "Return to main menu"};
        int n = JOptionPane.showOptionDialog(null, "What do you want to edit?",
                "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (n) {
            case 0:
                manage.editGame();
                break;
            case 1:
                manage.editRelease();
                break;
            case 2:
                manage.editDeveloper();
                break;
            case 3:
                //manage.connectToDeveloper();
                break;
            case 4:
                //manage.connectToGame();
            case 5:
                return;
        }
    }

    private void addData() {
        Object[] options = {"Add new Game", "Add new Developer", "Add new release", "Return to main menu"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to add?",
                "Add data", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                manage.newGame();
                break;
            case 1:
                manage.newDeveloper();
                break;
            case 2:
                manage.newRelease();
            case 3:
                return;
        }
    }

    private void showData() {
        Object[] options = {"View All", "View Releases", "View Developers", "View Games", "Return to main menu"};
        int n = JOptionPane.showOptionDialog(null, "What do you want to view?",
                "View Data", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (n) {
            case 0:
                manage.showAll();
                break;
            case 3:
                manage.showGames();
                break;
            case 2:
                manage.showDevelopers();
                break;
            case 1:
                showRelease();
                break;
            case 4:
                return;
            default:
                System.out.println("Incorrect input");
        }
    }

    private void showRelease() {
        EntityManager em = emf.createEntityManager();

        Object[] options = {"View All", "View Releases based on Game", "View Releases based on Developer", "View Releases based on ID", "Return to main menu"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to view?",
                "View Releases", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                manage.showReleases();
                break;
            case 1:
                manage.showReleasesByGame();
                break;
            case 2:
                manage.showReleasesByDev();
                break;
            case 3:
                //manage.showReleasesByID();
                break;
            case 4:
                return;
        }

    }
}
