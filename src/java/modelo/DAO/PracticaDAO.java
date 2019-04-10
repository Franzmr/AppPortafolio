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
import modelo.Entity.Usuario;
import modelo.Entity.Tipopractica;
import modelo.Entity.Notas;
import modelo.Entity.Empleador;
import modelo.Entity.Documentacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Practica;

/**
 *
 * @author Francisco
 */
public class PracticaDAO implements Serializable {

    public PracticaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Practica practica) throws PreexistingEntityException, Exception {
        if (practica.getDocumentacionList() == null) {
            practica.setDocumentacionList(new ArrayList<Documentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario profesorRut = practica.getProfesorRut();
            if (profesorRut != null) {
                profesorRut = em.getReference(profesorRut.getClass(), profesorRut.getRut());
                practica.setProfesorRut(profesorRut);
            }
            Usuario alumnoRut = practica.getAlumnoRut();
            if (alumnoRut != null) {
                alumnoRut = em.getReference(alumnoRut.getClass(), alumnoRut.getRut());
                practica.setAlumnoRut(alumnoRut);
            }
            Tipopractica tipopracticaIdtipopractica = practica.getTipopracticaIdtipopractica();
            if (tipopracticaIdtipopractica != null) {
                tipopracticaIdtipopractica = em.getReference(tipopracticaIdtipopractica.getClass(), tipopracticaIdtipopractica.getIdtipopractica());
                practica.setTipopracticaIdtipopractica(tipopracticaIdtipopractica);
            }
            Notas notasIdnotas = practica.getNotasIdnotas();
            if (notasIdnotas != null) {
                notasIdnotas = em.getReference(notasIdnotas.getClass(), notasIdnotas.getIdnotas());
                practica.setNotasIdnotas(notasIdnotas);
            }
            Empleador empleadorRut = practica.getEmpleadorRut();
            if (empleadorRut != null) {
                empleadorRut = em.getReference(empleadorRut.getClass(), empleadorRut.getRut());
                practica.setEmpleadorRut(empleadorRut);
            }
            List<Documentacion> attachedDocumentacionList = new ArrayList<Documentacion>();
            for (Documentacion documentacionListDocumentacionToAttach : practica.getDocumentacionList()) {
                documentacionListDocumentacionToAttach = em.getReference(documentacionListDocumentacionToAttach.getClass(), documentacionListDocumentacionToAttach.getIddocumentacion());
                attachedDocumentacionList.add(documentacionListDocumentacionToAttach);
            }
            practica.setDocumentacionList(attachedDocumentacionList);
            em.persist(practica);
            if (profesorRut != null) {
                profesorRut.getPracticaList().add(practica);
                profesorRut = em.merge(profesorRut);
            }
            if (alumnoRut != null) {
                alumnoRut.getPracticaList().add(practica);
                alumnoRut = em.merge(alumnoRut);
            }
            if (tipopracticaIdtipopractica != null) {
                tipopracticaIdtipopractica.getPracticaList().add(practica);
                tipopracticaIdtipopractica = em.merge(tipopracticaIdtipopractica);
            }
            if (notasIdnotas != null) {
                notasIdnotas.getPracticaList().add(practica);
                notasIdnotas = em.merge(notasIdnotas);
            }
            if (empleadorRut != null) {
                empleadorRut.getPracticaList().add(practica);
                empleadorRut = em.merge(empleadorRut);
            }
            for (Documentacion documentacionListDocumentacion : practica.getDocumentacionList()) {
                Practica oldPracticaIdpracticaOfDocumentacionListDocumentacion = documentacionListDocumentacion.getPracticaIdpractica();
                documentacionListDocumentacion.setPracticaIdpractica(practica);
                documentacionListDocumentacion = em.merge(documentacionListDocumentacion);
                if (oldPracticaIdpracticaOfDocumentacionListDocumentacion != null) {
                    oldPracticaIdpracticaOfDocumentacionListDocumentacion.getDocumentacionList().remove(documentacionListDocumentacion);
                    oldPracticaIdpracticaOfDocumentacionListDocumentacion = em.merge(oldPracticaIdpracticaOfDocumentacionListDocumentacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPractica(practica.getIdpractica()) != null) {
                throw new PreexistingEntityException("Practica " + practica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Practica practica) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Practica persistentPractica = em.find(Practica.class, practica.getIdpractica());
            Usuario profesorRutOld = persistentPractica.getProfesorRut();
            Usuario profesorRutNew = practica.getProfesorRut();
            Usuario alumnoRutOld = persistentPractica.getAlumnoRut();
            Usuario alumnoRutNew = practica.getAlumnoRut();
            Tipopractica tipopracticaIdtipopracticaOld = persistentPractica.getTipopracticaIdtipopractica();
            Tipopractica tipopracticaIdtipopracticaNew = practica.getTipopracticaIdtipopractica();
            Notas notasIdnotasOld = persistentPractica.getNotasIdnotas();
            Notas notasIdnotasNew = practica.getNotasIdnotas();
            Empleador empleadorRutOld = persistentPractica.getEmpleadorRut();
            Empleador empleadorRutNew = practica.getEmpleadorRut();
            List<Documentacion> documentacionListOld = persistentPractica.getDocumentacionList();
            List<Documentacion> documentacionListNew = practica.getDocumentacionList();
            List<String> illegalOrphanMessages = null;
            for (Documentacion documentacionListOldDocumentacion : documentacionListOld) {
                if (!documentacionListNew.contains(documentacionListOldDocumentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentacion " + documentacionListOldDocumentacion + " since its practicaIdpractica field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (profesorRutNew != null) {
                profesorRutNew = em.getReference(profesorRutNew.getClass(), profesorRutNew.getRut());
                practica.setProfesorRut(profesorRutNew);
            }
            if (alumnoRutNew != null) {
                alumnoRutNew = em.getReference(alumnoRutNew.getClass(), alumnoRutNew.getRut());
                practica.setAlumnoRut(alumnoRutNew);
            }
            if (tipopracticaIdtipopracticaNew != null) {
                tipopracticaIdtipopracticaNew = em.getReference(tipopracticaIdtipopracticaNew.getClass(), tipopracticaIdtipopracticaNew.getIdtipopractica());
                practica.setTipopracticaIdtipopractica(tipopracticaIdtipopracticaNew);
            }
            if (notasIdnotasNew != null) {
                notasIdnotasNew = em.getReference(notasIdnotasNew.getClass(), notasIdnotasNew.getIdnotas());
                practica.setNotasIdnotas(notasIdnotasNew);
            }
            if (empleadorRutNew != null) {
                empleadorRutNew = em.getReference(empleadorRutNew.getClass(), empleadorRutNew.getRut());
                practica.setEmpleadorRut(empleadorRutNew);
            }
            List<Documentacion> attachedDocumentacionListNew = new ArrayList<Documentacion>();
            for (Documentacion documentacionListNewDocumentacionToAttach : documentacionListNew) {
                documentacionListNewDocumentacionToAttach = em.getReference(documentacionListNewDocumentacionToAttach.getClass(), documentacionListNewDocumentacionToAttach.getIddocumentacion());
                attachedDocumentacionListNew.add(documentacionListNewDocumentacionToAttach);
            }
            documentacionListNew = attachedDocumentacionListNew;
            practica.setDocumentacionList(documentacionListNew);
            practica = em.merge(practica);
            if (profesorRutOld != null && !profesorRutOld.equals(profesorRutNew)) {
                profesorRutOld.getPracticaList().remove(practica);
                profesorRutOld = em.merge(profesorRutOld);
            }
            if (profesorRutNew != null && !profesorRutNew.equals(profesorRutOld)) {
                profesorRutNew.getPracticaList().add(practica);
                profesorRutNew = em.merge(profesorRutNew);
            }
            if (alumnoRutOld != null && !alumnoRutOld.equals(alumnoRutNew)) {
                alumnoRutOld.getPracticaList().remove(practica);
                alumnoRutOld = em.merge(alumnoRutOld);
            }
            if (alumnoRutNew != null && !alumnoRutNew.equals(alumnoRutOld)) {
                alumnoRutNew.getPracticaList().add(practica);
                alumnoRutNew = em.merge(alumnoRutNew);
            }
            if (tipopracticaIdtipopracticaOld != null && !tipopracticaIdtipopracticaOld.equals(tipopracticaIdtipopracticaNew)) {
                tipopracticaIdtipopracticaOld.getPracticaList().remove(practica);
                tipopracticaIdtipopracticaOld = em.merge(tipopracticaIdtipopracticaOld);
            }
            if (tipopracticaIdtipopracticaNew != null && !tipopracticaIdtipopracticaNew.equals(tipopracticaIdtipopracticaOld)) {
                tipopracticaIdtipopracticaNew.getPracticaList().add(practica);
                tipopracticaIdtipopracticaNew = em.merge(tipopracticaIdtipopracticaNew);
            }
            if (notasIdnotasOld != null && !notasIdnotasOld.equals(notasIdnotasNew)) {
                notasIdnotasOld.getPracticaList().remove(practica);
                notasIdnotasOld = em.merge(notasIdnotasOld);
            }
            if (notasIdnotasNew != null && !notasIdnotasNew.equals(notasIdnotasOld)) {
                notasIdnotasNew.getPracticaList().add(practica);
                notasIdnotasNew = em.merge(notasIdnotasNew);
            }
            if (empleadorRutOld != null && !empleadorRutOld.equals(empleadorRutNew)) {
                empleadorRutOld.getPracticaList().remove(practica);
                empleadorRutOld = em.merge(empleadorRutOld);
            }
            if (empleadorRutNew != null && !empleadorRutNew.equals(empleadorRutOld)) {
                empleadorRutNew.getPracticaList().add(practica);
                empleadorRutNew = em.merge(empleadorRutNew);
            }
            for (Documentacion documentacionListNewDocumentacion : documentacionListNew) {
                if (!documentacionListOld.contains(documentacionListNewDocumentacion)) {
                    Practica oldPracticaIdpracticaOfDocumentacionListNewDocumentacion = documentacionListNewDocumentacion.getPracticaIdpractica();
                    documentacionListNewDocumentacion.setPracticaIdpractica(practica);
                    documentacionListNewDocumentacion = em.merge(documentacionListNewDocumentacion);
                    if (oldPracticaIdpracticaOfDocumentacionListNewDocumentacion != null && !oldPracticaIdpracticaOfDocumentacionListNewDocumentacion.equals(practica)) {
                        oldPracticaIdpracticaOfDocumentacionListNewDocumentacion.getDocumentacionList().remove(documentacionListNewDocumentacion);
                        oldPracticaIdpracticaOfDocumentacionListNewDocumentacion = em.merge(oldPracticaIdpracticaOfDocumentacionListNewDocumentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = practica.getIdpractica();
                if (findPractica(id) == null) {
                    throw new NonexistentEntityException("The practica with id " + id + " no longer exists.");
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
            Practica practica;
            try {
                practica = em.getReference(Practica.class, id);
                practica.getIdpractica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The practica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Documentacion> documentacionListOrphanCheck = practica.getDocumentacionList();
            for (Documentacion documentacionListOrphanCheckDocumentacion : documentacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Practica (" + practica + ") cannot be destroyed since the Documentacion " + documentacionListOrphanCheckDocumentacion + " in its documentacionList field has a non-nullable practicaIdpractica field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario profesorRut = practica.getProfesorRut();
            if (profesorRut != null) {
                profesorRut.getPracticaList().remove(practica);
                profesorRut = em.merge(profesorRut);
            }
            Usuario alumnoRut = practica.getAlumnoRut();
            if (alumnoRut != null) {
                alumnoRut.getPracticaList().remove(practica);
                alumnoRut = em.merge(alumnoRut);
            }
            Tipopractica tipopracticaIdtipopractica = practica.getTipopracticaIdtipopractica();
            if (tipopracticaIdtipopractica != null) {
                tipopracticaIdtipopractica.getPracticaList().remove(practica);
                tipopracticaIdtipopractica = em.merge(tipopracticaIdtipopractica);
            }
            Notas notasIdnotas = practica.getNotasIdnotas();
            if (notasIdnotas != null) {
                notasIdnotas.getPracticaList().remove(practica);
                notasIdnotas = em.merge(notasIdnotas);
            }
            Empleador empleadorRut = practica.getEmpleadorRut();
            if (empleadorRut != null) {
                empleadorRut.getPracticaList().remove(practica);
                empleadorRut = em.merge(empleadorRut);
            }
            em.remove(practica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Practica> findPracticaEntities() {
        return findPracticaEntities(true, -1, -1);
    }

    public List<Practica> findPracticaEntities(int maxResults, int firstResult) {
        return findPracticaEntities(false, maxResults, firstResult);
    }

    private List<Practica> findPracticaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Practica.class));
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

    public Practica findPractica(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Practica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPracticaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Practica> rt = cq.from(Practica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
