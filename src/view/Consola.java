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
            System.out.println("ERROR!!: Entrada invÃ¡lida. Por favor, ingrese un nÃºmero entero.");
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
            // ValidaciÃ³n de los datos introducidos
            while (!teclado.hasNextInt()) {
                System.out.println("ERROR!!: Entrada invÃ¡lida. Por favor, inserte un nÃºmero entero.");
                teclado.next(); // Limpia la entrada no numÃ©rica
                System.out.print("Seleccione una opciÃ³n: "); // Vuelve a pedir
            }

            // Lectura Segura
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpia el buffer (evita saltos en futuros nextLine)

            // ValidaciÃ³n de Rango
            if (opcion < min || opcion > max) {
                System.out.println("ERROR!!: OpciÃ³n invÃ¡lida. Debe ser entre " + min + " y " + max + ".");
                System.out.print("Seleccione una opciÃ³n: ");
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
            System.out.println("ERROR!!: Entrada invÃ¡lida. Por favor, ingrese un nÃºmero decimal.");
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
        System.out.println("Â¡BIENVENIDO A LA ONLINE STORE DE POOWER CODERS!");
        System.out.println("\n----- MENÃš PRINCIPAL -----");
        System.out.println("1. Gestionar ArtÃ­culos");
        System.out.println("2. Gestionar Clientes");
        System.out.println("3. Gestionar Pedidos");
        System.out.println("4. Salir"); 
        System.out.print("Seleccione una opciÃ³n: ");
        // Opciones entre 1 y 4
        return pedirInt(1, 4); 
    }

    public int menuArticulos() {
        System.out.println("\n----- GESTIÃ“N DE ARTÃCULOS -----");
        System.out.println("1. AÃ±adir artÃ­culo");
        System.out.println("2. Mostrar artÃ­culos");
        System.out.println("0. Volver al menÃº principal");
        System.out.print("Seleccione una opciÃ³n: ");
        return pedirInt(0, 2);
    }
    
    public int menuClientes() {
        System.out.println("\n----- GESTIÃ“N DE CLIENTES -----");
        System.out.println("1. AÃ±adir cliente");
        System.out.println("2. Mostrar clientes (Todos)");
        System.out.println("3. Mostrar Clientes EstÃ¡ndar");
        System.out.println("4. Mostrar Clientes Premium");
        System.out.println("0. Volver al menÃº principal");
        System.out.print("Seleccione una opciÃ³n: ");
        return pedirInt(0, 4);
    }

    public int menuPedidos() {
        System.out.println("\n----- GESTIÃ“N DE PEDIDOS -----");
        System.out.println("1. Crear pedido");
        System.out.println("2. Eliminar pedido");
        System.out.println("3. Mostrar pedidos pendientes");
        System.out.println("4. Mostrar pedidos enviados");
        System.out.println("0. Volver al menÃº principal");
        System.out.print("Seleccione una opciÃ³n: ");
        return pedirInt(0, 4);
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
            System.out.println("--- La lista estÃ¡ vacÃ­a ---");
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
            System.out.println("--- La lista estÃ¡ vacÃ­a ---");
            return;
        }
        System.out.println("\n--- " + titulo + " ---");
        for (T elemento : lista) {
            System.out.println(elemento.toString());
        }
        System.out.println("-------------------------------------");
    }
}