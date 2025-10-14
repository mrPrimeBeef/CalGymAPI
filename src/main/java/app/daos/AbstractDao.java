package app.daos;

import app.config.HibernateConfig;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public abstract class AbstractDao<T, I> implements IDAO<T, I> {
    protected final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    protected final Class<T> entityClass;

    protected AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e) {
            throw new DaoException("Error in create(): " + t.getClass());
        }
    }

    public T findById(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            T entity = em.find(entityClass, id);
            if (entity == null) {
                throw new DaoException("Error in finding " + entityClass + " with id: " + id);
            }
            return entity;
        } catch (Exception e) {
            throw new DaoException("Error in finding " + entityClass + " with id: " + id);
        }
    }

    public List<T> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT t FROM " + entityClass.getSimpleName() + " t";
            return em.createQuery(jpql, entityClass).getResultList();
        } catch (Exception e) {
            throw new DaoException("Error in finding all of :" + entityClass);
        }
    }

    public T update(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T mergedT = em.merge(t);
            em.getTransaction().commit();
            return mergedT;
        } catch (Exception e) {
            throw new DaoException("Error in updating :" + t);
        }
    }

    public void delete(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "DELETE FROM " + entityClass.getSimpleName() + " t WHERE t.id = :id";
            em.getTransaction().begin();
            em.createQuery(jpql)
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Error in delting " + entityClass + " with id: " + id);
        }
    }
}