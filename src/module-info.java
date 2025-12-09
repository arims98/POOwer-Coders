module POOwerCoders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires transitive jakarta.persistence;
    requires org.hibernate.orm.core;
    
    exports app;
    exports controller;
    exports dao;
    exports model;
    exports util;
    exports view;
    
    // Open packages to modules that need reflection access
    opens model to org.hibernate.orm.core;
    opens controller to javafx.fxml;
}
