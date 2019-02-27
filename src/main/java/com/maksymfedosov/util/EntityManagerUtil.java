package com.maksymfedosov.util;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityManagerUtil {

    private EntityManagerFactory entityManagerFactory;

    public EntityManagerUtil (EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public void performWithinTx(Consumer<EntityManager> entityManagerConsumer){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            entityManagerConsumer.accept(entityManager);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }finally {
            entityManager.close();
        }
    }

    public <T> T performWithinTxWithReturning(Function<EntityManager, T> entityManagerTFunction){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            T result = entityManagerTFunction.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }finally {
            entityManager.close();
        }
    }

    public <T> T readWithinTxWithSession(Function<EntityManager, T> entityManagerConsumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.unwrap(Session.class).setDefaultReadOnly(true);
        entityManager.getTransaction().begin();
        try {
            T result = entityManagerConsumer.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Transaction is rolled back.", e);
        } finally {
            entityManager.close();
        }

    }


}
