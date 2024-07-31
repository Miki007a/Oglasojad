package Eads.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 4000)
    private String description;

    public Category(String name) {
        this.name=name;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category() {

    }
}
