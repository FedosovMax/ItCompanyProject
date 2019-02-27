package com.maksymfedosov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany
    @JoinTable(name = "customer_project",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> customerProjects = new HashSet<>();

    public void addProject(Project project){
        customerProjects.add(project);
        project.getCustomers().add(this);
    }

    public void removeProject(Project project){
        customerProjects.remove(project);
        project.getCustomers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return 33;
    }

}
