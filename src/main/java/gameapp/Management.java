package gameapp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
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

    //newGame är helt förändrad
    public void newGame() {
        EntityManager em = emf.createEntityManager();;
        int devID = inputDevId();
        Developer dev = em.find(Developer.class, devID);
        em.getTransaction().begin();
        Game spel = new Game(dev);
        System.out.print("Name of game: ");
        spel.setName(scan.nextLine());
        System.out.print("Price of game: ");
        spel.setPrice(scanDouble());
        em.persist(spel);
        em.getTransaction().commit();
        em.close();
        addIDtoBank(spel);
    }

    private void addIDtoBank(Game spel) {
        Game.idBank.add(spel.getId());
    }

    public void newDeveloper() {
        //Earnings är tänkt att autogenereras istället
        EntityManager em = emf.createEntityManager();
        System.out.print("Developer: ");
        String name = scan.nextLine();
        int id = generateId();
        while(true) {
            if(Developer.idBank.contains(id)) {
                id = generateId();
            } else { break;}
        }
        Developer.idBank.add(id);
        //Konstruktor ändrad
        Developer dev = new Developer(id, name);
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    private int generateId() {
        double min = Math.ceil(1000);
        double max = Math.floor(9999);
        return (int)Math.round(Math.floor(Math.random() * (max - min) + min));

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
            double price = scanDouble();
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
        //Detta bör auto genereras istället
        //System.out.println("2. Earnings");
        System.out.println("0. Return to main");
        int choice = scanInt();
        if (choice==1) {
            System.out.print("New name: ");
            String name = scan.nextLine();
            dev.setDeveloperName(name);
        } /*else if (choice==2) {
            System.out.print("New earnings: ");
            String earnings = scan.nextLine();
            dev.setEarnings(earnings);
        } */else {return;}
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    public int inputDevId() {
        //Ifall inga utvecklare finns
        if (Developer.idBank.size()==0) {
            System.out.println("No developers available");
            return 0;
        }
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
        if (id==0 || gem==0) {
            System.out.println("Developer or game not found. Returning to main");
            return;
        }
        Game spel = em.find(Game.class, gem);
        Developer dev = em.find(Developer.class, id);
        em.getTransaction().begin();
        spel.setDev(dev);
        dev.getGames().add(spel);
        em.getTransaction().commit();
        em.close();
    }

    public int inputGameId() {
        if (Game.idBank.size()==0) {
            System.out.println("No games available");
            return 0;
        }
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
    //ny metod här
    private double scanDouble() {
        double scanned;
        while(true) {
            try {
                scanned=scan.nextDouble();
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
        Game gem;
        if (dev.getGames().size()>0) {
            while(true) {
                gem = em.find(Game.class, inputGameId());
                //Kan man göra såhär? Kanske en equals metod behövs
                if(gem.getDev().equals(dev)) break;
                else {
                    System.out.println("Game is not made by selected developer. Please try again.");
                }
            }
        } else {
            System.out.println("Developer does not have any registered games. Returning to main menu");
            return;
        }
        gem.setDev(null);
        dev.getGames().remove(gem);
        em.getTransaction().begin();
        em.merge(gem);
        em.merge(dev);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteDev() {
        /*
        Hela denna metod funkar inte längre
        EntityManager em = emf.createEntityManager();
        int id = inputDevId();
        Developer dev = em.find(Developer.class, id);
        TypedQuery<Game> giveAll = em.createQuery("SELECT g FROM Game g", Game.class);
        List<Game> allGames = giveAll.getResultList();
        em.getTransaction().begin();
        allGames.stream().filter(g->g.getDev().contains(dev)).forEach(g->g.getDev().remove(dev));
        em.remove(dev);
        em.getTransaction().commit();
        em.close();*/
    }
    //Ny metod här
    public void newRelease() {
        EntityManager em = emf.createEntityManager();
        int gameID;
        while (true) {
            gameID = inputGameId();
            if(gameID!=0) {
                break;
            } else {
                System.out.println("No games to base release on. Please create game first");
                return;
            }

        }
        Game spel =em.find(Game.class, gameID);
        //Här skulle det behöva säkerställas att användaren matar in rätt format på datumet
        System.out.println("Time to input your release date. Please inout only numerical data.");
        System.out.print("Year of release: ");
        int year = scanInt();
        System.out.print("Month of release: ");
        int month = scanInt();
        System.out.print("Day of release: ");
        int day = scanInt();
        //LocalDate date = LocalDate.of(year, month, day);
        Date date = Date.valueOf(LocalDate.of(year, month, day));
        System.out.print("Country: ");
        String country = scan.nextLine();
        System.out.print("Units sold: ");
        int sold = scanInt();
        //Detta kan leda till Exception tror jag
        LocalRelease release = new LocalRelease(date, country, sold, spel);
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(release);
        spel.addRelease(release);
        trans.commit();
        em.close();

    }

    public void deleteRelease() {
        System.out.println("TBA!!!");
    }

    public void showReleases() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT loco FROM LocalRelease loco");
        List<LocalRelease> content = what.getResultList();
        System.out.println("<Displaying all releases>");
        for (LocalRelease loco : content) {
            System.out.println(loco);
        }
        System.out.println("<End of list>\n");
        em.close();
    }

    public void showReleasesByGame(Game spel) {
        System.out.println("<Displaying all releases for Game: " + spel.getName() + ">");
        spel.getReleases().forEach(System.out::println);
        System.out.println("<End of list>\n");
    }

    public void showReleasesByDev(Developer dev) {

    }

    public void showReleasesByID() {
        System.out.println("TBA!!!");
    }
}
