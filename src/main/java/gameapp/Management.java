package gameapp;

import javax.persistence.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Management {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bajs");
    Scanner scan = new Scanner(System.in);

    public void showAll() { showGames(); showDevelopers();}

    public void showGames() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT g FROM Game g");
        List<Game> content = what.getResultList();
        System.out.println("<Displaying all games>");
        for (Game g : content) {
            System.out.println(g);
        }
        System.out.println("<End of list>\n");
    }

    public void showDevelopers() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT d FROM Developer d");
        List<Developer> content = what.getResultList();
        System.out.println("<Displaying all developers>");
        for (Developer d : content) {
            System.out.println(d);
        }
        System.out.println("<End of list>\n");
        em.close();
    }

    public void newGame() {
        System.out.print("Title: ");
        String name = scan.nextLine();
        System.out.print("Price: ");
        String price = scan.nextLine();
        Game spel = new Game(name, price);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(spel);
        em.getTransaction().commit();
        em.close();
        addIDtoBank(spel);
    }

    private void addIDtoBank(Game spel) {
        Game.idBank.add(spel.getId());
    }

    public void newDeveloper() {
        System.out.print("Developer: ");
        String name = scan.nextLine();
        System.out.print("Earnings: ");
        String earnings = scan.nextLine();
        int id = generateId();
        while(true) {
            if(Developer.idBank.contains(id)) {
                id = generateId();
            } else { break;}
        }
        Developer dev = new Developer(id, name, earnings);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    private int generateId() {
        double min = Math.ceil(1000);
        double max = Math.floor(9999);
        int id = (int)Math.round(Math.floor(Math.random() * (max - min) + min));
        return id;
    }

    public void editGame() {
        int id = inputGameId();
        EntityManager em = emf.createEntityManager();
        Game spel = em.find(Game.class, id);
        System.out.println(spel);
        System.out.println("\nWhat would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("0. Return to main menu");
        int choice = scanInt();
        if (choice==1) {
            System.out.print("\nEnter new name: ");
            String name = scan.nextLine();
            spel.setName(name);
        } else if (choice == 2) {
            System.out.print("Enter new price: ");
            String price = scan.nextLine();
            spel.setPrice(price);
        } else {return; }
        em.getTransaction().begin();
        em.persist(spel);
        em.getTransaction().commit();
        em.close();
    }

    public void editDeveloper() {
        int id = inputDevId();
        EntityManager em = emf.createEntityManager();
        Developer dev = em.find(Developer.class, id);
        System.out.println(dev);
        System.out.println("\nWhat would you like to edit:");
        System.out.println("1. Company name");
        System.out.println("2. Earnings");
        System.out.println("0. Return to main");
        int choice = scanInt();
        if (choice==1) {
            System.out.print("New name: ");
            String name = scan.nextLine();
            dev.setDeveloperName(name);
        } else if (choice==2) {
            System.out.print("New earnings: ");
            String earnings = scan.nextLine();
            dev.setEarnings(earnings);
        } else {return;}
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    private int inputDevId() {
        int id;
        showDevelopers();
        while (true) {
            System.out.print("\nCompanyID of developer: ");
            id = scanInt();
            if(Developer.idBank.contains(id)) {
                break;
            } else {
                System.out.println("ID does not exist. Please try again\n");
                showDevelopers();
            }
        }
        return id;
    }

    public void connectToDeveloper() {
        EntityManager em = emf.createEntityManager();
        System.out.print("\n ID of game: ");
        int gem = inputGameId();
        System.out.print("\nID of developer: ");
        int id = inputDevId();
        Game spel = em.find(Game.class, gem);
        Developer dev = em.find(Developer.class, id);
        em.getTransaction().begin();
        spel.setDev(dev);
        dev.getGames().add(spel);
        em.getTransaction().commit();
        em.close();
    }

    private int inputGameId() {
        int id;
        showGames();
        while (true) {
            System.out.print("\nID of game: ");
            id = scanInt();
            if(Game.idBank.contains(id)) {
                break;
            } else {
                System.out.println("ID does not exist. Please try again\n");
                showGames();
            }
        }
        return id;
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

    public void deleteGame() {
        int id = inputGameId();
        EntityManager em = emf.createEntityManager();
        Game spel = em.find(Game.class, id);
        em.getTransaction().begin();
        Query all = em.createQuery("SELECT d FROM Developer d");
        List<Developer> allDevs = all.getResultList();
        for (Developer dev: allDevs) {
            final Game[] z = new Game[1];
            dev.getGames().stream().filter(x->x.getId()==id).forEach(x-> z[0] =x);
            dev.getGames().remove(z[0]);
        }
        em.remove(spel);
        em.getTransaction().commit();
        em.close();
    }


    public void findByDev() {
        int id = inputDevId();
        EntityManager em = emf.createEntityManager();
        System.out.println("You have selected: ");
        System.out.println(em.find(Developer.class, id));
        Developer dev = em.find(Developer.class, id);
        System.out.println("<Games made by selected developer>");
        dev.getGames().stream().forEach(System.out::println);

    }

    public void removeGameFromDev() {
        EntityManager em = emf.createEntityManager();
        Developer dev = em.find(Developer.class, inputDevId());
        Game spel = em.find(Game.class, inputGameId());
        dev.getGames().remove(spel);
        spel.setDev(null);
        em.getTransaction().begin();
        em.merge(dev);
        em.getTransaction().commit();
        em.close();
    }
}
