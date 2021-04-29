package gameapp;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Game {
    static ArrayList<Integer> idBank = new ArrayList<>();
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String price;
    @ManyToOne (cascade = CascadeType.PERSIST)
    private Developer dev;

    public Game(Developer dev) {
        this.dev = dev;
    }

    public Game(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Game() {

    }

    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
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
        String end = "";
        if (dev!=null) end = "Developer: " + dev.getDeveloperName();
            return "\nID: " + id +
                    "\nName: " + name +
                    "\nPrice: " + price +
                    "\n" + end;

        }
}
