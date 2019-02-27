package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Developer;
import com.maksymfedosov.entity.Project;

import java.util.List;

public interface ProjectDao {

    void save(Project project);

    Project findById(int id);

    List findAll();

    void remove(Project project);

    Object getSalarySumInProjectById(int projectId);

    List<Developer> getDevelopersListOfProjectById(int projectId);

    List<Object[]> getAllProjectsWithDevelopersCount();
}
