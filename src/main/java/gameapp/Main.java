package gameapp;



import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Management manage = new Management();
        manage.setEarnings();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bajs");
        EntityManager em = emf.createEntityManager();
        Query what = em.createQuery("SELECT d.companyId FROM Developer d");
        List<Integer> ids = what.getResultList();
        Developer.idBank.addAll(ids);
        manage.updateReleaseBank();
        manage.updateGameBank();

        Menu meny = new Menu();
        while(true) {
            meny.mainMenu();
        }
    }
}
