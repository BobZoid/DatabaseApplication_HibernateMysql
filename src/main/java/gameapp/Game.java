package gameapp;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.*;

@Entity
@Table(schema = "team_martin", name = "game")
public class Game {
    static Set<Integer> idBank = new TreeSet<>();
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Double price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "companyid")
    private Developer dev;
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST)
    private Set<LocalRelease> releases = new HashSet<>();

    public Game() {
    }

    public Double getEarnings() {
        int sold=0;
        DecimalFormat formatter = new DecimalFormat("##0.0######");
        for (LocalRelease loco: releases) { sold+= loco.getUnitsSold();}
        Double earned = price*sold;;
        return earned;
    }

    public Game(Developer dev) {
        this.dev = dev;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRelease(LocalRelease release) {
        releases.add(release);
    }

    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }

    public Set<LocalRelease> getReleases() {
        return releases;
    }

    public void setReleases(Set<LocalRelease> releases) {
        this.releases = releases;
    }

    @Override
    public String toString() {
        String dev=", developer=";
        if (this.dev != null) {
            dev +=this.dev.getDeveloperName();
        } else {
            dev += "!!NO DEVELOPER SET!!";}
            return "Game{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price + dev +
                    ", releases=" + releases.size() + '}';
        }
    }
