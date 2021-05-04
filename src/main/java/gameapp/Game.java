package gameapp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    static ArrayList<Integer> idBank = new ArrayList<>();
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String price;
    @ManyToMany (cascade = CascadeType.PERSIST, mappedBy = "games")
    private List<Developer> devs=new ArrayList<>();
    /*Ny variabel, tar den senare
    @OneToMany
    private LocalRelease localRelease; */

    /* Varför har vi ens denna?
    public Game(Developer dev) {
        devs.add(dev);
    }

     */

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
    public List<Developer> getDev() {
        return devs;
    }

    public void setDev(List<Developer> devs) {
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

//Ny toString här
        @Override
        public String toString() {
        String end = "Developer: ";
        if (devs.size()!=0) {
            for(int x=0; x<devs.size(); x++) {
                Developer dev = devs.get(x);
                end+=dev.getDeveloperName() + ", ";
            }
        }
            return "\nID: " + id +
                    "\nName: " + name +
                    "\nPrice: " + price +
                    "\n" + end;

        }
}
