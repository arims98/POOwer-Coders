// src/module-info.java

// El nombre del módulo debe coincidir con el artifactId del pom.xml: POOwer-Coders -> POOwer.Coders
module POOwer.Coders { 
    
    // --- 1. Requerimientos de JavaFX ---
    requires javafx.controls; 
    requires javafx.fxml;     
    requires javafx.graphics; 

    // --- 2. Requerimientos de Persistencia y Conexión (Hibernate/JPA) ---
    requires jakarta.persistence; 
    requires org.hibernate.orm.core; 
    requires java.sql; // Para JDBC (MySQL)
    
    

    // --- 3. Apertura de Paquetes (CRÍTICO para Reflection y Modularidad) ---
    
    // Abrir 'app' para JavaFX: Necesario para que el framework acceda a la clase App que extiende Application.
    opens app to javafx.graphics, javafx.fxml; 
    
    // Abrir 'controller' para FXML: Permite al FXMLLoader crear instancias de tus controladores.
    opens controller to javafx.fxml;
    
    // Abrir 'model' para Hibernate/JPA: ¡Absolutamente crítico! Permite que la reflexión de JPA acceda a tus clases @Entity.
    opens model to org.hibernate.orm.core, jakarta.persistence; 
    
    // Abrir 'dao' por si tus DAOs necesitan reflexión de Hibernate.
    opens dao to org.hibernate.orm.core; 
    
    // Abrir 'util' (Si tu carpeta 'util' contiene clases que necesitan reflexión de JavaFX o Hibernate)
    // Nota: Si 'util' solo tiene métodos estáticos, esto no es necesario. Si aparece un error, descomentar.
    // opens util to javafx.fxml, org.hibernate.orm.core;

    // --- 4. Exportación de Paquetes (para que se puedan usar externamente) ---
    exports app;
    exports controller;
    exports dao;
    exports model;
    exports util; // Exportamos 'util' por si otras clases lo necesitan.
}