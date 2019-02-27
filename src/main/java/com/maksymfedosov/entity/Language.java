package com.maksymfedosov.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "language")
    private List<Developer> developers = new ArrayList<>();

    public void addDeveloper(Developer developer) {
        developers.add(developer);
        developer.setLanguage(this);
    }

    public void removeDeveloper(Developer developer) {
        developers.remove(developer);
        developer.setLanguage(null);
    }

}
