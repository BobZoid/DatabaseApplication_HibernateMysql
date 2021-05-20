package gameapp;




import javax.persistence.*;
import javax.swing.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class Management {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bajs");

    public void deleteGame() {
        EntityManager em = emf.createEntityManager();
        int id = inputGameId();
        Game spel = em.find(Game.class, id);
        em.getTransaction().begin();
        Query allDevs = em.createQuery("SELECT d FROM Developer d");
        List<Developer> allD = allDevs.getResultList();
        for (Developer dev: allD) {
            final Game[] z = new Game[1];
            dev.getGames().stream().filter(x->x.getId()==id).forEach(x-> z[0] =x);
            dev.getGames().remove(z[0]);
        }
        Query allReleases = em.createQuery("SELECT r FROM LocalRelease r");
        List<LocalRelease> allR = allReleases.getResultList();
        for (LocalRelease loco: allR) {
            final Game[] z = new Game[1];
            if (loco.getGame().getId()==id) {
                loco.setGame(null);

            }
        }
        em.remove(spel);
        em.getTransaction().commit();
        em.close();
        Game.idBank.remove(spel.getId());
    }

    private int inputGameId() {
        if (Game.idBank.size()==0) {
            JOptionPane.showMessageDialog(null, "No games in database", "ERROR", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        int id;
        showGames();
        while (true) {
            String idString = JOptionPane.showInputDialog(null, "Input ID");
            id = stringToInt(idString);
            if(Game.idBank.contains(id)) {
                break;
            } else if (id==0) {
                showGames();
            }else {
                JOptionPane.showMessageDialog(null, "No such ID in database. Please try again", "ERROR", JOptionPane.ERROR_MESSAGE);
                showGames();
            }
        }
        return id;
    }

    private int inputDevId() {
        if (Developer.idBank.size()==0) {
            JOptionPane.showMessageDialog(null, "No developers in database", "ERROR", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        int id;
        showDevelopers();
        while (true) {
            String idString = JOptionPane.showInputDialog(null, "Input ID");
            id = stringToInt(idString);
            if(Developer.idBank.contains(id)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "No such ID in database. Please try again", "ERROR", JOptionPane.ERROR_MESSAGE);
                showDevelopers();
            }
        }
        return id;
    }

    private int inputReleaseId() {
        if (LocalRelease.idBank.size()==0) {
            JOptionPane.showMessageDialog(null, "No releases in database", "ERROR", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        int id;
        showReleases();
        while (true) {
            String idString = JOptionPane.showInputDialog(null, "Input ID");
            id = stringToInt(idString);
            if (LocalRelease.idBank.contains(id)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "No such ID in database. Please try again", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return id;
    }

    public void showReleases() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT loco FROM LocalRelease loco");
        List<LocalRelease> content = what.getResultList();
        String releases = "";
        for (LocalRelease loco: content) {
            releases += loco + "\n";
        }
        em.close();
        JOptionPane.showMessageDialog(null, releases, "Releases in database", JOptionPane.PLAIN_MESSAGE);
    }

    public void showGames() {
        EntityManager em = emf.createEntityManager();;
        Query what = em.createQuery("SELECT g FROM Game g");
        List<Game> content = what.getResultList();
        String games ="";
        for (Game g: content) {
            games += g + "\n;";
        }
        JOptionPane.showMessageDialog(null, games, "Games in database", JOptionPane.PLAIN_MESSAGE);
    }

    private int stringToInt(String idString) {
        int num=-1;
        try{
            num = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please input only numerical data", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return num;
    }

    public double stringToDouble(String idString) {
        double num=-1;
        try{
            num = Double.parseDouble(idString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please input only numerical data", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return num;
    }


    public void showAll() { showDevelopers(); showGames(); showReleases();}

    public void showDevelopers() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT d FROM Developer d");
        List<Developer> content = what.getResultList();
        String devs ="";
        for (Developer d: content) {
            devs += d + "\n;";
        }
        JOptionPane.showMessageDialog(null, devs, "Developers in database", JOptionPane.PLAIN_MESSAGE);
        em.close();
    }

    public void deleteDev() {
        EntityManager em = emf.createEntityManager();
        int id = inputDevId();
        if (id==0) {return;}
        Developer dev = em.find(Developer.class, id);
        TypedQuery<Game> give = em.createQuery("SELECT g  FROM Game g", Game.class);
        List<Game> allGames = give.getResultList();
        em.getTransaction().begin();
        allGames.stream().filter(g->g.getDev().equals(dev)).forEach(g->g.setDev(null));
        em.remove(dev);
        em.getTransaction().commit();
        em.close();
        Developer.idBank.remove(dev.getCompanyId());
    }

    public void deleteRelease() {
        EntityManager em = emf.createEntityManager();
        int id =inputReleaseId();
        if (id==0) return;
        LocalRelease loco = em.find(LocalRelease.class, id);
        em.getTransaction().begin();
        TypedQuery<Game> where = em.createQuery("SELECT g FROM Game g", Game.class);
        List<Game> allGames = where.getResultList();
        for (Game g: allGames) {
            if (g.getReleases().contains(loco)) {
                g.getReleases().remove(loco);
            }
        }
        em.remove(loco);
        em.getTransaction().commit();
        em.close();
        LocalRelease.idBank.remove(loco.getReleaseID());
    }

    public void editGame() {
        int id = inputGameId();
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, id);
        Object[] options = {"Name", "Price", "Return to main"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Edit Game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                String name = JOptionPane.showInputDialog(null, "New name");
                game.setName(name);
                break;
            case 1:
                int newPrice;
                while (true) {
                    String priceString = JOptionPane.showInputDialog(null, "New price");
                    newPrice = stringToInt(priceString);
                    if (newPrice > 0) break;
                }
                game.setPrice(newPrice);
                break;
            case 2:
                return;
        }
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        em.close();

    }

    public void editRelease() {
        int id = inputReleaseId();
        EntityManager em = emf.createEntityManager();
        LocalRelease loco = em.find(LocalRelease.class, id);
        Object[] options = {"Release Date", "Country", "Units Sold", "Return to main"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Edit Release",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                loco.setRelease(inputDate());
                break;
            case 1:
                loco.setCountry(JOptionPane.showInputDialog(null, "New country"));
                break;
            case 2:
                int sold;
                while (true) {
                    String temp = JOptionPane.showInputDialog(null, "Units sold");
                    sold = stringToInt(temp);
                    if (sold<0) {
                        JOptionPane.showMessageDialog(null, "Incorrect input", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else break;
                } loco.setUnitsSold(sold);
                break;
            case 3:
                return;
        }
        em.getTransaction().begin();
        em.persist(loco);
        em.getTransaction().commit();
        em.close();
    }

    private Date inputDate() {
        int year;
        int month;
        int day;
        while (true) {
            String temp = JOptionPane.showInputDialog(null, "Year");
            year = stringToInt(temp);
            if (year<1000 || year>2050) {
                JOptionPane.showMessageDialog(null, "Please inout a year between 1000 and 2050 with four digits", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {break;}
        }

        while (true) {
            String temp = JOptionPane.showInputDialog(null, "Month");
            month = stringToInt(temp);
            if (month<1 || month>12) {
                JOptionPane.showMessageDialog(null, "Please input a month between 1 and 12 with only digits", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {break;}
        }

        while (true) {
            String temp = JOptionPane.showInputDialog(null, "Day");
            day = stringToInt(temp);
            if (day<1 || day>31) {
                JOptionPane.showMessageDialog(null, "Please inout a date between 1 and 31 with only digits", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {break;}
        }
        Date date = Date.valueOf(LocalDate.of(year, month, day));
        return date;
    }

    public void editDeveloper() {
        EntityManager em = emf.createEntityManager();
        int id = inputDevId();
        Developer dev = em.find(Developer.class, id);
        Object[] options = {"Company name", "Return to main"};
        int choice = JOptionPane.showOptionDialog(null, "What would you like to edit?", "Edit Developer",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                String name = JOptionPane.showInputDialog(null, "New name");
                dev.setDeveloperName(name);
                break;
            default:
                return;
        }
        dev.calculateEarnings();
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    public void newGame() {
        EntityManager em = emf.createEntityManager();
        int devId = inputDevId();
        Developer dev = em.find(Developer.class, devId);
        Game game = new Game (dev);
        String name = JOptionPane.showInputDialog(null, "Name");
        game.setName(name);
        double price;
        while (true) {
            String temp = JOptionPane.showInputDialog(null, "Price");
            price = stringToDouble(temp);
            if (price<0) {
                JOptionPane.showMessageDialog(null, "Incorrect input", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else break;
        }
        game.setPrice(price);
        dev.getGames().add(game);
        dev.calculateEarnings();
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        em.close();
        updateGameBank();
    }


    public void newDeveloper() {
        EntityManager em = emf.createEntityManager();
        String name = JOptionPane.showInputDialog(null, "Name");
        int id = generateId();
        Developer dev = new Developer(id, name);
        Developer.idBank.add(id);
        dev.calculateEarnings();
        em.getTransaction().begin();
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    private int generateId() {
        int id;
        while (true) {
            double min = Math.ceil(1000);
            double max = Math.floor(9999);
            id = (int)Math.round(Math.floor(Math.random() * (max - min) + min));
            if (!Developer.idBank.contains(id)) break;
        }
        return id;
    }

    public void newRelease() {
        EntityManager em = emf.createEntityManager();
        int gameId = inputGameId();
        if (gameId==0) {
            JOptionPane.showMessageDialog(null, "No games to base release on. Returning to main", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Game game = em.find(Game.class, gameId);
        Date release = inputDate();
        String country = JOptionPane.showInputDialog(null, "Country");
        int sold;
        while (true) {
            String temp = JOptionPane.showInputDialog(null, "Units sold");
            sold = stringToInt(temp);
            if (sold>-1) {break;} else {
                JOptionPane.showMessageDialog(null, "Incorrect input", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        LocalRelease loco = new LocalRelease(release, country, sold, game);
        em.getTransaction().begin();
        em.persist(loco);
        em.getTransaction().commit();
        em.close();
        updateReleaseBank();
    }


    public void updateGameBank() {
        EntityManager em = emf.createEntityManager();
        Query who = em.createQuery("SELECT g.id FROM Game g");
        List<Integer> ids = who.getResultList();
        Game.idBank.addAll(ids);
    }

    public void updateReleaseBank() {
        EntityManager em = emf.createEntityManager();
        Query who = em.createQuery("SELECT r.releaseID FROM LocalRelease r");
        List<Integer> ids = who.getResultList();
        LocalRelease.idBank.addAll(ids);
    }

    public void showReleasesByGame() {
        EntityManager em = emf.createEntityManager();
        int id = inputGameId();
        Game game = em.find(Game.class, id);
        String name = "Releases for " + game.getName();
        String releases = "";
        for (LocalRelease loco: game.getReleases()) {
            releases += loco + "\n";
        }
        em.close();
        JOptionPane.showMessageDialog(null, releases, name, JOptionPane.PLAIN_MESSAGE);
    }

    public void showReleasesByDev() {
        int id = inputDevId();
        EntityManager em = emf.createEntityManager();
        Developer dev = em.find(Developer.class, id);
        String name = "Releases for " + dev.getDeveloperName();
        String releases = "";
        for (Game game: dev.getGames()) {
            for (LocalRelease loco: game.getReleases()) {
                releases += loco + "\n";
            }
        }
        em.close();
        JOptionPane.showMessageDialog(null, releases, name, JOptionPane.PLAIN_MESSAGE);
    }

    public void showReleasesByID() {
        int id = inputReleaseId();
        EntityManager em = emf.createEntityManager();
        String name = "Release with ID: " + id;
        LocalRelease loco = em.find(LocalRelease.class, id);
        String releases = loco.toString();
        JOptionPane.showMessageDialog(null, releases, name, JOptionPane.PLAIN_MESSAGE);

    }

    public void connectToDev() {
        EntityManager em = emf.createEntityManager();
        int gameId = inputGameId();
        int devId = inputDevId();
        if (gameId<1 || devId<1) {
            JOptionPane.showMessageDialog(null, "Developer or Game not found. Returning to main", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Game game = em.find(Game.class, gameId);
        Developer dev = em.find(Developer.class, devId);
        game.setDev(dev);
        dev.getGames().add(game);
        em.getTransaction().begin();
        em.persist(game);
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    public void disconnectFromDev() {
        int id = inputGameId();
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, id);
        int devId = game.getDev().getCompanyId();
        Developer dev = em.find(Developer.class, devId);
        game.setDev(null);
        dev.getGames().remove(game);
        em.getTransaction().begin();
        em.persist(game);
        em.persist(dev);
        em.getTransaction().commit();
        em.close();
    }

    public void connectToGame() {
        EntityManager em = emf.createEntityManager();
        int releaseId = inputReleaseId();
        int gameId = inputGameId();
        if (gameId<1 || releaseId<1) {
            JOptionPane.showMessageDialog(null, "Release or Game not found. Returning to main", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Game game = em.find(Game.class, gameId);
        LocalRelease loco = em.find(LocalRelease.class, releaseId);
        loco.setGame(game);
        game.getReleases().add(loco);
        em.getTransaction().begin();
        em.persist(loco);
        em.persist(game);
        em.getTransaction().commit();
        em.close();
    }

    public void disconnectFromGame() {
        EntityManager em = emf.createEntityManager();
        int releaseId = inputReleaseId();
        LocalRelease loco = em.find(LocalRelease.class, releaseId);
        int gameId = loco.game.getId();
        Game game = em.find(Game.class, gameId);
        loco.setGame(null);
        game.getReleases().remove(loco);
        em.getTransaction().begin();
        em.persist(loco);
        em.persist(game);
        em.getTransaction().commit();
        em.close();
    }

    public void setEarnings() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT d FROM Developer d");
        List<Developer> devs = what.getResultList();
        em.getTransaction().begin();
        for (Developer dev: devs) {
            dev.calculateEarnings();
            em.persist(dev);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void averageEarnings() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT dev FROM Developer dev");
        List<Developer> devs = what.getResultList();
        int devAmount=0;
        double totalEarnings = 0;
        for (Developer dev: devs) {
            dev.calculateEarnings();
            devAmount++;
            totalEarnings += dev.getEarnings();
        }
        Double temp = totalEarnings / devAmount;
        Double average = temp.doubleValue();
        DecimalFormat formatter = new DecimalFormat("##0.0######");
        JOptionPane.showMessageDialog(null, "Average profit for all Developers is: " + formatter.format(average),
                "Average profit", JOptionPane.PLAIN_MESSAGE);
        em.close();
    }

    public void averageAmountOfGames() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT dev FROM Developer dev");
        List<Developer> devs = what.getResultList();
        double games=0;
        double alldevs=0;
        for (Developer dev: devs) {
            games+=dev.getGames().size();
            alldevs++;
        }
        double average = games/alldevs;
        JOptionPane.showMessageDialog(null, "Average amount of games for all Developers is: " + average,
                "Average Games released", JOptionPane.PLAIN_MESSAGE);
        em.close();
    }

    public void maxMinProfit() {
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT dev FROM Developer dev");
        List<Developer> devs = what.getResultList();
        Double max=devs.get(0).getEarnings();
        Double min=devs.get(0).getEarnings();
        for (Developer dev: devs) {
            if (dev.getEarnings()>max) max=dev.getEarnings();
            if (dev.getEarnings()<min) min=dev.getEarnings();
        }
        DecimalFormat formatter = new DecimalFormat("##0.0######");
        String answer = "The most profittable developer has earned: " + formatter.format(max) +
                "\nThe least profittable developer has earned: " + formatter.format(min);
        JOptionPane.showMessageDialog(null, answer,
                "Most and Least profittable Developer", JOptionPane.PLAIN_MESSAGE);
        em.close();
    }
}
