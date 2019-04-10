/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Documentacion;
import modelo.Entity.Practica;

/**
 *
 * @author Francisco
 */
public class DocumentacionDAO implements Serializable {

    public DocumentacionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentacion documentacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Practica practicaIdpractica = documentacion.getPracticaIdpractica();
            if (practicaIdpractica != null) {
                practicaIdpractica = em.getReference(practicaIdpractica.getClass(), practicaIdpractica.getIdpractica());
                documentacion.setPracticaIdpractica(practicaIdpractica);
            }
            em.persist(documentacion);
            if (practicaIdpractica != null) {
                practicaIdpractica.getDocumentacionList().add(documentacion);
                practicaIdpractica = em.merge(practicaIdpractica);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentacion(documentacion.getIddocumentacion()) != null) {
                throw new PreexistingEntityException("Documentacion " + documentacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentacion documentacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentacion persistentDocumentacion = em.find(Documentacion.class, documentacion.getIddocumentacion());
            Practica practicaIdpracticaOld = persistentDocumentacion.getPracticaIdpractica();
            Practica practicaIdpracticaNew = documentacion.getPracticaIdpractica();
            if (practicaIdpracticaNew != null) {
                practicaIdpracticaNew = em.getReference(practicaIdpracticaNew.getClass(), practicaIdpracticaNew.getIdpractica());
                documentacion.setPracticaIdpractica(practicaIdpracticaNew);
            }
            documentacion = em.merge(documentacion);
            if (practicaIdpracticaOld != null && !practicaIdpracticaOld.equals(practicaIdpracticaNew)) {
                practicaIdpracticaOld.getDocumentacionList().remove(documentacion);
                practicaIdpracticaOld = em.merge(practicaIdpracticaOld);
            }
            if (practicaIdpracticaNew != null && !practicaIdpracticaNew.equals(practicaIdpracticaOld)) {
                practicaIdpracticaNew.getDocumentacionList().add(documentacion);
                practicaIdpracticaNew = em.merge(practicaIdpracticaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = documentacion.getIddocumentacion();
                if (findDocumentacion(id) == null) {
                    throw new NonexistentEntityException("The documentacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentacion documentacion;
            try {
                documentacion = em.getReference(Documentacion.class, id);
                documentacion.getIddocumentacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentacion with id " + id + " no longer exists.", enfe);
            }
            Practica practicaIdpractica = documentacion.getPracticaIdpractica();
            if (practicaIdpractica != null) {
                practicaIdpractica.getDocumentacionList().remove(documentacion);
                practicaIdpractica = em.merge(practicaIdpractica);
            }
            em.remove(documentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentacion> findDocumentacionEntities() {
        return findDocumentacionEntities(true, -1, -1);
    }

    public List<Documentacion> findDocumentacionEntities(int maxResults, int firstResult) {
        return findDocumentacionEntities(false, maxResults, firstResult);
    }

    private List<Documentacion> findDocumentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentacion.class));
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

    public Documentacion findDocumentacion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentacion> rt = cq.from(Documentacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
