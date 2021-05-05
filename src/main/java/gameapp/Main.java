package gameapp;


import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bajs");
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT d.companyId FROM Developer d");
        Query who = em.createQuery("SELECT g.id FROM Game g");
        List<Integer> alsoIds = who.getResultList();
        List<Integer> ids = what.getResultList();
        em.close();
        Developer.idBank.addAll(ids);
        Game.idBank.addAll(alsoIds);

        Menu meny = new Menu();
        while(true) {
            meny.mainMenu();
        }


    }
}
