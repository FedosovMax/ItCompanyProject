package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Language;
import com.maksymfedosov.util.EntityManagerUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;

public class LanguageDaoImpl implements LanguageDao {

    EntityManagerUtil emUtil;

    public LanguageDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.emUtil = new EntityManagerUtil(entityManagerFactory);
    }

    @Override
    public void save(Language language) {
        Objects.requireNonNull(language);
        emUtil.performWithinTx(entityManager -> entityManager.persist(language));
    }

    @Override
    public Language findById(int id) {
        return emUtil.performWithinTxWithReturning(entityManager -> entityManager.find(Language.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Language> findAll() {
        return emUtil.performWithinTxWithReturning(entityManager ->
                entityManager
                        .createQuery("select l from Language l join fetch l.developers").getResultList());
    }

    @Override
    public void remove(Language language) {
        Objects.requireNonNull(language);
        emUtil.performWithinTx(entityManager -> {
            Language managedLanguage = entityManager.merge(language);
            entityManager.remove(managedLanguage);
        });
    }


}
