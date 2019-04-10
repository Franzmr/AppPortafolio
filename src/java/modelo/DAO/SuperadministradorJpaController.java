/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Superadministrador;

/**
 *
 * @author Francisco
 */
public class SuperadministradorJpaController implements Serializable {

    public SuperadministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Superadministrador superadministrador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(superadministrador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuperadministrador(superadministrador.getIdadministrador()) != null) {
                throw new PreexistingEntityException("Superadministrador " + superadministrador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Superadministrador superadministrador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            superadministrador = em.merge(superadministrador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = superadministrador.getIdadministrador();
                if (findSuperadministrador(id) == null) {
                    throw new NonexistentEntityException("The superadministrador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Superadministrador superadministrador;
            try {
                superadministrador = em.getReference(Superadministrador.class, id);
                superadministrador.getIdadministrador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The superadministrador with id " + id + " no longer exists.", enfe);
            }
            em.remove(superadministrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Superadministrador> findSuperadministradorEntities() {
        return findSuperadministradorEntities(true, -1, -1);
    }

    public List<Superadministrador> findSuperadministradorEntities(int maxResults, int firstResult) {
        return findSuperadministradorEntities(false, maxResults, firstResult);
    }

    private List<Superadministrador> findSuperadministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Superadministrador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Superadministrador findSuperadministrador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Superadministrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuperadministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Superadministrador> rt = cq.from(Superadministrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
