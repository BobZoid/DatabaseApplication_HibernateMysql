package gameapp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Developer {
    static ArrayList<Integer> idBank = new ArrayList<>();
    @Id
    int companyId;
    String developerName;
    String earnings;


    public Developer(int id, String developerName, String earnings) {
        companyId = id;
        this.developerName = developerName;
        this.earnings = earnings;
        Developer.idBank.add(id);
    }

    public Developer() {

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

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "companyId=" + companyId +
                ", developerName='" + developerName + '\'' +
                ", earnings='" + earnings + '\'' +
                '}';
    }
}
