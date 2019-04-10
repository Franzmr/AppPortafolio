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
import modelo.Entity.Asociacion;
import modelo.Entity.Practica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Usuario;

/**
 *
 * @author Francisco
 */
public class UsuarioDAO implements Serializable {

    public UsuarioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getPracticaList() == null) {
            usuario.setPracticaList(new ArrayList<Practica>());
        }
        if (usuario.getPracticaList1() == null) {
            usuario.setPracticaList1(new ArrayList<Practica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario tipousuarioIdtipousuario = usuario.getTipousuarioIdtipousuario();
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario = em.getReference(tipousuarioIdtipousuario.getClass(), tipousuarioIdtipousuario.getIdtipousuario());
                usuario.setTipousuarioIdtipousuario(tipousuarioIdtipousuario);
            }
            Comuna comunaIdcomuna = usuario.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna = em.getReference(comunaIdcomuna.getClass(), comunaIdcomuna.getIdcomuna());
                usuario.setComunaIdcomuna(comunaIdcomuna);
            }
            Asociacion asociacionIdasociacion = usuario.getAsociacionIdasociacion();
            if (asociacionIdasociacion != null) {
                asociacionIdasociacion = em.getReference(asociacionIdasociacion.getClass(), asociacionIdasociacion.getIdasociacion());
                usuario.setAsociacionIdasociacion(asociacionIdasociacion);
            }
            List<Practica> attachedPracticaList = new ArrayList<Practica>();
            for (Practica practicaListPracticaToAttach : usuario.getPracticaList()) {
                practicaListPracticaToAttach = em.getReference(practicaListPracticaToAttach.getClass(), practicaListPracticaToAttach.getIdpractica());
                attachedPracticaList.add(practicaListPracticaToAttach);
            }
            usuario.setPracticaList(attachedPracticaList);
            List<Practica> attachedPracticaList1 = new ArrayList<Practica>();
            for (Practica practicaList1PracticaToAttach : usuario.getPracticaList1()) {
                practicaList1PracticaToAttach = em.getReference(practicaList1PracticaToAttach.getClass(), practicaList1PracticaToAttach.getIdpractica());
                attachedPracticaList1.add(practicaList1PracticaToAttach);
            }
            usuario.setPracticaList1(attachedPracticaList1);
            em.persist(usuario);
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario.getUsuarioList().add(usuario);
                tipousuarioIdtipousuario = em.merge(tipousuarioIdtipousuario);
            }
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getUsuarioList().add(usuario);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            if (asociacionIdasociacion != null) {
                asociacionIdasociacion.getUsuarioList().add(usuario);
                asociacionIdasociacion = em.merge(asociacionIdasociacion);
            }
            for (Practica practicaListPractica : usuario.getPracticaList()) {
                Usuario oldProfesorRutOfPracticaListPractica = practicaListPractica.getProfesorRut();
                practicaListPractica.setProfesorRut(usuario);
                practicaListPractica = em.merge(practicaListPractica);
                if (oldProfesorRutOfPracticaListPractica != null) {
                    oldProfesorRutOfPracticaListPractica.getPracticaList().remove(practicaListPractica);
                    oldProfesorRutOfPracticaListPractica = em.merge(oldProfesorRutOfPracticaListPractica);
                }
            }
            for (Practica practicaList1Practica : usuario.getPracticaList1()) {
                Usuario oldAlumnoRutOfPracticaList1Practica = practicaList1Practica.getAlumnoRut();
                practicaList1Practica.setAlumnoRut(usuario);
                practicaList1Practica = em.merge(practicaList1Practica);
                if (oldAlumnoRutOfPracticaList1Practica != null) {
                    oldAlumnoRutOfPracticaList1Practica.getPracticaList1().remove(practicaList1Practica);
                    oldAlumnoRutOfPracticaList1Practica = em.merge(oldAlumnoRutOfPracticaList1Practica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getRut()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getRut());
            Tipousuario tipousuarioIdtipousuarioOld = persistentUsuario.getTipousuarioIdtipousuario();
            Tipousuario tipousuarioIdtipousuarioNew = usuario.getTipousuarioIdtipousuario();
            Comuna comunaIdcomunaOld = persistentUsuario.getComunaIdcomuna();
            Comuna comunaIdcomunaNew = usuario.getComunaIdcomuna();
            Asociacion asociacionIdasociacionOld = persistentUsuario.getAsociacionIdasociacion();
            Asociacion asociacionIdasociacionNew = usuario.getAsociacionIdasociacion();
            List<Practica> practicaListOld = persistentUsuario.getPracticaList();
            List<Practica> practicaListNew = usuario.getPracticaList();
            List<Practica> practicaList1Old = persistentUsuario.getPracticaList1();
            List<Practica> practicaList1New = usuario.getPracticaList1();
            List<String> illegalOrphanMessages = null;
            for (Practica practicaListOldPractica : practicaListOld) {
                if (!practicaListNew.contains(practicaListOldPractica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practica " + practicaListOldPractica + " since its profesorRut field is not nullable.");
                }
            }
            for (Practica practicaList1OldPractica : practicaList1Old) {
                if (!practicaList1New.contains(practicaList1OldPractica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practica " + practicaList1OldPractica + " since its alumnoRut field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipousuarioIdtipousuarioNew != null) {
                tipousuarioIdtipousuarioNew = em.getReference(tipousuarioIdtipousuarioNew.getClass(), tipousuarioIdtipousuarioNew.getIdtipousuario());
                usuario.setTipousuarioIdtipousuario(tipousuarioIdtipousuarioNew);
            }
            if (comunaIdcomunaNew != null) {
                comunaIdcomunaNew = em.getReference(comunaIdcomunaNew.getClass(), comunaIdcomunaNew.getIdcomuna());
                usuario.setComunaIdcomuna(comunaIdcomunaNew);
            }
            if (asociacionIdasociacionNew != null) {
                asociacionIdasociacionNew = em.getReference(asociacionIdasociacionNew.getClass(), asociacionIdasociacionNew.getIdasociacion());
                usuario.setAsociacionIdasociacion(asociacionIdasociacionNew);
            }
            List<Practica> attachedPracticaListNew = new ArrayList<Practica>();
            for (Practica practicaListNewPracticaToAttach : practicaListNew) {
                practicaListNewPracticaToAttach = em.getReference(practicaListNewPracticaToAttach.getClass(), practicaListNewPracticaToAttach.getIdpractica());
                attachedPracticaListNew.add(practicaListNewPracticaToAttach);
            }
            practicaListNew = attachedPracticaListNew;
            usuario.setPracticaList(practicaListNew);
            List<Practica> attachedPracticaList1New = new ArrayList<Practica>();
            for (Practica practicaList1NewPracticaToAttach : practicaList1New) {
                practicaList1NewPracticaToAttach = em.getReference(practicaList1NewPracticaToAttach.getClass(), practicaList1NewPracticaToAttach.getIdpractica());
                attachedPracticaList1New.add(practicaList1NewPracticaToAttach);
            }
            practicaList1New = attachedPracticaList1New;
            usuario.setPracticaList1(practicaList1New);
            usuario = em.merge(usuario);
            if (tipousuarioIdtipousuarioOld != null && !tipousuarioIdtipousuarioOld.equals(tipousuarioIdtipousuarioNew)) {
                tipousuarioIdtipousuarioOld.getUsuarioList().remove(usuario);
                tipousuarioIdtipousuarioOld = em.merge(tipousuarioIdtipousuarioOld);
            }
            if (tipousuarioIdtipousuarioNew != null && !tipousuarioIdtipousuarioNew.equals(tipousuarioIdtipousuarioOld)) {
                tipousuarioIdtipousuarioNew.getUsuarioList().add(usuario);
                tipousuarioIdtipousuarioNew = em.merge(tipousuarioIdtipousuarioNew);
            }
            if (comunaIdcomunaOld != null && !comunaIdcomunaOld.equals(comunaIdcomunaNew)) {
                comunaIdcomunaOld.getUsuarioList().remove(usuario);
                comunaIdcomunaOld = em.merge(comunaIdcomunaOld);
            }
            if (comunaIdcomunaNew != null && !comunaIdcomunaNew.equals(comunaIdcomunaOld)) {
                comunaIdcomunaNew.getUsuarioList().add(usuario);
                comunaIdcomunaNew = em.merge(comunaIdcomunaNew);
            }
            if (asociacionIdasociacionOld != null && !asociacionIdasociacionOld.equals(asociacionIdasociacionNew)) {
                asociacionIdasociacionOld.getUsuarioList().remove(usuario);
                asociacionIdasociacionOld = em.merge(asociacionIdasociacionOld);
            }
            if (asociacionIdasociacionNew != null && !asociacionIdasociacionNew.equals(asociacionIdasociacionOld)) {
                asociacionIdasociacionNew.getUsuarioList().add(usuario);
                asociacionIdasociacionNew = em.merge(asociacionIdasociacionNew);
            }
            for (Practica practicaListNewPractica : practicaListNew) {
                if (!practicaListOld.contains(practicaListNewPractica)) {
                    Usuario oldProfesorRutOfPracticaListNewPractica = practicaListNewPractica.getProfesorRut();
                    practicaListNewPractica.setProfesorRut(usuario);
                    practicaListNewPractica = em.merge(practicaListNewPractica);
                    if (oldProfesorRutOfPracticaListNewPractica != null && !oldProfesorRutOfPracticaListNewPractica.equals(usuario)) {
                        oldProfesorRutOfPracticaListNewPractica.getPracticaList().remove(practicaListNewPractica);
                        oldProfesorRutOfPracticaListNewPractica = em.merge(oldProfesorRutOfPracticaListNewPractica);
                    }
                }
            }
            for (Practica practicaList1NewPractica : practicaList1New) {
                if (!practicaList1Old.contains(practicaList1NewPractica)) {
                    Usuario oldAlumnoRutOfPracticaList1NewPractica = practicaList1NewPractica.getAlumnoRut();
                    practicaList1NewPractica.setAlumnoRut(usuario);
                    practicaList1NewPractica = em.merge(practicaList1NewPractica);
                    if (oldAlumnoRutOfPracticaList1NewPractica != null && !oldAlumnoRutOfPracticaList1NewPractica.equals(usuario)) {
                        oldAlumnoRutOfPracticaList1NewPractica.getPracticaList1().remove(practicaList1NewPractica);
                        oldAlumnoRutOfPracticaList1NewPractica = em.merge(oldAlumnoRutOfPracticaList1NewPractica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getRut();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getRut();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Practica> practicaListOrphanCheck = usuario.getPracticaList();
            for (Practica practicaListOrphanCheckPractica : practicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Practica " + practicaListOrphanCheckPractica + " in its practicaList field has a non-nullable profesorRut field.");
            }
            List<Practica> practicaList1OrphanCheck = usuario.getPracticaList1();
            for (Practica practicaList1OrphanCheckPractica : practicaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Practica " + practicaList1OrphanCheckPractica + " in its practicaList1 field has a non-nullable alumnoRut field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipousuario tipousuarioIdtipousuario = usuario.getTipousuarioIdtipousuario();
            if (tipousuarioIdtipousuario != null) {
                tipousuarioIdtipousuario.getUsuarioList().remove(usuario);
                tipousuarioIdtipousuario = em.merge(tipousuarioIdtipousuario);
            }
            Comuna comunaIdcomuna = usuario.getComunaIdcomuna();
            if (comunaIdcomuna != null) {
                comunaIdcomuna.getUsuarioList().remove(usuario);
                comunaIdcomuna = em.merge(comunaIdcomuna);
            }
            Asociacion asociacionIdasociacion = usuario.getAsociacionIdasociacion();
            if (asociacionIdasociacion != null) {
                asociacionIdasociacion.getUsuarioList().remove(usuario);
                asociacionIdasociacion = em.merge(asociacionIdasociacion);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
