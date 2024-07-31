package Eads.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Oglas {
    public Oglas() {
    }

    public Oglas(String name, Double price, LocalDate date, List<Category> categories, String description, String city,User user,String imagePath) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.categories = categories;
        this.description=description;
        this.city=city;
        this.creator=user;
        this.imagePath=imagePath;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Double price;

    private LocalDate date;

    private String city;

    private String imagePath;

    @ManyToMany
    private List<Category> categories;

    @ManyToOne
    private User creator;



    @Column(length = 10000)
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
