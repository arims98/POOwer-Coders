
package app.model;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("Mi Unidad");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
