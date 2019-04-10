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
import modelo.Entity.Sede;
import modelo.Entity.Carrera;
import modelo.Entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.DAO.exceptions.IllegalOrphanException;
import modelo.DAO.exceptions.NonexistentEntityException;
import modelo.DAO.exceptions.PreexistingEntityException;
import modelo.Entity.Asociacion;

/**
 *
 * @author Francisco
 */
public class AsociacionJpaController implements Serializable {

    public AsociacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asociacion asociacion) throws PreexistingEntityException, Exception {
        if (asociacion.getUsuarioList() == null) {
            asociacion.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sedeIdsede = asociacion.getSedeIdsede();
            if (sedeIdsede != null) {
                sedeIdsede = em.getReference(sedeIdsede.getClass(), sedeIdsede.getIdsede());
                asociacion.setSedeIdsede(sedeIdsede);
            }
            Carrera carreraIdcarrera = asociacion.getCarreraIdcarrera();
            if (carreraIdcarrera != null) {
                carreraIdcarrera = em.getReference(carreraIdcarrera.getClass(), carreraIdcarrera.getIdcarrera());
                asociacion.setCarreraIdcarrera(carreraIdcarrera);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : asociacion.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getRut());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            asociacion.setUsuarioList(attachedUsuarioList);
            em.persist(asociacion);
            if (sedeIdsede != null) {
                sedeIdsede.getAsociacionList().add(asociacion);
                sedeIdsede = em.merge(sedeIdsede);
            }
            if (carreraIdcarrera != null) {
                carreraIdcarrera.getAsociacionList().add(asociacion);
                carreraIdcarrera = em.merge(carreraIdcarrera);
            }
            for (Usuario usuarioListUsuario : asociacion.getUsuarioList()) {
                Asociacion oldAsociacionIdasociacionOfUsuarioListUsuario = usuarioListUsuario.getAsociacionIdasociacion();
                usuarioListUsuario.setAsociacionIdasociacion(asociacion);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldAsociacionIdasociacionOfUsuarioListUsuario != null) {
                    oldAsociacionIdasociacionOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldAsociacionIdasociacionOfUsuarioListUsuario = em.merge(oldAsociacionIdasociacionOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsociacion(asociacion.getIdasociacion()) != null) {
                throw new PreexistingEntityException("Asociacion " + asociacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asociacion asociacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asociacion persistentAsociacion = em.find(Asociacion.class, asociacion.getIdasociacion());
            Sede sedeIdsedeOld = persistentAsociacion.getSedeIdsede();
            Sede sedeIdsedeNew = asociacion.getSedeIdsede();
            Carrera carreraIdcarreraOld = persistentAsociacion.getCarreraIdcarrera();
            Carrera carreraIdcarreraNew = asociacion.getCarreraIdcarrera();
            List<Usuario> usuarioListOld = persistentAsociacion.getUsuarioList();
            List<Usuario> usuarioListNew = asociacion.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its asociacionIdasociacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sedeIdsedeNew != null) {
                sedeIdsedeNew = em.getReference(sedeIdsedeNew.getClass(), sedeIdsedeNew.getIdsede());
                asociacion.setSedeIdsede(sedeIdsedeNew);
            }
            if (carreraIdcarreraNew != null) {
                carreraIdcarreraNew = em.getReference(carreraIdcarreraNew.getClass(), carreraIdcarreraNew.getIdcarrera());
                asociacion.setCarreraIdcarrera(carreraIdcarreraNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getRut());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            asociacion.setUsuarioList(usuarioListNew);
            asociacion = em.merge(asociacion);
            if (sedeIdsedeOld != null && !sedeIdsedeOld.equals(sedeIdsedeNew)) {
                sedeIdsedeOld.getAsociacionList().remove(asociacion);
                sedeIdsedeOld = em.merge(sedeIdsedeOld);
            }
            if (sedeIdsedeNew != null && !sedeIdsedeNew.equals(sedeIdsedeOld)) {
                sedeIdsedeNew.getAsociacionList().add(asociacion);
                sedeIdsedeNew = em.merge(sedeIdsedeNew);
            }
            if (carreraIdcarreraOld != null && !carreraIdcarreraOld.equals(carreraIdcarreraNew)) {
                carreraIdcarreraOld.getAsociacionList().remove(asociacion);
                carreraIdcarreraOld = em.merge(carreraIdcarreraOld);
            }
            if (carreraIdcarreraNew != null && !carreraIdcarreraNew.equals(carreraIdcarreraOld)) {
                carreraIdcarreraNew.getAsociacionList().add(asociacion);
                carreraIdcarreraNew = em.merge(carreraIdcarreraNew);
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Asociacion oldAsociacionIdasociacionOfUsuarioListNewUsuario = usuarioListNewUsuario.getAsociacionIdasociacion();
                    usuarioListNewUsuario.setAsociacionIdasociacion(asociacion);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldAsociacionIdasociacionOfUsuarioListNewUsuario != null && !oldAsociacionIdasociacionOfUsuarioListNewUsuario.equals(asociacion)) {
                        oldAsociacionIdasociacionOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldAsociacionIdasociacionOfUsuarioListNewUsuario = em.merge(oldAsociacionIdasociacionOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = asociacion.getIdasociacion();
                if (findAsociacion(id) == null) {
                    throw new NonexistentEntityException("The asociacion with id " + id + " no longer exists.");
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
            Asociacion asociacion;
            try {
                asociacion = em.getReference(Asociacion.class, id);
                asociacion.getIdasociacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asociacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = asociacion.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asociacion (" + asociacion + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable asociacionIdasociacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sede sedeIdsede = asociacion.getSedeIdsede();
            if (sedeIdsede != null) {
                sedeIdsede.getAsociacionList().remove(asociacion);
                sedeIdsede = em.merge(sedeIdsede);
            }
            Carrera carreraIdcarrera = asociacion.getCarreraIdcarrera();
            if (carreraIdcarrera != null) {
                carreraIdcarrera.getAsociacionList().remove(asociacion);
                carreraIdcarrera = em.merge(carreraIdcarrera);
            }
            em.remove(asociacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asociacion> findAsociacionEntities() {
        return findAsociacionEntities(true, -1, -1);
    }

    public List<Asociacion> findAsociacionEntities(int maxResults, int firstResult) {
        return findAsociacionEntities(false, maxResults, firstResult);
    }

    private List<Asociacion> findAsociacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asociacion.class));
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

    public Asociacion findAsociacion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asociacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsociacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asociacion> rt = cq.from(Asociacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
