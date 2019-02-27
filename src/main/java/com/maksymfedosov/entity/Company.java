package com.maksymfedosov.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "company")
public class Company{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "company_name")
    private String companyName;

    @Column (name = "company_country")
    private String companyCountry;

    @ManyToMany
    @JoinTable(name = "company_project",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> companyProjects = new HashSet<>();

    public void addProject(Project project){
        companyProjects.add(project);
        project.getCompanies().add(this);
    }

    public void removeProject(Project project){
        companyProjects.remove(project);
        project.getCompanies().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return 32;
    }

}
