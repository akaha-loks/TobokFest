package kg.akahagroup.tobokfest.model;

import jakarta.persistence.*;
import kg.akahagroup.tobokfest.enums.Oblasts;

@Table(name = "venues")
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Oblasts oblast;
    private String city;
    private String address;
    private int capacity;

    public Venue() {
    }

    public Venue(String name, Oblasts oblast, String city, String address, int capacity) {
        this.name = name;
        this.oblast = oblast;
        this.city = city;
        this.address = address;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Oblasts getOblast() {
        return oblast;
    }

    public void setOblast(Oblasts oblast) {
        this.oblast = oblast;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
