package gameapp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    Scanner scan = new Scanner(System.in);
    Management manage = new Management();
//Uppdaterad MainMenu
    public void mainMenu() {
        System.out.println("\n=================================");
        System.out.println("              Menu               ");
        System.out.println("=================================");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Edit");
        System.out.println("4. Delete");
        System.out.println("\n0.Exit");
        System.out.println("=================================");
        int choice = scanInt();
        switch (choice) {
            case 1:
                showData();
                break;
            case 2: addData();
                break;
            case 3: editData();
                break;
            case 4: deleteData();
                break;
            case 0: System.exit(0);
            default: System.out.println("Incorrect input");
        }
    }
//Uppdaterat Deletemeny. Ett nytt val
    private void deleteData() {
        System.out.println("\n=================================");
        System.out.println("              Delete               ");
        System.out.println("=================================");
        System.out.println("1. Delete game");
        System.out.println("2. Delete Release");
        System.out.println("3. Delete Developer");
        System.out.println("4. Remove game from developer");
        System.out.println("\n0. Return to main menu");
        System.out.println("=================================");
        int choice = scanInt();
        switch (choice) {
            case 1:
                manage.deleteGame();
                break;
            case 4:
                manage.removeGameFromDev();
                break;
            case 3:
                manage.deleteDev();
                break;
            case 2:
                manage.deleteRelease();
                break;
            case 0:
                return;
            default:
                System.out.println("Incorrect input");
        }
    }

    private void editData() {
        System.out.println("\n=================================");
        System.out.println("              Edit               ");
        System.out.println("=================================");
        System.out.println("1. Edit game");
        System.out.println("2. Edit developer");
        System.out.println("3. Connect game to developer");
        System.out.println("\n0. Return to main menu");
        System.out.println("=================================");
        int choice = scanInt();
        switch (choice) {
            case 1:
                manage.editGame();
                break;
            case 2:
                manage.editDeveloper();
                break;
            case 3:
                manage.connectToDeveloper();
                break;
            case 0:
                return;
            default:
                System.out.println("Incorrect input");
        }
    }

    private void addData() {
        System.out.println("\n=================================");
        System.out.println("              Add data               ");
        System.out.println("=================================");
        System.out.println("1. Add new game");
        System.out.println("2. Add new developer");
        System.out.println("3. Add new release");
        System.out.println("\n0. Return to main menu");
        System.out.println("=================================");
        int choice = scanInt();
        switch (choice) {
            case 1:
                manage.newGame();
                break;
            case 2:
                manage.newDeveloper();
                break;
            case 3:
                manage.newRelease();
            case 0:
                return;
            default:
                System.out.println("Incorrect input");
        }
    }

    private void showData() {
        System.out.println("\n=================================");
        System.out.println("            Show Data               ");
        System.out.println("=================================");
        System.out.println("1. View all");
        System.out.println("2. View games");
        System.out.println("3. View developers");
        System.out.println("4. View releases");
        System.out.println("5. View games based on developers");
        System.out.println("\n0.Return to main menu");
        System.out.println("=================================");
        int choice = scanInt();
        switch (choice) {
            case 1:
                manage.showAll();
                break;
            case 2:
                manage.showGames();
                break;
            case 3:
                manage.showDevelopers();
                break;
            case 4:
                showRelease();
                break;
            case 5:
                manage.findByDev();
                break;
            case 0:
                return;
            default:
                System.out.println("Incorrect input");
        }
    }

    private void showRelease() {
        System.out.println("\n=================================");
        System.out.println("            View Releases               ");
        System.out.println("=================================");
        System.out.println("1. View all ");
        System.out.println("2. View releases based on game");
        System.out.println("3. View releases based on developer");
        System.out.println("4. View releases based on release ID");
        System.out.println("\n0.Return to main menu");
        System.out.println("=================================");
        switch (scanInt()) {
            case 1:
                manage.showReleases();
                break;
            case 2:
                manage.showReleasesByGame();
                break;
            case 3:
                manage.showReleasesByDev();
                break;
            case 4:
                manage.showReleasesByID();
                break;
            case 0:
            return;
            default:
                System.out.println("Incorrect input. Returning to main");
                return;
        }

    }

    private int scanInt() {
        int scanned;
        while(true) {
            try {
                scanned=scan.nextInt();
                break;
            } catch(InputMismatchException e) {
                System.out.println("Please input numerical data");
                scan.nextLine();
            }
        }
        scan.nextLine();
        return scanned;
    }
}
