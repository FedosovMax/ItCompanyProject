package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Developer;
import com.maksymfedosov.entity.Project;
import com.maksymfedosov.util.EntityManagerUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {

    EntityManagerUtil emUtil;

    public ProjectDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.emUtil = new EntityManagerUtil(entityManagerFactory);
    }

    @Override
    public void save(Project project) {
        emUtil.performWithinTx(entityManager -> entityManager.persist(project));
    }

    @Override
    public Project findById(int id) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.find(Project.class, id));
    }

    @Override
    public List findAll() {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.createQuery("select p from Project p").getResultList());
    }

    @Override
    public void remove(Project project) {
        emUtil.performWithinTx(entityManager -> {
            Project managedProject = entityManager.merge(project);
            entityManager.remove(managedProject);
        });
    }


    @Override
    public Long getSalarySumInProjectById(int projectId) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager
                .find(Project.class, projectId)
                .getDevelopers()
                .stream()
        .collect(Collectors.summarizingLong(Developer::getSalary))
                .getSum());
    }

    @Override
    public List<Developer> getDevelopersListOfProjectById(int projectId) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager
        .find(Project.class, projectId)
        .getDevelopers()
                .stream()
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public List<Object[]> getAllProjectsWithDevelopersCount() {
        return emUtil.performWithinTxWithReturning(entityManager ->
                entityManager
                        .createNativeQuery("SELECT p.start_time, p.project_name, count(dp.developer_Id)\n" +
                                "FROM project p LEFT JOIN developer_project dp \n" +
                                "ON dp.project_id = p.project_id\n" +
                                "GROUP BY p.project_name").getResultList());
    }
}
