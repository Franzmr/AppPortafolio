/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Entity.Comuna;
import modelo.Entity.Asociacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Sede;

/**
 *
 * @author Francisco
 */
public class SedeJpaController implements Serializable {

    public SedeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sede sede) throws PreexistingEntityException, Exception {
        if (sede.getAsociacionList() == null) {
            sede.setAsociacionList(new ArrayList<Asociacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna comunaIdcomuna = sede.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna = em.getReference(comunaIdcomuna.getClass(), comunaIdcomuna.getIdcomuna());
                sede.setComunaIdcomuna(comunaIdcomuna);
            }
            List<Asociacion> attachedAsociacionList = new ArrayList<Asociacion>();
            for (Asociacion asociacionListAsociacionToAttach : sede.getAsociacionList()) {
                asociacionListAsociacionToAttach = em.getReference(asociacionListAsociacionToAttach.getClass(), asociacionListAsociacionToAttach.getIdasociacion());
                attachedAsociacionList.add(asociacionListAsociacionToAttach);
            }
            sede.setAsociacionList(attachedAsociacionList);
            em.persist(sede);
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getSedeList().add(sede);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            for (Asociacion asociacionListAsociacion : sede.getAsociacionList()) {
                Sede oldSedeIdsedeOfAsociacionListAsociacion = asociacionListAsociacion.getSedeIdsede();
                asociacionListAsociacion.setSedeIdsede(sede);
                asociacionListAsociacion = em.merge(asociacionListAsociacion);
                if (oldSedeIdsedeOfAsociacionListAsociacion != null) {
                    oldSedeIdsedeOfAsociacionListAsociacion.getAsociacionList().remove(asociacionListAsociacion);
                    oldSedeIdsedeOfAsociacionListAsociacion = em.merge(oldSedeIdsedeOfAsociacionListAsociacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSede(sede.getIdsede()) != null) {
                throw new PreexistingEntityException("Sede " + sede + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sede sede) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede persistentSede = em.find(Sede.class, sede.getIdsede());
            Comuna comunaIdcomunaOld = persistentSede.getComunaIdcomuna();
            Comuna comunaIdcomunaNew = sede.getComunaIdcomuna();
            List<Asociacion> asociacionListOld = persistentSede.getAsociacionList();
            List<Asociacion> asociacionListNew = sede.getAsociacionList();
            List<String> illegalOrphanMessages = null;
            for (Asociacion asociacionListOldAsociacion : asociacionListOld) {
                if (!asociacionListNew.contains(asociacionListOldAsociacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asociacion " + asociacionListOldAsociacion + " since its sedeIdsede field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (comunaIdcomunaNew != null) {
                comunaIdcomunaNew = em.getReference(comunaIdcomunaNew.getClass(), comunaIdcomunaNew.getIdcomuna());
                sede.setComunaIdcomuna(comunaIdcomunaNew);
            }
            List<Asociacion> attachedAsociacionListNew = new ArrayList<Asociacion>();
            for (Asociacion asociacionListNewAsociacionToAttach : asociacionListNew) {
                asociacionListNewAsociacionToAttach = em.getReference(asociacionListNewAsociacionToAttach.getClass(), asociacionListNewAsociacionToAttach.getIdasociacion());
                attachedAsociacionListNew.add(asociacionListNewAsociacionToAttach);
            }
            asociacionListNew = attachedAsociacionListNew;
            sede.setAsociacionList(asociacionListNew);
            sede = em.merge(sede);
            if (comunaIdcomunaOld != null && !comunaIdcomunaOld.equals(comunaIdcomunaNew)) {
                comunaIdcomunaOld.getSedeList().remove(sede);
                comunaIdcomunaOld = em.merge(comunaIdcomunaOld);
            }
            if (comunaIdcomunaNew != null && !comunaIdcomunaNew.equals(comunaIdcomunaOld)) {
                comunaIdcomunaNew.getSedeList().add(sede);
                comunaIdcomunaNew = em.merge(comunaIdcomunaNew);
            }
            for (Asociacion asociacionListNewAsociacion : asociacionListNew) {
                if (!asociacionListOld.contains(asociacionListNewAsociacion)) {
                    Sede oldSedeIdsedeOfAsociacionListNewAsociacion = asociacionListNewAsociacion.getSedeIdsede();
                    asociacionListNewAsociacion.setSedeIdsede(sede);
                    asociacionListNewAsociacion = em.merge(asociacionListNewAsociacion);
                    if (oldSedeIdsedeOfAsociacionListNewAsociacion != null && !oldSedeIdsedeOfAsociacionListNewAsociacion.equals(sede)) {
                        oldSedeIdsedeOfAsociacionListNewAsociacion.getAsociacionList().remove(asociacionListNewAsociacion);
                        oldSedeIdsedeOfAsociacionListNewAsociacion = em.merge(oldSedeIdsedeOfAsociacionListNewAsociacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = sede.getIdsede();
                if (findSede(id) == null) {
                    throw new NonexistentEntityException("The sede with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sede;
            try {
                sede = em.getReference(Sede.class, id);
                sede.getIdsede();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sede with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Asociacion> asociacionListOrphanCheck = sede.getAsociacionList();
            for (Asociacion asociacionListOrphanCheckAsociacion : asociacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Asociacion " + asociacionListOrphanCheckAsociacion + " in its asociacionList field has a non-nullable sedeIdsede field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Comuna comunaIdcomuna = sede.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getSedeList().remove(sede);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            em.remove(sede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sede> findSedeEntities() {
        return findSedeEntities(true, -1, -1);
    }

    public List<Sede> findSedeEntities(int maxResults, int firstResult) {
        return findSedeEntities(false, maxResults, firstResult);
    }

    private List<Sede> findSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sede.class));
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

    public Sede findSede(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sede.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sede> rt = cq.from(Sede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
