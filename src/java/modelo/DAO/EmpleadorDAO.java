/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Entity.Tipousuario;
import modelo.Entity.Comuna;
import modelo.Entity.Practica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Empleador;

/**
 *
 * @author Francisco
 */
public class EmpleadorDAO implements Serializable {

    public EmpleadorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleador empleador) throws PreexistingEntityException, Exception {
        if (empleador.getPracticaList() == null) {
            empleador.setPracticaList(new ArrayList<Practica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario tipousuarioIdtipousuario = empleador.getTipousuarioIdtipousuario();
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario = em.getReference(tipousuarioIdtipousuario.getClass(), tipousuarioIdtipousuario.getIdtipousuario());
                empleador.setTipousuarioIdtipousuario(tipousuarioIdtipousuario);
            }
            Comuna comunaIdcomuna = empleador.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna = em.getReference(comunaIdcomuna.getClass(), comunaIdcomuna.getIdcomuna());
                empleador.setComunaIdcomuna(comunaIdcomuna);
            }
            List<Practica> attachedPracticaList = new ArrayList<Practica>();
            for (Practica practicaListPracticaToAttach : empleador.getPracticaList()) {
                practicaListPracticaToAttach = em.getReference(practicaListPracticaToAttach.getClass(), practicaListPracticaToAttach.getIdpractica());
                attachedPracticaList.add(practicaListPracticaToAttach);
            }
            empleador.setPracticaList(attachedPracticaList);
            em.persist(empleador);
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario.getEmpleadorList().add(empleador);
                tipousuarioIdtipousuario = em.merge(tipousuarioIdtipousuario);
            }
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getEmpleadorList().add(empleador);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            for (Practica practicaListPractica : empleador.getPracticaList()) {
                Empleador oldEmpleadorRutOfPracticaListPractica = practicaListPractica.getEmpleadorRut();
                practicaListPractica.setEmpleadorRut(empleador);
                practicaListPractica = em.merge(practicaListPractica);
                if (oldEmpleadorRutOfPracticaListPractica != null) {
                    oldEmpleadorRutOfPracticaListPractica.getPracticaList().remove(practicaListPractica);
                    oldEmpleadorRutOfPracticaListPractica = em.merge(oldEmpleadorRutOfPracticaListPractica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleador(empleador.getRut()) != null) {
                throw new PreexistingEntityException("Empleador " + empleador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleador empleador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleador persistentEmpleador = em.find(Empleador.class, empleador.getRut());
            Tipousuario tipousuarioIdtipousuarioOld = persistentEmpleador.getTipousuarioIdtipousuario();
            Tipousuario tipousuarioIdtipousuarioNew = empleador.getTipousuarioIdtipousuario();
            Comuna comunaIdcomunaOld = persistentEmpleador.getComunaIdcomuna();
            Comuna comunaIdcomunaNew = empleador.getComunaIdcomuna();
            List<Practica> practicaListOld = persistentEmpleador.getPracticaList();
            List<Practica> practicaListNew = empleador.getPracticaList();
            List<String> illegalOrphanMessages = null;
            for (Practica practicaListOldPractica : practicaListOld) {
                if (!practicaListNew.contains(practicaListOldPractica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practica " + practicaListOldPractica + " since its empleadorRut field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipousuarioIdtipousuarioNew != null) {
                tipousuarioIdtipousuarioNew = em.getReference(tipousuarioIdtipousuarioNew.getClass(), tipousuarioIdtipousuarioNew.getIdtipousuario());
                empleador.setTipousuarioIdtipousuario(tipousuarioIdtipousuarioNew);
            }
            if (comunaIdcomunaNew != null) {
                comunaIdcomunaNew = em.getReference(comunaIdcomunaNew.getClass(), comunaIdcomunaNew.getIdcomuna());
                empleador.setComunaIdcomuna(comunaIdcomunaNew);
            }
            List<Practica> attachedPracticaListNew = new ArrayList<Practica>();
            for (Practica practicaListNewPracticaToAttach : practicaListNew) {
                practicaListNewPracticaToAttach = em.getReference(practicaListNewPracticaToAttach.getClass(), practicaListNewPracticaToAttach.getIdpractica());
                attachedPracticaListNew.add(practicaListNewPracticaToAttach);
            }
            practicaListNew = attachedPracticaListNew;
            empleador.setPracticaList(practicaListNew);
            empleador = em.merge(empleador);
            if (tipousuarioIdtipousuarioOld != null && !tipousuarioIdtipousuarioOld.equals(tipousuarioIdtipousuarioNew)) {
                tipousuarioIdtipousuarioOld.getEmpleadorList().remove(empleador);
                tipousuarioIdtipousuarioOld = em.merge(tipousuarioIdtipousuarioOld);
            }
            if (tipousuarioIdtipousuarioNew != null && !tipousuarioIdtipousuarioNew.equals(tipousuarioIdtipousuarioOld)) {
                tipousuarioIdtipousuarioNew.getEmpleadorList().add(empleador);
                tipousuarioIdtipousuarioNew = em.merge(tipousuarioIdtipousuarioNew);
            }
            if (comunaIdcomunaOld != null && !comunaIdcomunaOld.equals(comunaIdcomunaNew)) {
                comunaIdcomunaOld.getEmpleadorList().remove(empleador);
                comunaIdcomunaOld = em.merge(comunaIdcomunaOld);
            }
            if (comunaIdcomunaNew != null && !comunaIdcomunaNew.equals(comunaIdcomunaOld)) {
                comunaIdcomunaNew.getEmpleadorList().add(empleador);
                comunaIdcomunaNew = em.merge(comunaIdcomunaNew);
            }
            for (Practica practicaListNewPractica : practicaListNew) {
                if (!practicaListOld.contains(practicaListNewPractica)) {
                    Empleador oldEmpleadorRutOfPracticaListNewPractica = practicaListNewPractica.getEmpleadorRut();
                    practicaListNewPractica.setEmpleadorRut(empleador);
                    practicaListNewPractica = em.merge(practicaListNewPractica);
                    if (oldEmpleadorRutOfPracticaListNewPractica != null && !oldEmpleadorRutOfPracticaListNewPractica.equals(empleador)) {
                        oldEmpleadorRutOfPracticaListNewPractica.getPracticaList().remove(practicaListNewPractica);
                        oldEmpleadorRutOfPracticaListNewPractica = em.merge(oldEmpleadorRutOfPracticaListNewPractica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleador.getRut();
                if (findEmpleador(id) == null) {
                    throw new NonexistentEntityException("The empleador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleador empleador;
            try {
                empleador = em.getReference(Empleador.class, id);
                empleador.getRut();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Practica> practicaListOrphanCheck = empleador.getPracticaList();
            for (Practica practicaListOrphanCheckPractica : practicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleador (" + empleador + ") cannot be destroyed since the Practica " + practicaListOrphanCheckPractica + " in its practicaList field has a non-nullable empleadorRut field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipousuario tipousuarioIdtipousuario = empleador.getTipousuarioIdtipousuario();
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario.getEmpleadorList().remove(empleador);
                tipousuarioIdtipousuario = em.merge(tipousuarioIdtipousuario);
            }
            Comuna comunaIdcomuna = empleador.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getEmpleadorList().remove(empleador);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            em.remove(empleador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleador> findEmpleadorEntities() {
        return findEmpleadorEntities(true, -1, -1);
    }

    public List<Empleador> findEmpleadorEntities(int maxResults, int firstResult) {
        return findEmpleadorEntities(false, maxResults, firstResult);
    }

    private List<Empleador> findEmpleadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleador.class));
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

    public Empleador findEmpleador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleador.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleador> rt = cq.from(Empleador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
