package viewfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class HomeView extends BorderPane {

    public HomeView(Runnable openArticulos, Runnable openClientes, Runnable openPedidos) {

        getStyleClass().add("home-bg");

        // ===== Header =====
        Label brand = new Label("POOwer Coders");
        brand.getStyleClass().add("brand");

        Label product = new Label("Online Store");
        product.getStyleClass().add("brand-sub");

        VBox brandBox = new VBox(2, brand, product);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        Label team = new Label("Ariadna, Cèlia, Meritxell, Sergio");
        team.getStyleClass().add("header-subtitle");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, brandBox, spacer, team);
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 22, 18, 22));
        setTop(header);

        // ===== Hero =====
        Label heroTitle = new Label("Bienvenido a Online Store!");
        heroTitle.getStyleClass().add("hero-title");

        Label heroText = new Label("Selecciona un módulo para empezar la gestión de Online Store.");
        heroText.getStyleClass().add("hero-subtitle");

        // ===== Cards (todas iguales) =====
        TilePane cards = new TilePane();
        cards.setPrefColumns(3);
        cards.setHgap(16);
        cards.setVgap(16);
        cards.setAlignment(Pos.CENTER);

        cards.getChildren().addAll(
                moduleCard("🛒", "Artículos",
                        "Alta, listar y eliminar artículos.\n",
                        openArticulos),
                moduleCard("👤", "Clientes",
                        "Alta, filtros (E/P) y eliminación\ncon confirmación.",
                        openClientes),
                moduleCard("📦", "Pedidos",
                        "Crear, buscar, filtrar y eliminar.\nRegla: solo si está pendiente.",
                        openPedidos)
        );

        VBox center = new VBox(14, heroTitle, heroText, cards);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(24, 24, 10, 24));

        VBox container = new VBox(center);
        container.getStyleClass().add("home-container");
        container.setPadding(new Insets(22));
        container.setMaxWidth(980);

        BorderPane.setAlignment(container, Pos.TOP_CENTER);
        BorderPane.setMargin(container, new Insets(28, 24, 18, 24));
        setCenter(container);

        Label footer = new Label("Producto 5 · Interfaz JavaFX con MVC + ORM");
        footer.getStyleClass().add("footer");
        BorderPane.setAlignment(footer, Pos.CENTER);
        BorderPane.setMargin(footer, new Insets(0, 0, 18, 0));
        setBottom(footer);
    }

    private Button moduleCard(String icon, String title, String desc, Runnable action) {
        Label ic = new Label(icon);
        ic.getStyleClass().add("card-icon");

        Label t = new Label(title);
        t.getStyleClass().add("card-title");

        Label d = new Label(desc);
        d.getStyleClass().add("card-desc");
        d.setWrapText(true);

        Label cta = new Label("Abrir →");
        cta.getStyleClass().add("card-cta");

        VBox content = new VBox(8, ic, t, d, cta);
        content.setAlignment(Pos.TOP_LEFT);

        Button card = new Button();
        card.setGraphic(content);
        card.setText(null);
        card.setOnAction(e -> action.run());
        card.getStyleClass().add("module-card");

        // ✅ Todas iguales y más pequeñas
        card.setMinSize(260, 150);
        card.setPrefSize(260, 150);
        card.setMaxSize(260, 150);

        return card;
    }
}