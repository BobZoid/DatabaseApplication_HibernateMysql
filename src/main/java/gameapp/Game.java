package gameapp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Game {
    static Set<Integer> idBank = new TreeSet<>();
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String price;
    @ManyToMany (cascade = CascadeType.PERSIST, mappedBy = "games")
    private Set<Developer> devs=new TreeSet<>();
    /*Ny variabel, tar den senare
    @OneToMany
    private LocalRelease localRelease; */

    public Game(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Game() {

    }
    /*
    public LocalRelease getReleaseDate() {
        return localRelease;
    }

    public void setReleaseDate(LocalRelease localRelease) {
        this.localRelease = localRelease;
    }
    */
    public Set<Developer> getDev() {
        return devs;
    }

    public void setDev(Set<Developer> devs) {
        this.devs = devs;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

        @Override
        public String toString() {
        String end = "Developer: ";
        if (devs.size()!=0) {
            Developer[] lista = new Developer[devs.size()];
            lista = devs.toArray(lista);
            for(int x=0; x<devs.size(); x++) {
                Developer dev = lista[x];
                end+=dev.getDeveloperName() + ", ";
            }
        }
            return "\nID: " + id +
                    "\nName: " + name +
                    "\nPrice: " + price +
                    "\n" + end;

        }
}
