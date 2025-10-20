package view;

import controller.PedidoControlador;
import dao.PedidoRepositorio;
import java.io.PrintStream;
import java.util.List;

public class PedidoVista {
    public PedidoVista() {
    }

    public void iniciar() {
        PedidoRepositorio repositorio = new PedidoRepositorio();
        PedidoControlador controller = new PedidoControlador(repositorio);
        repositorio.cargarDatosPrueba();
        System.out.println("\n=== LISTADO DE PEDIDOS ===");
        List var10000 = controller.listarPedidos();
        PrintStream var10001 = System.out;
        var10000.forEach(var10001::println);
    }
}