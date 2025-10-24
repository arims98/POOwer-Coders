import controller.Controller;
import model.Datos;
import view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Datos datos = new Datos();
        Controller controller = new Controller(datos);

        MenuPrincipal app = new MenuPrincipal(controller);
        app.iniciar();


    }

}