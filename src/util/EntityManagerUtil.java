package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase para gestionar el EntityManager de JPA/Hibernate
 */
public class EntityManagerUtil {
    
    private static final String PERSISTENCE_UNIT_NAME = "OnlineStorePU";
    private static EntityManagerFactory emf;
    
    /**
     * Obtiene la instancia del EntityManagerFactory (Singleton)
     */
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                System.err.println("Error al crear EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return emf;
    }
    
    /**
     * Crea y retorna un nuevo EntityManager
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    
    /**
     * Cierra el EntityManagerFactory
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
