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
import modelo.Entity.Empleador;
import modelo.Entity.Tipousuario;

/**
 *
 * @author Francisco
 */
public class TipousuarioJpaController implements Serializable {

    public TipousuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipousuario tipousuario) throws PreexistingEntityException, Exception {
        if (tipousuario.getUsuarioList() == null) {
            tipousuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (tipousuario.getEmpleadorList() == null) {
            tipousuario.setEmpleadorList(new ArrayList<Empleador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipousuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getRut());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipousuario.setUsuarioList(attachedUsuarioList);
            List<Empleador> attachedEmpleadorList = new ArrayList<Empleador>();
            for (Empleador empleadorListEmpleadorToAttach : tipousuario.getEmpleadorList()) {
                empleadorListEmpleadorToAttach = em.getReference(empleadorListEmpleadorToAttach.getClass(), empleadorListEmpleadorToAttach.getRut());
                attachedEmpleadorList.add(empleadorListEmpleadorToAttach);
            }
            tipousuario.setEmpleadorList(attachedEmpleadorList);
            em.persist(tipousuario);
            for (Usuario usuarioListUsuario : tipousuario.getUsuarioList()) {
                Tipousuario oldTipousuarioIdtipousuarioOfUsuarioListUsuario = usuarioListUsuario.getTipousuarioIdtipousuario();
                usuarioListUsuario.setTipousuarioIdtipousuario(tipousuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldTipousuarioIdtipousuarioOfUsuarioListUsuario != null) {
                    oldTipousuarioIdtipousuarioOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldTipousuarioIdtipousuarioOfUsuarioListUsuario = em.merge(oldTipousuarioIdtipousuarioOfUsuarioListUsuario);
                }
            }
            for (Empleador empleadorListEmpleador : tipousuario.getEmpleadorList()) {
                Tipousuario oldTipousuarioIdtipousuarioOfEmpleadorListEmpleador = empleadorListEmpleador.getTipousuarioIdtipousuario();
                empleadorListEmpleador.setTipousuarioIdtipousuario(tipousuario);
                empleadorListEmpleador = em.merge(empleadorListEmpleador);
                if (oldTipousuarioIdtipousuarioOfEmpleadorListEmpleador != null) {
                    oldTipousuarioIdtipousuarioOfEmpleadorListEmpleador.getEmpleadorList().remove(empleadorListEmpleador);
                    oldTipousuarioIdtipousuarioOfEmpleadorListEmpleador = em.merge(oldTipousuarioIdtipousuarioOfEmpleadorListEmpleador);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipousuario(tipousuario.getIdtipousuario()) != null) {
                throw new PreexistingEntityException("Tipousuario " + tipousuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipousuario tipousuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario persistentTipousuario = em.find(Tipousuario.class, tipousuario.getIdtipousuario());
            List<Usuario> usuarioListOld = persistentTipousuario.getUsuarioList();
            List<Usuario> usuarioListNew = tipousuario.getUsuarioList();
            List<Empleador> empleadorListOld = persistentTipousuario.getEmpleadorList();
            List<Empleador> empleadorListNew = tipousuario.getEmpleadorList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its tipousuarioIdtipousuario field is not nullable.");
                }
            }
            for (Empleador empleadorListOldEmpleador : empleadorListOld) {
                if (!empleadorListNew.contains(empleadorListOldEmpleador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleador " + empleadorListOldEmpleador + " since its tipousuarioIdtipousuario field is not nullable.");
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
            tipousuario.setUsuarioList(usuarioListNew);
            List<Empleador> attachedEmpleadorListNew = new ArrayList<Empleador>();
            for (Empleador empleadorListNewEmpleadorToAttach : empleadorListNew) {
                empleadorListNewEmpleadorToAttach = em.getReference(empleadorListNewEmpleadorToAttach.getClass(), empleadorListNewEmpleadorToAttach.getRut());
                attachedEmpleadorListNew.add(empleadorListNewEmpleadorToAttach);
            }
            empleadorListNew = attachedEmpleadorListNew;
            tipousuario.setEmpleadorList(empleadorListNew);
            tipousuario = em.merge(tipousuario);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Tipousuario oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario = usuarioListNewUsuario.getTipousuarioIdtipousuario();
                    usuarioListNewUsuario.setTipousuarioIdtipousuario(tipousuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario != null && !oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario.equals(tipousuario)) {
                        oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario = em.merge(oldTipousuarioIdtipousuarioOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Empleador empleadorListNewEmpleador : empleadorListNew) {
                if (!empleadorListOld.contains(empleadorListNewEmpleador)) {
                    Tipousuario oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador = empleadorListNewEmpleador.getTipousuarioIdtipousuario();
                    empleadorListNewEmpleador.setTipousuarioIdtipousuario(tipousuario);
                    empleadorListNewEmpleador = em.merge(empleadorListNewEmpleador);
                    if (oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador != null && !oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador.equals(tipousuario)) {
                        oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador.getEmpleadorList().remove(empleadorListNewEmpleador);
                        oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador = em.merge(oldTipousuarioIdtipousuarioOfEmpleadorListNewEmpleador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipousuario.getIdtipousuario();
                if (findTipousuario(id) == null) {
                    throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.");
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
            Tipousuario tipousuario;
            try {
                tipousuario = em.getReference(Tipousuario.class, id);
                tipousuario.getIdtipousuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = tipousuario.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable tipousuarioIdtipousuario field.");
            }
            List<Empleador> empleadorListOrphanCheck = tipousuario.getEmpleadorList();
            for (Empleador empleadorListOrphanCheckEmpleador : empleadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Empleador " + empleadorListOrphanCheckEmpleador + " in its empleadorList field has a non-nullable tipousuarioIdtipousuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipousuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipousuario> findTipousuarioEntities() {
        return findTipousuarioEntities(true, -1, -1);
    }

    public List<Tipousuario> findTipousuarioEntities(int maxResults, int firstResult) {
        return findTipousuarioEntities(false, maxResults, firstResult);
    }

    private List<Tipousuario> findTipousuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipousuario.class));
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

    public Tipousuario findTipousuario(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipousuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipousuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipousuario> rt = cq.from(Tipousuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
