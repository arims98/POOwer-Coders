package view;

import java.util.Scanner;
import java.util.List;
// Importamos las clases de moedel para poder mostrar sus listas
import model.Cliente; 
import model.Articulo; 
import model.Pedido;

public class Consola {
    
    private Scanner teclado;

    public Consola() {
        teclado = new Scanner(System.in);
    }
    

    // MÉTODOS DE LECTURA
    
    /**
     * Pide un valor entero al usuario, asegurando que es numérico y está en el rango [min, max].
     * @param min Valor mínimo permitido
     * @param max Valor máximo permitido
     * @return La opción válida seleccionada por el usuario.
     */
    public int pedirInt(String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextInt()) {
            System.out.println("ERROR!!: Entrada inválida. Por favor, ingrese un número entero.");
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
            // Validación de los datos introducidos
            while (!teclado.hasNextInt()) {
                System.out.println("ERROR!!: Entrada inválida. Por favor, inserte un número entero.");
                teclado.next(); // Limpia la entrada no numérica
                System.out.print("Seleccione una opción: "); // Vuelve a pedir
            }

            // Lectura Segura
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpia el buffer (evita saltos en futuros nextLine)

            // Validación de Rango
            if (opcion < min || opcion > max) {
                System.out.println("ERROR!!: Opción inválida. Debe ser entre " + min + " y " + max + ".");
                System.out.print("Seleccione una opción: ");
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
            System.out.println("ERROR!!: Entrada inválida. Por favor, ingrese un número decimal.");
            teclado.next();
            System.out.print(mensaje); 
        }
        float valor = teclado.nextFloat();
        teclado.nextLine(); 
        return valor;
    }


    // MÉTODOS DE MENÚS
   

    public int menuPrincipal() {
        System.out.println("\n----- MENÚ PRINCIPAL -----");
        System.out.println("1. Gestionar Artículos");
        System.out.println("2. Gestionar Clientes");
        System.out.println("3. Gestionar Pedidos");
        System.out.println("4. Salir"); 
        System.out.print("Seleccione una opción: ");
        // Opciones entre 1 y 4
        return pedirInt(1, 4); 
    }

    public int menuArticulos() {
        System.out.println("\n----- GESTIÓN DE ARTÍCULOS -----");
        System.out.println("1. Añadir artículo");
        System.out.println("2. Mostrar artículos");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return pedirInt(0, 2);
    }
    
    public int menuClientes() {
        System.out.println("\n----- GESTIÓN DE CLIENTES -----");
        System.out.println("1. Añadir cliente");
        System.out.println("2. Mostrar clientes (Todos)");
        System.out.println("3. Mostrar Clientes Estándar");
        System.out.println("4. Mostrar Clientes Premium");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return pedirInt(0, 4);
    }

    public int menuPedidos() {
        System.out.println("\n----- GESTIÓN DE PEDIDOS -----");
        System.out.println("1. Crear pedido");
        System.out.println("2. Eliminar pedido");
        System.out.println("3. Mostrar pedidos pendientes");
        System.out.println("4. Mostrar pedidos enviados");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return pedirInt(0, 4);
    }


    // MÉTODOS DE VISUALIZACIÓN
    
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    /**
     * Muestra el contenido de cualquier lista de objetos (usa el toString() de cada objeto).
     */
    public <T> void mostrarLista(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("--- La lista está vacía ---");
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
     * Variante con título explícito para controlar el encabezado del listado.
     */
    public <T> void mostrarLista(String titulo, List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("--- La lista está vacía ---");
            return;
        }
        System.out.println("\n--- " + titulo + " ---");
        for (T elemento : lista) {
            System.out.println(elemento.toString());
        }
        System.out.println("-------------------------------------");
    }
}