import controller.Controlador;

/**
 * MainApp: arranca la app usando la clase controller.Controlador.
 */
public class MainApp {
    public static void main(String[] args) {
        // Instanciamos el controlador y lanzamos la aplicacionn
        Controlador app = new Controlador();
        app.iniciarApp();
    }
}
