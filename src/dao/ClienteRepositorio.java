package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Cliente;
import java.util.List;

public class ClienteRepositorio implements Repositorio<Cliente> {

    private final EntityManager em;

    public ClienteRepositorio(EntityManager em) {
        this.em = em;
    }

    @Override
    public void agregar(Cliente cliente) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Cliente buscarPorId(String nif) {
        return em.find(Cliente.class, nif);
    }

    @Override
    public List<Cliente> listar() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    @Override
    public void eliminar(String nif) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente c = em.find(Cliente.class, nif);
            if (c != null) em.remove(c);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
