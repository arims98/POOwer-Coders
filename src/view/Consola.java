package view;

import java.util.Scanner;
import java.util.List;

public class Consola {
    
    private Scanner teclado;

    public Consola() {
        teclado = new Scanner(System.in);
    }
    

    // MÃ‰TODOS DE LECTURA
    
    /**
     * Pide un valor entero al usuario, asegurando que es numÃ©rico y estÃ¡ en el rango [min, max].
     * @param min Valor mÃ­nimo permitido
     * @param max Valor mÃ¡ximo permitido
     * @return La opciÃ³n vÃ¡lida seleccionada por el usuario.
     */
    public int pedirInt(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextInt()) {
            System.out.println("ERROR: Entrada invalida. Por favor, ingrese un numero entero.");
            teclado.next();
            System.out.print(mensaje);
        }
        int valor = teclado.nextInt();
        teclado.nextLine();
        return valor;
    }

    private int pedirInt(int min, int max) {
        int opcion = -1;
        
        do {
            // Validacion de los datos introducidos
            while (!teclado.hasNextInt()) {
                System.out.println("ERROR: Entrada invalida. Por favor, inserte un numero entero.");
                teclado.next(); // Limpia la entrada no numerica
                System.out.print("Seleccione una opcion: "); // Vuelve a pedir
            }

            // Lectura Segura
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpia el buffer (evita saltos en futuros nextLine)

            // Validacion de Rango
            if (opcion < min || opcion > max) {
                System.out.println("ERROR: Opcion invalida. Debe ser entre " + min + " y " + max + ".");
                System.out.print("Seleccione una opcion: ");
            }

        } while (opcion < min || opcion > max);

        return opcion;
    }

    public String pedirString(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }
    
    public float pedirFloat(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextFloat()) {
            System.out.println("ERROR: Entrada invalida. Por favor, ingrese un numero decimal.");
            teclado.next();
            System.out.print(mensaje); 
        }
        float valor = teclado.nextFloat();
        teclado.nextLine(); 
        return valor;
    }


    // MÃ‰TODOS DE MENÃšS
   

    public int menuPrincipal() {
        System.out.println("\n");
        System.out.println("BIENVENIDO A LA ONLINE STORE DE POOWER CODERS!");
        System.out.println("\n----- MENU PRINCIPAL -----");
        System.out.println("1. Gestionar Articulos");
        System.out.println("2. Gestionar Clientes");
        System.out.println("3. Gestionar Pedidos");
        System.out.println("4. Salir"); 
        System.out.print("Seleccione una opcion: ");
        // Opciones entre 1 y 4
        return pedirInt(1, 4); 
    }

    public int menuArticulos() {
        System.out.println("\n----- GESTION DE ARTICULOS -----");
        System.out.println("1. Mostrar articulos");
        System.out.println("2. Anadir articulo");
        System.out.println("3. Eliminar articulo");
        System.out.println("4. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        return pedirInt(1, 4);
    }
    
    public int menuClientes() {
        System.out.println("\n----- GESTION DE CLIENTES -----");
        System.out.println("1. Mostrar clientes");
        System.out.println("2. Anadir cliente");
        System.out.println("3. Eliminar cliente");
        System.out.println("4. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        return pedirInt(1, 4);
    }

    public int menuPedidos() {
        System.out.println("\n----- GESTION DE PEDIDOS -----");
        System.out.println("1. Mostrar pedidos");
        System.out.println("2. Crear pedido");
        System.out.println("3. Eliminar pedido");
        System.out.println("4. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        return pedirInt(1, 4);
    }


    // MÃ‰TODOS DE VISUALIZACIÃ“N
    
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    /**
     * Muestra el contenido de cualquier lista de objetos (usa el toString() de cada objeto).
     */
    public <T> void mostrarLista(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("--- La lista esta vacia ---");
            return;
        }
        // Usa el nombre de la clase del primer elemento para el encabezado
        String nombreClase = lista.get(0).getClass().getSimpleName();
        System.out.println("\n--- LISTADO DE " + nombreClase.toUpperCase() + "S ---");
        for (T elemento : lista) {
            System.out.println(elemento.toString());
        }
        System.out.println("-------------------------------------");
    }

    /**
     * Variante con tÃ­tulo explÃ­cito para controlar el encabezado del listado.
     */
    public <T> void mostrarLista(String titulo, List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("--- La lista esta vacia ---");
            return;
        }
        System.out.println("\n--- " + titulo + " ---");
        for (T elemento : lista) {
            System.out.println(elemento.toString());
        }
        System.out.println("-------------------------------------");
    }
}