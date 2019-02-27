package com.maksymfedosov.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode (of = "project_id")
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "project_id")
    private int project_id;

    @Column (name = "project_name")
    private String project_name;

    @Column (name = "start_time")
    private Time start_time;

    @Column (name = "cost")
    private int cost;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "developerProjects")
    private Set<Developer> developers = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "customerProjects")
    private Set<Customer> customers = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "companyProjects")
    private Set<Company> companies = new HashSet<>();

}
