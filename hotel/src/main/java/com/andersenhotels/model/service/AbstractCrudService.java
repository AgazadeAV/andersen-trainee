package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudService.class);
    private static EntityManagerFactory entityManagerFactory;
    protected final Class<T> entityClass;

    protected AbstractCrudService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            synchronized (AbstractCrudService.class) {
                if (entityManagerFactory == null) {
                    entityManagerFactory = Persistence.createEntityManagerFactory(ConfigManager.getPersistenceUnitName());
                }
            }
        }
        return entityManagerFactory.createEntityManager();
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
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            LOGGER.error("Invalid argument provided for entity creation: {}", entity, e);
            throw new IllegalArgumentException("Invalid argument for entity creation", e);
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            LOGGER.error("Transaction rollback occurred during entity creation: {}", entity, e);
            throw new RollbackException("Transaction rollback during entity creation", e);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.error("Persistence error during entity creation: {}", entity, e);
            throw new PersistenceException("Persistence error during entity creation", e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Optional<T> read(ID id) {
        EntityManager em = getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            LOGGER.info("Entity read by ID {}: {}", id, entity);
            return Optional.ofNullable(entity);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid argument provided for entity read by ID: {}", id, e);
            throw new IllegalArgumentException("Invalid argument for entity read by ID: " + id, e);
        } catch (PersistenceException e) {
            LOGGER.error("Persistence error during entity read by ID: {}", id, e);
            throw new PersistenceException("Persistence error during entity read by ID: " + id, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
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
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            LOGGER.error("Invalid argument provided for entity update: {}", entity, e);
            throw new IllegalArgumentException("Invalid argument for entity update", e);
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            LOGGER.error("Transaction rollback occurred during entity update: {}", entity, e);
            throw new RollbackException("Transaction rollback during entity update", e);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.error("Persistence error during entity update: {}", entity, e);
            throw new PersistenceException("Persistence error during entity update", e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
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
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            LOGGER.error("Entity not found during deletion for ID: {}", id, e);
            throw new EntityNotFoundException("Entity not found for deletion by ID: " + id);
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            LOGGER.error("Transaction rollback during deletion for ID: {}", id, e);
            throw new RollbackException("Transaction rollback during deletion for ID: " + id, e);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.error("Persistence error during deletion for ID: {}", id, e);
            throw new PersistenceException("Persistence error during deletion for ID: " + id, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.error("Persistence error during finding all entities", e);
            throw new PersistenceException("Persistence error during finding all entities", e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean existsById(ID id) {
        EntityManager em = getEntityManager();
        try {
            Integer count = em.createQuery(
                            "SELECT COUNT(e) FROM " + entityClass.getSimpleName() +
                                    " e WHERE e.id = :id", Integer.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid argument provided for existsById check: {}", id, e);
            throw new IllegalArgumentException("Invalid argument for existsById check: " + id, e);
        } catch (PersistenceException e) {
            LOGGER.error("Persistence error during existsById check for ID: {}", id, e);
            throw new PersistenceException("Persistence error during existsById check for ID: " + id, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
