package gameapp;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "team_martin", name = "local_release")
public class LocalRelease {
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


    public LocalRelease(Date release, String country, int unitsSold, Game spel) {
        this.release = release;
        this.country = country;
        this.unitsSold = unitsSold;
        game=spel;
    }

    public LocalRelease() {

    }

    public void Game(Game spel) {
        game=spel;
    }

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
        String gameInfo=", game=";
        if(game!=null) gameInfo+=game.getName();
        else gameInfo+="!!NOT CONNECTED TO GAME!!";
        return "LocalRelease{" +
                "releaseID=" + releaseID +
                ", release=" + release +
                ", country='" + country + '\'' +
                ", unitsSold=" + unitsSold +
                '}';
    }
}