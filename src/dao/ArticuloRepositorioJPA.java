package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Articulo;
import util.EntityManagerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio JPA para la entidad Articulo
 */
public class ArticuloRepositorioJPA implements Repositorio<Articulo> {

    @Override
    public void agregar(Articulo articulo) throws Exception {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al agregar artículo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Articulo> listar() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        List<Articulo> articulos = new ArrayList<>();
        
        try {
            articulos = em.createQuery("SELECT a FROM Articulo a", Articulo.class)
                         .getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return articulos;
    }

    @Override
    public Articulo buscarPorId(String codigo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Articulo articulo = null;
        
        try {
            articulo = em.find(Articulo.class, codigo);
        } catch (Exception e) {
            System.err.println("Error al buscar artículo: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return articulo;
    }

    @Override
    public void eliminar(String codigo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
            Articulo articulo = em.find(Articulo.class, codigo);
            if (articulo != null) {
                em.remove(articulo);
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar artículo: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}

