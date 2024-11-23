package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudService.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(ConfigManager.getPersistenceUnitName());

    protected final Class<T> entityClass;

    protected AbstractCrudService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    @Override
    public T create(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            LOGGER.info("Entity created: {}", entity);
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.error("Failed to create entity", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<T> read(ID id) {
        EntityManager em = getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            LOGGER.info("Entity read by ID {}: {}", id, entity);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            LOGGER.error("Failed to read entity by ID {}", id, e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T mergedEntity = em.merge(entity);
            em.getTransaction().commit();
            LOGGER.info("Entity updated: {}", mergedEntity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.error("Failed to update entity", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(ID id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                LOGGER.info("Entity deleted by ID: {}", id);
            } else {
                LOGGER.warn("No entity found to delete by ID: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.error("Failed to delete entity by ID {}", id, e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.error("Failed to find all entities", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(ID id) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            LOGGER.error("Failed to check if entity exists by ID {}", id, e);
            throw e;
        } finally {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
            LOGGER.info("EntityManagerFactory closed successfully.");
        }
    }
}
