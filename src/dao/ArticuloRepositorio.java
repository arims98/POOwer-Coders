package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Articulo;

import java.util.List;

public class ArticuloRepositorio implements Repositorio<Articulo> {

    private final EntityManager em;

    public ArticuloRepositorio(EntityManager em) {
        this.em = em;
    }

    @Override
    public void agregar(Articulo articulo) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Articulo buscarPorId(String codigo) {
        return em.find(Articulo.class, codigo);
    }

    @Override
    public List<Articulo> listar() {
        return em.createQuery("SELECT a FROM Articulo a", Articulo.class)
                .getResultList();
    }

    @Override
    public void eliminar(String codigo) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Articulo existente = em.find(Articulo.class, codigo);
            if (existente != null) {
                em.remove(existente);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
