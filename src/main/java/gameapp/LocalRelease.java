package gameapp;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;

@Entity
@Table(schema = "team_martin", name = "local_release")
public class LocalRelease {
    static HashSet<Integer> idBank = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int releaseID;
    @Column(name = "release_date")
    private Date release;
    private String country;
    @Column(name = "units_sold")
    private int unitsSold;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gameID")
    Game game;


    public LocalRelease(Date release, String country, int unitsSold, Game game) {
        this.release = release;
        this.country = country;
        this.unitsSold = unitsSold;
        this.game =game;
    }

    public LocalRelease() {}

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getCountry() {
        return country;
    }

    public int getReleaseID() {
        return releaseID;
    }

    public void setReleaseID(int releaseID) {
        this.releaseID = releaseID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        String gameInfo;
        if(game!=null) gameInfo=game.getName();
        else gameInfo="!!NOT CONNECTED TO GAME!!";
        return "LocalRelease{" +
                "Game=" + gameInfo +
                ", ID=" + releaseID +
                ", release=" + release.toString() +
                ", country='" + country + '\'' +
                ", unitsSold=" + unitsSold +
                '}' + "\n";
    }
}