package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Cliente;
import util.EntityManagerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio JPA para la entidad Cliente
 */
public class ClienteRepositorioJPA implements Repositorio<Cliente> {

    @Override
    public void agregar(Cliente cliente) throws Exception {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al agregar cliente: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listar() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                        .getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return clientes;
    }

    @Override
    public Cliente buscarPorId(String nif) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Cliente cliente = null;
        
        try {
            cliente = em.find(Cliente.class, nif);
        } catch (Exception e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            em.close();
        }
        
        return cliente;
    }

    @Override
    public void eliminar(String nif) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
            Cliente cliente = em.find(Cliente.class, nif);
            if (cliente != null) {
                em.remove(cliente);
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
