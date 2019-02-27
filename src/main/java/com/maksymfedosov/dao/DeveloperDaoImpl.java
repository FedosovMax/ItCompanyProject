package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Developer;
import com.maksymfedosov.entity.Language;
import com.maksymfedosov.entity.SkillLevel;
import com.maksymfedosov.util.EntityManagerUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DeveloperDaoImpl implements DeveloperDao {

    EntityManagerUtil emUtil;

    public DeveloperDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.emUtil = new EntityManagerUtil(entityManagerFactory);
    }

    @Override
    public void save(Developer developer) {
        Objects.requireNonNull(developer);
        emUtil.performWithinTx(entityManager -> entityManager.persist(developer));
    }

    @Override
    public Developer findById(int id) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.find(Developer.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Developer> findAll() {
        return emUtil.performWithinTxWithReturning(entityManager ->
                entityManager
                        .createQuery("select d from Developer d join fetch d.developerProjects").getResultList());
    }

    @Override
    public void remove(Developer developer) {
        Objects.requireNonNull(developer);
        emUtil.performWithinTx(entityManager -> {
            Developer managedDeveloper = entityManager.merge(developer);
            entityManager.remove(managedDeveloper);
        });
    }

    @Override
    public void setLanguage(Developer developer, int languageId) {
        emUtil.performWithinTx(entityManager -> entityManager.createNativeQuery("UPDATE developer SET language_id =:languageId WHERE developer.id =:developerId")
                .setParameter("languageId", languageId)
                .setParameter("developerId", developer.getId())
        .executeUpdate());
    }

    @Override
    public void setLevel(Developer developer, int levelId) {
        emUtil.performWithinTx(entityManager -> entityManager.createQuery("UPDATE Developer d SET d.skillLevel = :levelId WHERE d.id =:id")
                .setParameter("levelId", levelId)
                .setParameter("id", developer.getId())
        .executeUpdate());
    }

    @Override
    public List<Developer> getAllDevelopersWhichLanguageIsJava(int languageId) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.find(Language.class, languageId).getDevelopers()
                .stream()
        .filter(developer -> developer.getLanguage().getId() == languageId)
        .collect(Collectors.toList()));
    }

    @Override
    public List<Developer> getAllDevelopersWithParticularLevel(int skillLevelId) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.find(SkillLevel.class, skillLevelId).getDevelopers()
        .stream()
                .filter(Objects::nonNull)
        .collect(Collectors.toList()));
    }


}
