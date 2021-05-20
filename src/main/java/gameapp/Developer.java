package gameapp;

import javax.persistence.*;
import java.text.DecimalFormat;
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
    private Double earnings;
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "dev")
    private Set<Game> games = new TreeSet<>();


    public Developer(int id, String developerName) {
        companyId = id;
        this.developerName = developerName;
        Developer.idBank.add(id);
    }


    public Developer() {
    }

    public void calculateEarnings() {
        double earned = 0;
        for (Game spel : games) {
            int sold = 0;
            for (LocalRelease loco : spel.getReleases()) {
                sold += loco.getUnitsSold();
            }
            earned += spel.getPrice() * sold;
        }
        earnings = earned;
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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }


    @Override
    public String toString() {
        calculateEarnings();
        String end = "";
        for (Game gem : games) {
            end += gem.getName() + ", ";
        }
        Double dd = earnings.doubleValue();
        DecimalFormat formatter = new DecimalFormat("##0.0######");
        return "\nCompanyID: " + companyId +
                "\nDeveloperName: " + developerName +
                "\nEarnings: " + formatter.format(dd) +
                "\nGames: " + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        if (companyId != developer.companyId) return false;
        return developerName != null ? developerName.equals(developer.developerName) : developer.developerName == null;
    }

    @Override
    public int hashCode() {
        int result = companyId;
        result = 31 * result + (developerName != null ? developerName.hashCode() : 0);
        return result;
    }
}
