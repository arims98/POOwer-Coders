package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Articulo;
import java.util.List;

public class ArticuloRepositorio implements Repositorio<Articulo> {

    private EntityManager em;

    public ArticuloRepositorio() {
        this.em = Persistence
                .createEntityManagerFactory("MiUnidadPersistencia")
                .createEntityManager();
    }

    @Override
    public void agregar(Articulo articulo) {
        em.getTransaction().begin();
        em.persist(articulo);
        em.getTransaction().commit();
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
        Articulo art = em.find(Articulo.class, codigo);
        if (art != null) {
            em.getTransaction().begin();
            em.remove(art);
            em.getTransaction().commit();
        }
    }
}
