package gameapp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(schema = "team_martin", name = "developer")
public class Developer {
    static ArrayList<Integer> idBank = new ArrayList<>();
    @Id
    private int companyId;
    private String developerName;
    //detta är en double nu
    private double earnings;
    @OneToMany (cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "dev")
    private Set<Game> games = new TreeSet<>();


    public Developer(int id, String developerName) {
        companyId = id;
        this.developerName = developerName;
        this.earnings = earnings;
        Developer.idBank.add(id);
    }


    public Developer() {

    }


    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        String end= "";
        for (Game gem: games) {
            end+=gem.getName() + ", ";
        }
        return "\nCompanyID: " + companyId +
                "\nDeveloperName: " + developerName +
                "\nEarnings: " + earnings +
                "\nGames: " + end;
    }
}
