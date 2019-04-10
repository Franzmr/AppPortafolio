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
import modelo.Entity.Asociacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Carrera;

/**
 *
 * @author Francisco
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) throws PreexistingEntityException, Exception {
        if (carrera.getAsociacionList() == null) {
            carrera.setAsociacionList(new ArrayList<Asociacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asociacion> attachedAsociacionList = new ArrayList<Asociacion>();
            for (Asociacion asociacionListAsociacionToAttach : carrera.getAsociacionList()) {
                asociacionListAsociacionToAttach = em.getReference(asociacionListAsociacionToAttach.getClass(), asociacionListAsociacionToAttach.getIdasociacion());
                attachedAsociacionList.add(asociacionListAsociacionToAttach);
            }
            carrera.setAsociacionList(attachedAsociacionList);
            em.persist(carrera);
            for (Asociacion asociacionListAsociacion : carrera.getAsociacionList()) {
                Carrera oldCarreraIdcarreraOfAsociacionListAsociacion = asociacionListAsociacion.getCarreraIdcarrera();
                asociacionListAsociacion.setCarreraIdcarrera(carrera);
                asociacionListAsociacion = em.merge(asociacionListAsociacion);
                if (oldCarreraIdcarreraOfAsociacionListAsociacion != null) {
                    oldCarreraIdcarreraOfAsociacionListAsociacion.getAsociacionList().remove(asociacionListAsociacion);
                    oldCarreraIdcarreraOfAsociacionListAsociacion = em.merge(oldCarreraIdcarreraOfAsociacionListAsociacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrera(carrera.getIdcarrera()) != null) {
                throw new PreexistingEntityException("Carrera " + carrera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getIdcarrera());
            List<Asociacion> asociacionListOld = persistentCarrera.getAsociacionList();
            List<Asociacion> asociacionListNew = carrera.getAsociacionList();
            List<String> illegalOrphanMessages = null;
            for (Asociacion asociacionListOldAsociacion : asociacionListOld) {
                if (!asociacionListNew.contains(asociacionListOldAsociacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asociacion " + asociacionListOldAsociacion + " since its carreraIdcarrera field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Asociacion> attachedAsociacionListNew = new ArrayList<Asociacion>();
            for (Asociacion asociacionListNewAsociacionToAttach : asociacionListNew) {
                asociacionListNewAsociacionToAttach = em.getReference(asociacionListNewAsociacionToAttach.getClass(), asociacionListNewAsociacionToAttach.getIdasociacion());
                attachedAsociacionListNew.add(asociacionListNewAsociacionToAttach);
            }
            asociacionListNew = attachedAsociacionListNew;
            carrera.setAsociacionList(asociacionListNew);
            carrera = em.merge(carrera);
            for (Asociacion asociacionListNewAsociacion : asociacionListNew) {
                if (!asociacionListOld.contains(asociacionListNewAsociacion)) {
                    Carrera oldCarreraIdcarreraOfAsociacionListNewAsociacion = asociacionListNewAsociacion.getCarreraIdcarrera();
                    asociacionListNewAsociacion.setCarreraIdcarrera(carrera);
                    asociacionListNewAsociacion = em.merge(asociacionListNewAsociacion);
                    if (oldCarreraIdcarreraOfAsociacionListNewAsociacion != null && !oldCarreraIdcarreraOfAsociacionListNewAsociacion.equals(carrera)) {
                        oldCarreraIdcarreraOfAsociacionListNewAsociacion.getAsociacionList().remove(asociacionListNewAsociacion);
                        oldCarreraIdcarreraOfAsociacionListNewAsociacion = em.merge(oldCarreraIdcarreraOfAsociacionListNewAsociacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = carrera.getIdcarrera();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getIdcarrera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Asociacion> asociacionListOrphanCheck = carrera.getAsociacionList();
            for (Asociacion asociacionListOrphanCheckAsociacion : asociacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the Asociacion " + asociacionListOrphanCheckAsociacion + " in its asociacionList field has a non-nullable carreraIdcarrera field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
