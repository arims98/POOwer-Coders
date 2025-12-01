package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Pedido;
import util.EntityManagerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio JPA para la entidad Pedido
 */
public class PedidoRepositorioJPA implements Repositorio<Pedido> {

    @Override
    public void agregar(Pedido pedido) throws Exception {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al agregar pedido: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> listar() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        List<Pedido> pedidos = new ArrayList<>();
        
        try {
            // Usar JOIN FETCH para cargar las relaciones de forma eficiente
            pedidos = em.createQuery(
                "SELECT p FROM Pedido p " +
                "JOIN FETCH p.cliente " +
                "JOIN FETCH p.articulo", 
                Pedido.class)
                .getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return pedidos;
    }

    @Override
    public Pedido buscarPorId(String numeroPedido) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Pedido pedido = null;
        
        try {
            pedido = em.find(Pedido.class, numeroPedido);
            // Forzar la carga de las relaciones
            if (pedido != null) {
                pedido.getCliente().getNombre();
                pedido.getArticulo().getDescripcion();
            }
        } catch (Exception e) {
            System.err.println("Error al buscar pedido: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return pedido;
    }

    @Override
    public void eliminar(String numeroPedido) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
            Pedido pedido = em.find(Pedido.class, numeroPedido);
            if (pedido != null) {
                em.remove(pedido);
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar pedido: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
