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
import modelo.Entity.Practica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Tipopractica;

/**
 *
 * @author Francisco
 */
public class TipopracticaJpaController implements Serializable {

    public TipopracticaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipopractica tipopractica) throws PreexistingEntityException, Exception {
        if (tipopractica.getPracticaList() == null) {
            tipopractica.setPracticaList(new ArrayList<Practica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Practica> attachedPracticaList = new ArrayList<Practica>();
            for (Practica practicaListPracticaToAttach : tipopractica.getPracticaList()) {
                practicaListPracticaToAttach = em.getReference(practicaListPracticaToAttach.getClass(), practicaListPracticaToAttach.getIdpractica());
                attachedPracticaList.add(practicaListPracticaToAttach);
            }
            tipopractica.setPracticaList(attachedPracticaList);
            em.persist(tipopractica);
            for (Practica practicaListPractica : tipopractica.getPracticaList()) {
                Tipopractica oldTipopracticaIdtipopracticaOfPracticaListPractica = practicaListPractica.getTipopracticaIdtipopractica();
                practicaListPractica.setTipopracticaIdtipopractica(tipopractica);
                practicaListPractica = em.merge(practicaListPractica);
                if (oldTipopracticaIdtipopracticaOfPracticaListPractica != null) {
                    oldTipopracticaIdtipopracticaOfPracticaListPractica.getPracticaList().remove(practicaListPractica);
                    oldTipopracticaIdtipopracticaOfPracticaListPractica = em.merge(oldTipopracticaIdtipopracticaOfPracticaListPractica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipopractica(tipopractica.getIdtipopractica()) != null) {
                throw new PreexistingEntityException("Tipopractica " + tipopractica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipopractica tipopractica) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipopractica persistentTipopractica = em.find(Tipopractica.class, tipopractica.getIdtipopractica());
            List<Practica> practicaListOld = persistentTipopractica.getPracticaList();
            List<Practica> practicaListNew = tipopractica.getPracticaList();
            List<String> illegalOrphanMessages = null;
            for (Practica practicaListOldPractica : practicaListOld) {
                if (!practicaListNew.contains(practicaListOldPractica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practica " + practicaListOldPractica + " since its tipopracticaIdtipopractica field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Practica> attachedPracticaListNew = new ArrayList<Practica>();
            for (Practica practicaListNewPracticaToAttach : practicaListNew) {
                practicaListNewPracticaToAttach = em.getReference(practicaListNewPracticaToAttach.getClass(), practicaListNewPracticaToAttach.getIdpractica());
                attachedPracticaListNew.add(practicaListNewPracticaToAttach);
            }
            practicaListNew = attachedPracticaListNew;
            tipopractica.setPracticaList(practicaListNew);
            tipopractica = em.merge(tipopractica);
            for (Practica practicaListNewPractica : practicaListNew) {
                if (!practicaListOld.contains(practicaListNewPractica)) {
                    Tipopractica oldTipopracticaIdtipopracticaOfPracticaListNewPractica = practicaListNewPractica.getTipopracticaIdtipopractica();
                    practicaListNewPractica.setTipopracticaIdtipopractica(tipopractica);
                    practicaListNewPractica = em.merge(practicaListNewPractica);
                    if (oldTipopracticaIdtipopracticaOfPracticaListNewPractica != null && !oldTipopracticaIdtipopracticaOfPracticaListNewPractica.equals(tipopractica)) {
                        oldTipopracticaIdtipopracticaOfPracticaListNewPractica.getPracticaList().remove(practicaListNewPractica);
                        oldTipopracticaIdtipopracticaOfPracticaListNewPractica = em.merge(oldTipopracticaIdtipopracticaOfPracticaListNewPractica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipopractica.getIdtipopractica();
                if (findTipopractica(id) == null) {
                    throw new NonexistentEntityException("The tipopractica with id " + id + " no longer exists.");
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
            Tipopractica tipopractica;
            try {
                tipopractica = em.getReference(Tipopractica.class, id);
                tipopractica.getIdtipopractica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipopractica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Practica> practicaListOrphanCheck = tipopractica.getPracticaList();
            for (Practica practicaListOrphanCheckPractica : practicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipopractica (" + tipopractica + ") cannot be destroyed since the Practica " + practicaListOrphanCheckPractica + " in its practicaList field has a non-nullable tipopracticaIdtipopractica field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipopractica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipopractica> findTipopracticaEntities() {
        return findTipopracticaEntities(true, -1, -1);
    }

    public List<Tipopractica> findTipopracticaEntities(int maxResults, int firstResult) {
        return findTipopracticaEntities(false, maxResults, firstResult);
    }

    private List<Tipopractica> findTipopracticaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipopractica.class));
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

    public Tipopractica findTipopractica(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipopractica.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipopracticaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipopractica> rt = cq.from(Tipopractica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
