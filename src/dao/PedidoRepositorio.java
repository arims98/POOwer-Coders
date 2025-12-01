package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Pedido;
import java.util.List;

public class PedidoRepositorio implements Repositorio<Pedido> {

    private final EntityManager em;

    public PedidoRepositorio(EntityManager em) {
        this.em = em;
    }

    @Override
    public void agregar(Pedido pedido) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Pedido buscarPorId(String numeroPedido) {
        return em.find(Pedido.class, numeroPedido);
    }

    @Override
    public List<Pedido> listar() {
        return em.createQuery("SELECT p FROM Pedido p", Pedido.class)
                .getResultList();
    }

    @Override
    public void eliminar(String numeroPedido) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Pedido p = em.find(Pedido.class, numeroPedido);
            if (p != null) em.remove(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
