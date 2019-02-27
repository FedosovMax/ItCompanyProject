package com.maksymfedosov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "developer")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "middle_name")
    private String middleName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "age")
    private int age;

    @Column (name = "salary")
    private int salary;

    @ManyToOne
    @JoinColumn(name = "skill_level_id")
    private SkillLevel skillLevel;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "developer_project",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> developerProjects = new HashSet<>();

    public void addProject(Project project){
        developerProjects.add(project);
        project.getDevelopers().add(this);
    }

    public void removeProject(Project project){
        developerProjects.remove(project);
        project.getDevelopers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer)) return false;

        Developer developer = (Developer) o;

        return Objects.equals(id, developer.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
