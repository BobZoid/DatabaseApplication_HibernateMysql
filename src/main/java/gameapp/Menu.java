package gameapp;


import javax.swing.*;

public class Menu {
    Management manage = new Management();

    public void mainMenu() {
        Object[] options = {"View", "Add", "Edit", "Delete", "Statistics", "Exit"};
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
            case 3:
                deleteData();
                break;
            case 4:
                statistics();
                break;
            case 5:
                manage.setEarnings();
                System.exit(0);
            default: System.out.println("Incorrect input");
        }
    }

    private void statistics() {
        Object[] options = {"Developer", "Game", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Statistics for",
                "Statistics", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                devStatistics();
                break;
            case 1:
                gameStatistics();
                break;
            case 2:
                return;
        }
    }

    private void gameStatistics() {
        Object[] options = {"Average profit for Games", "Average amount of Releases",
                "Average Price for Game", "Percentage of sales for each Release", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Statistics for Games",
                "Statistics", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                manage.averageEarningsGame();
                break;
            case 1:
                manage.averageReleases();
                break;
            case 2:
                manage.averagePrice();
                break;
            case 3:
                manage.percentageSales();
                break;
            case 4:
                return;
        }
    }

    private void devStatistics() {
        Object[] options = {"Average profit", "Average amount of Games", "Highest and Lowest profit", "Percentage of total profit from each game", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Statistics for",
                "Statistics for Developers", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                manage.averageEarningsDev();
                break;
            case 1:
                manage.averageAmountOfGames();
                break;
            case 2:
                manage.maxMinProfit();
                break;
            case 3:
                manage.percentageFromEachGame();
                break;
            case 4:
                return;
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
        Object[] options = {"Edit Game", "Edit Release", "Edit Developer", "Connect/Disconnect", "Return to main menu"};
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
                connectDisconnect();
                break;
            case 4:
                return;
        }
    }

    private void connectDisconnect() {
        Object[] options = {"Connect Game to Developer", "Connect Release to Game", "Disconnect Game from Developer",
                "Disconnect Release from Game", "Return to main"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to add?",
                "Add data", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                manage.connectToDev();
                break;
            case 1:
                manage.connectToGame();
                break;
            case 2:
                manage.disconnectFromDev();
                break;
            case 3:
                manage.disconnectFromGame();
            case 4:
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
                manage.showReleasesByID();
                break;
            case 4:
                return;
        }

    }
}
