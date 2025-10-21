import controller.Controlador;

/**
 * MainApp: punto de entrada independiente que arranca la app usando
 * la clase controller.Controlador. Mantiene el main separado del resto
 * de paquetes (model/view/controller).
 */
public class MainApp {
    public static void main(String[] args) {
        // Instanciamos el controlador y lanzamos la aplicación
        Controlador app = new Controlador();
        app.iniciarApp();
    }
}
