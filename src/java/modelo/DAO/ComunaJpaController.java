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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Comuna;
import modelo.Entity.Empleador;
import modelo.Entity.Sede;

/**
 *
 * @author Francisco
 */
public class ComunaJpaController implements Serializable {

    public ComunaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comuna comuna) throws PreexistingEntityException, Exception {
        if (comuna.getUsuarioList() == null) {
            comuna.setUsuarioList(new ArrayList<Usuario>());
        }
        if (comuna.getEmpleadorList() == null) {
            comuna.setEmpleadorList(new ArrayList<Empleador>());
        }
        if (comuna.getSedeList() == null) {
            comuna.setSedeList(new ArrayList<Sede>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : comuna.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getRut());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            comuna.setUsuarioList(attachedUsuarioList);
            List<Empleador> attachedEmpleadorList = new ArrayList<Empleador>();
            for (Empleador empleadorListEmpleadorToAttach : comuna.getEmpleadorList()) {
                empleadorListEmpleadorToAttach = em.getReference(empleadorListEmpleadorToAttach.getClass(), empleadorListEmpleadorToAttach.getRut());
                attachedEmpleadorList.add(empleadorListEmpleadorToAttach);
            }
            comuna.setEmpleadorList(attachedEmpleadorList);
            List<Sede> attachedSedeList = new ArrayList<Sede>();
            for (Sede sedeListSedeToAttach : comuna.getSedeList()) {
                sedeListSedeToAttach = em.getReference(sedeListSedeToAttach.getClass(), sedeListSedeToAttach.getIdsede());
                attachedSedeList.add(sedeListSedeToAttach);
            }
            comuna.setSedeList(attachedSedeList);
            em.persist(comuna);
            for (Usuario usuarioListUsuario : comuna.getUsuarioList()) {
                Comuna oldComunaIdcomunaOfUsuarioListUsuario = usuarioListUsuario.getComunaIdcomuna();
                usuarioListUsuario.setComunaIdcomuna(comuna);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldComunaIdcomunaOfUsuarioListUsuario != null) {
                    oldComunaIdcomunaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldComunaIdcomunaOfUsuarioListUsuario = em.merge(oldComunaIdcomunaOfUsuarioListUsuario);
                }
            }
            for (Empleador empleadorListEmpleador : comuna.getEmpleadorList()) {
                Comuna oldComunaIdcomunaOfEmpleadorListEmpleador = empleadorListEmpleador.getComunaIdcomuna();
                empleadorListEmpleador.setComunaIdcomuna(comuna);
                empleadorListEmpleador = em.merge(empleadorListEmpleador);
                if (oldComunaIdcomunaOfEmpleadorListEmpleador != null) {
                    oldComunaIdcomunaOfEmpleadorListEmpleador.getEmpleadorList().remove(empleadorListEmpleador);
                    oldComunaIdcomunaOfEmpleadorListEmpleador = em.merge(oldComunaIdcomunaOfEmpleadorListEmpleador);
                }
            }
            for (Sede sedeListSede : comuna.getSedeList()) {
                Comuna oldComunaIdcomunaOfSedeListSede = sedeListSede.getComunaIdcomuna();
                sedeListSede.setComunaIdcomuna(comuna);
                sedeListSede = em.merge(sedeListSede);
                if (oldComunaIdcomunaOfSedeListSede != null) {
                    oldComunaIdcomunaOfSedeListSede.getSedeList().remove(sedeListSede);
                    oldComunaIdcomunaOfSedeListSede = em.merge(oldComunaIdcomunaOfSedeListSede);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComuna(comuna.getIdcomuna()) != null) {
                throw new PreexistingEntityException("Comuna " + comuna + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comuna comuna) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna persistentComuna = em.find(Comuna.class, comuna.getIdcomuna());
            List<Usuario> usuarioListOld = persistentComuna.getUsuarioList();
            List<Usuario> usuarioListNew = comuna.getUsuarioList();
            List<Empleador> empleadorListOld = persistentComuna.getEmpleadorList();
            List<Empleador> empleadorListNew = comuna.getEmpleadorList();
            List<Sede> sedeListOld = persistentComuna.getSedeList();
            List<Sede> sedeListNew = comuna.getSedeList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its comunaIdcomuna field is not nullable.");
                }
            }
            for (Empleador empleadorListOldEmpleador : empleadorListOld) {
                if (!empleadorListNew.contains(empleadorListOldEmpleador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleador " + empleadorListOldEmpleador + " since its comunaIdcomuna field is not nullable.");
                }
            }
            for (Sede sedeListOldSede : sedeListOld) {
                if (!sedeListNew.contains(sedeListOldSede)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sede " + sedeListOldSede + " since its comunaIdcomuna field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getRut());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            comuna.setUsuarioList(usuarioListNew);
            List<Empleador> attachedEmpleadorListNew = new ArrayList<Empleador>();
            for (Empleador empleadorListNewEmpleadorToAttach : empleadorListNew) {
                empleadorListNewEmpleadorToAttach = em.getReference(empleadorListNewEmpleadorToAttach.getClass(), empleadorListNewEmpleadorToAttach.getRut());
                attachedEmpleadorListNew.add(empleadorListNewEmpleadorToAttach);
            }
            empleadorListNew = attachedEmpleadorListNew;
            comuna.setEmpleadorList(empleadorListNew);
            List<Sede> attachedSedeListNew = new ArrayList<Sede>();
            for (Sede sedeListNewSedeToAttach : sedeListNew) {
                sedeListNewSedeToAttach = em.getReference(sedeListNewSedeToAttach.getClass(), sedeListNewSedeToAttach.getIdsede());
                attachedSedeListNew.add(sedeListNewSedeToAttach);
            }
            sedeListNew = attachedSedeListNew;
            comuna.setSedeList(sedeListNew);
            comuna = em.merge(comuna);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Comuna oldComunaIdcomunaOfUsuarioListNewUsuario = usuarioListNewUsuario.getComunaIdcomuna();
                    usuarioListNewUsuario.setComunaIdcomuna(comuna);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldComunaIdcomunaOfUsuarioListNewUsuario != null && !oldComunaIdcomunaOfUsuarioListNewUsuario.equals(comuna)) {
                        oldComunaIdcomunaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldComunaIdcomunaOfUsuarioListNewUsuario = em.merge(oldComunaIdcomunaOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Empleador empleadorListNewEmpleador : empleadorListNew) {
                if (!empleadorListOld.contains(empleadorListNewEmpleador)) {
                    Comuna oldComunaIdcomunaOfEmpleadorListNewEmpleador = empleadorListNewEmpleador.getComunaIdcomuna();
                    empleadorListNewEmpleador.setComunaIdcomuna(comuna);
                    empleadorListNewEmpleador = em.merge(empleadorListNewEmpleador);
                    if (oldComunaIdcomunaOfEmpleadorListNewEmpleador != null && !oldComunaIdcomunaOfEmpleadorListNewEmpleador.equals(comuna)) {
                        oldComunaIdcomunaOfEmpleadorListNewEmpleador.getEmpleadorList().remove(empleadorListNewEmpleador);
                        oldComunaIdcomunaOfEmpleadorListNewEmpleador = em.merge(oldComunaIdcomunaOfEmpleadorListNewEmpleador);
                    }
                }
            }
            for (Sede sedeListNewSede : sedeListNew) {
                if (!sedeListOld.contains(sedeListNewSede)) {
                    Comuna oldComunaIdcomunaOfSedeListNewSede = sedeListNewSede.getComunaIdcomuna();
                    sedeListNewSede.setComunaIdcomuna(comuna);
                    sedeListNewSede = em.merge(sedeListNewSede);
                    if (oldComunaIdcomunaOfSedeListNewSede != null && !oldComunaIdcomunaOfSedeListNewSede.equals(comuna)) {
                        oldComunaIdcomunaOfSedeListNewSede.getSedeList().remove(sedeListNewSede);
                        oldComunaIdcomunaOfSedeListNewSede = em.merge(oldComunaIdcomunaOfSedeListNewSede);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = comuna.getIdcomuna();
                if (findComuna(id) == null) {
                    throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.");
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
            Comuna comuna;
            try {
                comuna = em.getReference(Comuna.class, id);
                comuna.getIdcomuna();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = comuna.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable comunaIdcomuna field.");
            }
            List<Empleador> empleadorListOrphanCheck = comuna.getEmpleadorList();
            for (Empleador empleadorListOrphanCheckEmpleador : empleadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Empleador " + empleadorListOrphanCheckEmpleador + " in its empleadorList field has a non-nullable comunaIdcomuna field.");
            }
            List<Sede> sedeListOrphanCheck = comuna.getSedeList();
            for (Sede sedeListOrphanCheckSede : sedeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Sede " + sedeListOrphanCheckSede + " in its sedeList field has a non-nullable comunaIdcomuna field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comuna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comuna> findComunaEntities() {
        return findComunaEntities(true, -1, -1);
    }

    public List<Comuna> findComunaEntities(int maxResults, int firstResult) {
        return findComunaEntities(false, maxResults, firstResult);
    }

    private List<Comuna> findComunaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comuna.class));
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

    public Comuna findComuna(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comuna.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comuna> rt = cq.from(Comuna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
