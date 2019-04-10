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
import modelo.Entity.Notas;

/**
 *
 * @author Francisco
 */
public class NotasDAO implements Serializable {

    public NotasDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notas notas) throws PreexistingEntityException, Exception {
        if (notas.getPracticaList() == null) {
            notas.setPracticaList(new ArrayList<Practica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Practica> attachedPracticaList = new ArrayList<Practica>();
            for (Practica practicaListPracticaToAttach : notas.getPracticaList()) {
                practicaListPracticaToAttach = em.getReference(practicaListPracticaToAttach.getClass(), practicaListPracticaToAttach.getIdpractica());
                attachedPracticaList.add(practicaListPracticaToAttach);
            }
            notas.setPracticaList(attachedPracticaList);
            em.persist(notas);
            for (Practica practicaListPractica : notas.getPracticaList()) {
                Notas oldNotasIdnotasOfPracticaListPractica = practicaListPractica.getNotasIdnotas();
                practicaListPractica.setNotasIdnotas(notas);
                practicaListPractica = em.merge(practicaListPractica);
                if (oldNotasIdnotasOfPracticaListPractica != null) {
                    oldNotasIdnotasOfPracticaListPractica.getPracticaList().remove(practicaListPractica);
                    oldNotasIdnotasOfPracticaListPractica = em.merge(oldNotasIdnotasOfPracticaListPractica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotas(notas.getIdnotas()) != null) {
                throw new PreexistingEntityException("Notas " + notas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notas notas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notas persistentNotas = em.find(Notas.class, notas.getIdnotas());
            List<Practica> practicaListOld = persistentNotas.getPracticaList();
            List<Practica> practicaListNew = notas.getPracticaList();
            List<String> illegalOrphanMessages = null;
            for (Practica practicaListOldPractica : practicaListOld) {
                if (!practicaListNew.contains(practicaListOldPractica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practica " + practicaListOldPractica + " since its notasIdnotas field is not nullable.");
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
            notas.setPracticaList(practicaListNew);
            notas = em.merge(notas);
            for (Practica practicaListNewPractica : practicaListNew) {
                if (!practicaListOld.contains(practicaListNewPractica)) {
                    Notas oldNotasIdnotasOfPracticaListNewPractica = practicaListNewPractica.getNotasIdnotas();
                    practicaListNewPractica.setNotasIdnotas(notas);
                    practicaListNewPractica = em.merge(practicaListNewPractica);
                    if (oldNotasIdnotasOfPracticaListNewPractica != null && !oldNotasIdnotasOfPracticaListNewPractica.equals(notas)) {
                        oldNotasIdnotasOfPracticaListNewPractica.getPracticaList().remove(practicaListNewPractica);
                        oldNotasIdnotasOfPracticaListNewPractica = em.merge(oldNotasIdnotasOfPracticaListNewPractica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = notas.getIdnotas();
                if (findNotas(id) == null) {
                    throw new NonexistentEntityException("The notas with id " + id + " no longer exists.");
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
            Notas notas;
            try {
                notas = em.getReference(Notas.class, id);
                notas.getIdnotas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Practica> practicaListOrphanCheck = notas.getPracticaList();
            for (Practica practicaListOrphanCheckPractica : practicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Notas (" + notas + ") cannot be destroyed since the Practica " + practicaListOrphanCheckPractica + " in its practicaList field has a non-nullable notasIdnotas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(notas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notas> findNotasEntities() {
        return findNotasEntities(true, -1, -1);
    }

    public List<Notas> findNotasEntities(int maxResults, int firstResult) {
        return findNotasEntities(false, maxResults, firstResult);
    }

    private List<Notas> findNotasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notas.class));
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

    public Notas findNotas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notas.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notas> rt = cq.from(Notas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
